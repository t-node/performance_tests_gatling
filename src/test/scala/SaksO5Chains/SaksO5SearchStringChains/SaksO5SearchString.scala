package SaksO5Chains.SaksO5SearchStringChains

import java.util.Random

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains.SaksSearchPDP._
import SaksChains.SaksCheckoutChains.SaksSearchStringPA._
import SaksChains.SaksPDPChains.SaksSearchPDPDirect._
import SaksO5Chains.SaksO5CheckoutChains.SaksO5SearchPDP._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SaksO5SearchString extends BaseTest{
  val SearchString_Transaction = instance + "SearchString_Transaction"
  val lookahead_json ="lookahead_json.jsp"
  val EndecaSearch ="EndecaSearch.jsp"
  val SaksSearchString = group(SearchString_Transaction) {
    feed(SearchString)
      .exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })
      /*.exec(http(lookahead_json)
      .get(baseUrlHttp + "/search/lookahead_json.jsp?callback=jQuery172012264395567273178_1458054414738&term=${SearchString}&_=1458054490227")
        .check(status.is(200))
    )

*/
      .exec(http(EndecaSearch)
        .get(baseUrlHttp + "/search/EndecaSearch.jsp?bmForm=endeca_search_form_one&SearchString=${SearchString}&submit-search=&N_Dim=0")
     // .check(regex("""<a name="(\d+)""").findAll.saveAs("ProductIDAll"))
     // .check(regex("""<a name="(\d+)""").count.saveAs("Img_id_count"))
    //  .check(regex("""folder_id=(\d+)&""").saveAs("folder_id"))
    //  .check(regex("""name="bmUID" value="(.*)"""").saveAs("bmUID"))
    //  .check(regex("""name="sid" value="(.*)"""").saveAs("sid"))
      .check(status.is(200))

      )

      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session("startTime").as[Long],EndTime,instance + "SaksO5_SearchString")
        session

      })
    /*  .exec(session => {
        val rand = new Random(System.currentTimeMillis());

        // println("cartID Info")
        //   println(session.attributes)
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

      })*/

    }

  }
