package SaksO5Chains.SaksO5PDPChains

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SaksO5SearchPDPData extends BaseTest{
  val PDP_Transaction = instance + "PDP_Transaction"
  val EndecaSearch ="EndecaSearch.jsp"

  val SaksSearch = group(PDP_Transaction) {
    feed(ItemIDs)
      .exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

    //  feed(ProductID)
          .exec(http(EndecaSearch)
        .get(baseUrlHttp + "/search/EndecaSearch.jsp?bmForm=endeca_search_form_one&SearchString=${ProductID}&submit-search=&N_Dim=0")
       // .check(bodyString.saveAs("fullBody1"))
          .check(regex("""${ProductID}""").exists)
          .check(status.is(200))
      )


      .exec(session => {
      val EndTime = System.currentTimeMillis()
      insertGroupResponseTime(session("startTime").as[Long],EndTime,instance + "SaksO5_PDP")
      session

    })



    }

  }
