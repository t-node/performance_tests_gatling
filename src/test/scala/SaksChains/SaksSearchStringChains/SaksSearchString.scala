package SaksChains.SaksSearchStringChains

import java.util.Random

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains.SaksSearchPDP._
import SaksChains.SaksCheckoutChains.SaksSearchStringPA._
import SaksChains.SaksPDPChains.SaksSearchPDPDirect._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SaksSearchString extends BaseTest{
  val SearchString_Transaction = instance + "SearchString_Transaction"
  val SaksSearchString = group(SearchString_Transaction) {
    feed(SearchString)
      .feed(ProductsPA)
      .exec(session => {
        // print the Session for debugging, don't do that on real Simulations
        val startTime = System.currentTimeMillis()
        session.set("startTime", startTime)
      })
      .exec(http("lookahead_json.jsp")
        .get(baseUrlHttp + "/search/lookahead_json.jsp?callback=jQuery172012264395567273178_1458054414738&term=${SearchString}&_=1458054490227")
        .check(status.is(200))
      )


      .exec(http("EndecaSearch.jsp")
        .get(baseUrlHttp + "/search/EndecaSearch.jsp?bmForm=endeca_search_form_one&bmFormID=${bmFormID}&bmUID=${bmUID}&bmIsForm=true&bmPrevTemplate=%2FEntry.jsp&bmText=SearchString&SearchString=${SearchString}&submit-search=&bmSingle=N_Dim&N_Dim=0&bmHidden=Ntk&Ntk=Entire+Site&bmHidden=Ntx&Ntx=mode%2Bmatchpartialmax&bmHidden=prp8&prp8=t15&bmHidden=prp13&prp13=&bmHidden=sid&sid=${sid}&bmHidden=FOLDER%3C%3Efolder_id&FOLDER%3C%3Efolder_id=")
        .check(regex("""<a name="(\d+)""").findAll.saveAs("ProductIDAll"))
        .check(regex("""<a name="(\d+)""").count.saveAs("Img_id_count"))
        .check(regex("""folder_id=(\d+)&""").saveAs("folder_id"))
        .check(status.is(200))

      )

      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session.toString,SearchString_Transaction)
        session

      })

      .exec(session => {
        val rand = new Random(System.currentTimeMillis());

        val ProductIDList: List[String] = session("ProductIDAll").as[List[String]]
        val random_index = rand.nextInt(ProductIDList.length);
        val result = ProductIDList(random_index);
        val random_index2 = rand.nextInt(ProductIDList.length);
        val result2 = ProductIDList(random_index2);
        val random_index3 = rand.nextInt(ProductIDList.length);
        val result3 = ProductIDList(random_index3);
        val random_index4 = rand.nextInt(ProductIDList.length);
        val result4 = ProductIDList(random_index4);
        //   println(session("fullBody1").as[String])
        // println(session("url").as[String])
        session.set("ProductID",result).set("ProductID2",result2).set("ProductID3",result3).set("ProductID4",result4)

      })
  }

}
