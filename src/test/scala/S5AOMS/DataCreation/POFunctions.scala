package S5AOMS.DataCreation

import OMSBase.OMSBaseTest
import io.gatling.core.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object POFunctions extends OMSBaseTest {


  val POCreate = group("POcreate_Transaction") {
   feed(address)
      .exec(session => {

        val OrderNumber = generateOrderNumber("S5APPO")
        var zipCode = session("zip").as[Int].toString
        if(zipCode.length==4) zipCode="0"+zipCode
        val state = session("state").as[String]
        val address1 = session("address").as[String]
        val city = session("city").as[String]

        val lengthPO : Int = lengthRedis("ItemsPO")
        var flag = 1

       if(lengthPO>0) {
          val items = readRedisLPOP("ItemsPO")
          inputRedis("ItemsPONew",items)
          val POcreateXml = getPOcreateXml(org, OrderNumber, "DC-LVG-689", "500", items, zipCode, address1, city, state, "US")
//          println(POcreateXml)

          sendMessage(POcreateXml, "Order Queue PO")
          inputRedis("PurchaseOrders", OrderNumber)
          inputRedis(OrderNumber, items)

        }
        else{
         flag = 0
      //   renameRedis("ItemsPONew","ItemsPO")

       }
        session.set("flag",flag)

      })

  }

    val POReceive = group("POReceive_Transaction") {
    feed(address)
      .exec(session => {



        var zipCode = session("zip").as[Int].toString
        if(zipCode.length==4) zipCode="0"+zipCode
        val state = session("state").as[String]
        val address1 = session("address").as[String]
        val city = session("city").as[String]
        val lengthPO : Int = lengthRedis("PurchaseOrders")
          var flag =1
          if(lengthPO>0) {

          val OrderNumber = readRedisLPOP("PurchaseOrders")
          val item = readRedisLPOP(OrderNumber)

          val POreceiveXml = getPOreceiveXml(org, OrderNumber, "DC-LVG-689", "500", item, zipCode, address1, city, state, "US")
       //   println(POreceiveXml)

          sendMessage(POreceiveXml, "Order Queue PO")
          //  inputRedis("ItemsPOPlaced", item)
        }
        else {
           flag = 0
          }
        session.set("flag", flag)

      })

  }

}
