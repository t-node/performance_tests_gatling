package SaksChains.SaksPDPChains

import java.util.Random

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains.SaksShoppingBag._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SaksSearchPA extends BaseTest{

  val SaksSearchStringPA = group("SearchPA_Transaction") {
      feed(Links)
          .feed(ProductsPA)
      .exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

        .exec(http("Product Array")
        .get(baseUrlHttp + "${Links}")
          .check(regex("""<a name="(\d+)""").findAll.saveAs("ProductIDAll"))
          .check(regex("""<a name="(\d+)""").count.saveAs("Img_id_count"))
          .check(regex("""folder_id=(\d+)&""").saveAs("folder_id"))
          .check(status.is(200))
        //  .check(css("a[id='image-url-${ProductID}']","href").saveAs("url"))

          //   .check(bodyString.saveAs("fullBody1"))
      )

        .exec(session => {
          val EndTime = System.currentTimeMillis()
          insertGroupResponseTime(session("startTime").as[Long],EndTime,"Saks_SearchPA")
          session

        })

        .exec(session => {
          val rand = new Random(System.currentTimeMillis());

             val ProductIDList: List[String] = session("ProductIDAll").as[List[String]]
          val random_index = rand.nextInt(ProductIDList.length);
          val result = ProductIDList(random_index);
           session.set("ProductID",result)

        })





    }

  }
