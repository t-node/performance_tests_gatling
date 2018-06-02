package SaksO5Chains.SaksO5CheckoutChains

import java.util.Random

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SaksO5SearchStringPA extends BaseTest{
  val MenuPA_Transaction = instance + "MenuPA_Transaction"
  val ProductArray = "Product Array"
  val SaksSearchStringPA = group(MenuPA_Transaction) {
      feed(saksO5Links)
        .feed(ProductsPA)
      .exec(session => {
        println(baseUrlHttp + session("Links").as[List[String]])
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

        .exec(http(ProductArray)
        .get(baseUrlHttp + "${Links}")
          .check(regex("""name="bmUID" value="(.*)"""").saveAs("bmUID"))
         // .check(regex("""name="bmFormID" value="(.*)"""").saveAs("bmFormID"))
          .check(regex("""sid=(.*)&""").saveAs("sid"))
        //  .check(regex("""name="sid" value="(.*)"""").saveAs("sid"))
          .check(regex("""<a name="(\d+)""").findAll.saveAs("ProductIDAll"))
          .check(regex("""<a name="(\d+)""").count.saveAs("Img_id_count"))
          .check(status.is(200))
        //  .check(css("a[id='image-url-${ProductID}']","href").saveAs("url"))

          //   .check(bodyString.saveAs("fullBody1"))
      )

        .exec(session => {
          val EndTime = System.currentTimeMillis()
          insertGroupResponseTime(session("startTime").as[Long],EndTime,instance + "SaksO5_SearchPA")
          session

        })

        .exec(session => {
          val rand = new Random(System.currentTimeMillis());

          // println("cartID Info")
       //   println(session.attributes)
          val ProductIDList: List[String] = session("ProductIDAll").as[List[String]]
          val random_index = rand.nextInt(ProductIDList.length);
          val result = ProductIDList(random_index);

          session.set("ProductID",result)

        })




    }

  }
