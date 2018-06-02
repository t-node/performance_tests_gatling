package PlaceOrderChains.PlaceOrderChains

import java.util.NoSuchElementException

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains.SaksAddToBag._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SearchPDPDC extends BaseTest{
  val PDP_Transaction = instance + "PDP_Transaction"
  val lookahead_json = "lookahead_json.jsp"
  val EndecaSearch = "EndecaSearch.jsp"
  val SaksSearch = group(PDP_Transaction) {
    feed(DCProducts)
     .exec(session => {
     println("Product ID : " + session("ProductID").as[String])
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

    .exec(http(EndecaSearch)
      .get(baseUrlHttp + "/search/EndecaSearch.jsp?bmForm=endeca_search_form_one&SearchString=${ProductID}&submit-search=&N_Dim=0")
        .check(regex("""prd_id=(\d+)&""").saveAs("prd_id"))
        .check(regex("""folder_id=(\d+)&""").saveAs("folder_id"))
        .check(regex("""This item is sold out. Add to Wait List to be notified when it is back in stock.</h""").count.saveAs("SoldOut"))
        .check(regex("""sku_id":"(\d+)","price":\{.*?\},"status_alias":"(\w+)""").count.saveAs("sku_idCount"))
        .check(regex("""sku_id":"(\d+)","price":\{.*?\},"status_alias":"\w+""").findAll.saveAs("sku_id_values"))
        .check(regex("""sku_id":"\d+","price":\{.*?\},"status_alias":"(\w+)""").findAll.saveAs("status"))
        .check(status.is(200))

      )

       .exec(session => {
         val EndTime = System.currentTimeMillis()
         insertGroupResponseTime(session("startTime").as[Long],EndTime,instance + "SaksO5_PDP")
         session

       })



        .exec(session => {
        try {
          val statusList: List[String] = session("status").as[List[String]]
          val skuList: List[String] = session("sku_id_values").as[List[String]]
          val mapp = (statusList zip skuList).toMap
          session.set("sku_id", mapp("available")).set("availableItems", 1)
        }
          catch{
            case e:NoSuchElementException => session.set("availableItems", 0)
          }

        })

    }

  }
