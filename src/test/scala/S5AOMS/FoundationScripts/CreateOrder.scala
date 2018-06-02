package S5AOMS.FoundationScripts

import OMSBase.OMSBaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object CreateOrder extends OMSBaseTest {


  val createOrder = group("${Transaction}_Transaction") {
    feed(Instances)
      .feed(credentials)
      .feed(address)
      .exec(session => {

        val Instance = session("Instance").as[String]
        val userId = session("userId").as[String]
        val password = session("password").as[String]
        val EnteredBy = session("EnteredBy").as[String]
        val Org = session("Org").as[String]
        val EntryType = session("EntryType").as[String]
        val OrderNumber = generateOrderNumber("S5AP")
        var zipCode = session("zip").as[Int].toString
        if(zipCode.length==4) zipCode="0"+zipCode
        val state = session("state").as[String]
        val address1 = session("address").as[String]
        val city = session("city").as[String]
        val Transaction = session("Transaction").as[String]

        val items = getItems(session("numOfItems").as[String].toInt, session("InvList").as[String])
        val arg0 = getArg0(userId, password)
        val arg1 = getReserveArg1(items, OrderNumber, zipCode, state)
        val reserveXml = getReserveXml(Instance, arg0, arg1)
        var orderLines = ""

        if (Transaction.contains("CreateOrderPO")) {
          orderLines = getPreOrderLines(items, OrderNumber)
        }
        else if (Transaction.contains("Mix")) {
          orderLines = getMixOrderLines(items, OrderNumber)
        }
        else {
          orderLines = getOrderLines(items, OrderNumber)
        }


        val paymentMethods = getPaymentMethod(session("numOfItems").as[String])
        val orderXml = getOrderXml(OrderNumber, EnteredBy, Org, EntryType, orderLines, paymentMethods, zipCode, address1, city, state, "US")
       // println(orderXml)
        session.set("reserveXml", reserveXml).set("InstanceUrl", Instance + "/smcfsejb/YIFWebService/YIFWebService").set("orderXml", orderXml).set("OrderNumber", OrderNumber)

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
        val OrdersList = session("Transaction").as[String] + session("numOfItems").as[String] + session("InvList").as[String]
        val OrderNumber = session("OrderNumber").as[String]
        sendMessage(messageText, "Order Queue")
        println(OrdersList)
        inputRedis(OrdersList,OrderNumber)

        session

      })
  }

}
