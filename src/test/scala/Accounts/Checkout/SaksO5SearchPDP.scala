package Accounts.Checkout

import java.util.NoSuchElementException

import Accounts.AccountsTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by 461967 on 8/29/2016.
  */
object SaksO5SearchPDP extends AccountsTest{
  val PDP_Transaction = instance + "PDP_Transaction"
  val lookahead_json = "lookahead_json.jsp"
  val EndecaSearch = "EndecaSearch.jsp"
  val SaksSearch = group(PDP_Transaction) {
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

      /* .exec(http(lookahead_json)
      .get(baseUrlHttp + "/search/lookahead_json.jsp?callback=jQuery172012264395567273178_1458054414738&term=${ProductID}&_=1458054490227")
       .check(status.is(200))
     )*/

      .exec(http(EndecaSearch)
      .get(baseUrlHttp + "/search/EndecaSearch.jsp?bmForm=endeca_search_form_one&SearchString=${ProductID}&submit-search=&N_Dim=0")
      // .header("LoadTesting","Gatling")
      .check(css("input[name='bmUID']", "value").saveAs("bmUID2"))
      .check(regex("""prd_id=(\d+)&""").saveAs("prd_id"))
      .check(regex("""folder_id=(\d+)&""").saveAs("folder_id"))
      // .check(regex("""id":(\d+),"value":"\d*\s*\W*\d*\W*\S*:false""").count.saveAs("sku_idCount"))
      .check(regex("""This item is sold out. Add to Wait List to be notified when it is back in stock.</h""").count.saveAs("SoldOut"))
      // .check(regex("""${ProductID}""").exists)
      //.check(regex("""id":(\d+),"value":"\d*\s*\W*\d*\W*\S*:false""").find(0).saveAs("value"))
      //.check(regex(""""size_id":${value},"?\w*"?:?-?\d*,?"sku_id":"(\d+)""").saveAs("sku_id"))
      // .check(regex(""""size_id":${value},"denomination_id":-1,"sku_id":"(\d+)""").saveAs("sku_id"))
      //.check(bodyString.saveAs("fullBody1"))
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
