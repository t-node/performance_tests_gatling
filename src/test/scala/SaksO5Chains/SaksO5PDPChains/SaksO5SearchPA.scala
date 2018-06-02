package SaksO5Chains.SaksO5PDPChains

import java.util.Random

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains.SaksShoppingBag._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SaksO5SearchPA extends BaseTest{
  val MenuPA_Transaction = instance + "MenuPA_Transaction"
  val ProductArray ="Product Array"

  val SaksSearchStringPA = group(MenuPA_Transaction) {
      feed(saksO5LinksProd)
          .feed(ProductsPA)
      .exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

        .exec(http(ProductArray)
        .get(baseUrlHttp + "${Links}")
          .check(regex("""<a name="(\d+)""").findAll.saveAs("ProductIDAll"))
          .check(regex("""<a name="(\d+)""").count.saveAs("Img_id_count"))
          .check(regex("""folder_id=(\d+)&""").saveAs("folder_id"))
          .check(regex("""name="bmUID" value="(.*)"""").saveAs("bmUID"))
        //  .check(regex("""name="bmFormID" value="(.*)"""").saveAs("bmFormID"))
          .check(regex("""sid=(.*)&""").saveAs("sid"))
         // .check(regex("""name="sid" value="(.*)"""").saveAs("sid"))
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
