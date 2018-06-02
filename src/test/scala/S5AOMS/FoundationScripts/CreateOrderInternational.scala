package S5AOMS.FoundationScripts

import OMSBase.OMSBaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object CreateOrderInternational extends OMSBaseTest {


  val CreateOrderInternational = group("${Transaction}_Transaction") {
    feed(Instances)
      .feed(credentials)
      .feed(addressInt)
      .exec(session => {
        val Instance = session("Instance").as[String]
        val userId = session("userId").as[String]
        val password = session("password").as[String]
        val EnteredBy = session("EnteredBy").as[String]
        val Org = session("Org").as[String]
        val EntryType = session("EntryType").as[String]
        val OrderNumber = generateOrderNumber("S5AP")
        val zipCode = session("zip").as[String]
        val country = session("country").as[String]
        val state = session("state").as[String]
        val address1 = session("address").as[String]
        val city = session("city").as[String]

        val items = getItems(session("numOfItems").as[String].toInt, session("InvList").as[String])
        val arg0 = getArg0(userId, password)
        val arg1 = getReserveArg1(items, OrderNumber, zipCode, state)
        val reserveXml = getReserveXml(Instance, arg0, arg1)


        val orderLines = getOrderLines(items, OrderNumber)
        val paymentMethods = getPaymentMethod(session("numOfItems").as[String])
        val orderXml = getOrderXml(OrderNumber, EnteredBy, Org, EntryType, orderLines, paymentMethods, zipCode, address1, city, state, country)
        println(orderXml)
        session.set("reserveXml", reserveXml).set("InstanceUrl",Instance+"/smcfsejb/YIFWebService/YIFWebService").set("orderXml", orderXml).set("OrderNumber", OrderNumber)

      })

      .exec(http("inventoryReservation_${numOfItems}${InvList}_${EnteredBy}")
        .post("${InstanceUrl}")
        .body(StringBody("${reserveXml}"))
         .check(status.is(200))
         .check(regex("""ReservationID""").exists)
        //  .check(bodyString.saveAs("fullBody1"))
      )

      .exec(session => {
        val messageText = session("orderXml").as[String]
        val OrdersList = "Orders" + session("InvList").as[String] + session("numOfItems").as[String] + session("EnteredBy").as[String]
        sendMessage(messageText, "Order Queue")
        inputRedis(OrdersList, session("OrderNumber").as[String])
        session

      })
  }

}
