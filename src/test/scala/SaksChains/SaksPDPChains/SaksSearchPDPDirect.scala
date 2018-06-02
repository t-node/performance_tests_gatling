package SaksChains.SaksPDPChains

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains.SaksShoppingBag._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SaksSearchPDPDirect extends BaseTest{

  val SaksSearch = group("Search_Transaction") {
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

    //  feed(ProductID)
          .exec(http("EndecaSearch.jsp")
        .get(baseUrlHttp + "/search/EndecaSearch.jsp?bmForm=endeca_search_form_one&SearchString=${ProductID}&submit-search=&N_Dim=0")
       // .check(bodyString.saveAs("fullBody1"))
          .check(regex("""${ProductID}""").exists)
          .check(status.is(200))
      )


      .exec(session => {
      val EndTime = System.currentTimeMillis()
      insertGroupResponseTime(session("startTime").as[Long],EndTime,"Saks_PDP")
      session

    })

    }

  }
