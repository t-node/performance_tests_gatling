package S5AOMS.FoundationScripts

import OMSBase.OMSBaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object FoundationScripts extends OMSBaseTest {


  val FoundationScripts = group("${Transaction}_Transaction") {
    feed(Instances)
      .feed(credentials)
      .feed(address)
      .exec(session => {
        val Instance = session("Instance").as[String]
        val userId = session("userId").as[String]
        val password = session("password").as[String]
        val Org = session("Org").as[String]
        val EntryType = session("EntryType").as[String]

        val Transaction = session("Transaction").as[String]


        val items = getItems(session("numOfItems").as[String].toInt, session("InvList").as[String])
        val arg0 = getArg0(userId, password)

        var invLookupXml = ""

        if (Transaction.contains("ATP")) {
          val arg1 = getATPArg1(items, Org)
          invLookupXml = getATPXml(Instance, arg0, arg1)
        }
        else {

          var zipCode = session("zip").as[Int].toString
          if(zipCode.length==4) zipCode="0"+zipCode
          val state = session("state").as[String]
          val FulfillmentType = session("FulfillmentType").as[String]
          val arg1 = getInvLookupArg1(items, EntryType, Org, zipCode, state, FulfillmentType)
          invLookupXml = getInvLookupXml(Instance, arg0, arg1)
        }

        //println(invLookupXml)
        session.set("invLookupXml", invLookupXml).set("InstanceUrl", Instance + "/smcfsejb/YIFWebService/YIFWebService").set("item",items.get(0))

      })

      .exec(http("${Transaction}_${numOfItems}${InvList}_${EntryType}")
        .post("${InstanceUrl}")
        .body(StringBody("${invLookupXml}"))
        .check(status.is(200))
     //   .check(regex("""Node="(\d+)""").saveAs("Node"))
         .check(bodyString.saveAs("fullBody1"))
      )

      .exec(session => {
      /*  val item = session("item").as[String]
        val Node = session("Node").as[String]
        inputRedis(Node,item)*/

        println(session("fullBody1").as[String])
        session

      })


  }

}
