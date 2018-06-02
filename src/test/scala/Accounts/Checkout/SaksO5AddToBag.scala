package Accounts.Checkout

import Accounts.AccountsTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by 461967 on 8/29/2016.
  */
object SaksO5AddToBag extends AccountsTest{
  // val ProductID = jsonFile("src/test/resources/data/ProductID.json").circular
  val checkout = baseUrlHttp + "/checkout/checkout.jsp"
  val AddToBag_Transaction = instance + "AddToBag_Transaction"
  val add_saks_suggests_item_service_product_array = "add_saks_suggests_item_service_product_array"
  val saksbaginsert = "saksbaginsert.jsp"
  val AddToBag = group(AddToBag_Transaction) {
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })
      .exec(session => {
        val prd_id = session("prd_id").as[String]
        val folder_id = session("folder_id").as[String]
        val bmUID = session("bmUID").as[String]
        val ItemQty = session("ItemQty").as[Int]
        val checkout2 = baseUrlHttp + "/include/saksbaginsert.jsp?PRODUCT%3C%3Eprd_id=" + prd_id + "&FOLDER%3C%3Efolder_id=" + folder_id + "&bmUID=" + bmUID + "&num_of_items_added=" + ItemQty
        //      val counter = session("counterAddToBag").as[Int]

        //val skuVal = sku(counter)


        //  println(session.attributes)
        session.set("checkoutUrl", checkout2)

      })

      .exec(http(add_saks_suggests_item_service_product_array)
        .post(checkout)
        //   .header("LoadTesting","Gatling")
        .formParam("bmForm", "add_saks_suggests_item_service_product_array")
        .formParam("productCode", "${ProductID}")
        .formParam("itemQuantity", "${ItemQty}")
        .formParam("sku_id", "${sku_id}")
        .formParam("type", "REQUEST_ADD_TO_BAG")
        .check(status.is(200))
        .check(regex("""displayAddToBag":true""").count.saveAs("AddToBag_valid"))
        //    .check(bodyString.saveAs("fullBody1"))


      )
      .doIf(session => session("AddToBag_valid").as[Int] != 0) {
        exec(http(saksbaginsert)
          .post("${checkoutUrl}")
          //  .header("LoadTesting","Gatling")
          .body(StringBody("&num_of_items_added=${ItemQty}"))
          .check(regex("""id="status(\d+)" class""").saveAs("cartID"))
          .check(regex("""id="status(\d+)" class""").count.saveAs("cartItemIdCount"))
          .check(status.is(200))
        )


          .exec(session => {
            val EndTime = System.currentTimeMillis()
            insertGroupResponseTime(session("startTime").as[Long], EndTime, instance + "SaksO5_AddToBag")
            session

          })


      }
  }

}
