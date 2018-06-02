package LTChains.LTPDPChains

import java.util.Random

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object LTSearchPA extends BaseTest{

  val LTSearchPA = group("LTSearchPA_Transaction") {
      feed(LTLinks)
        .exec(http("Product Array")
        .get(baseUrlHttp + "${LTLinks}")
          //.check(regex("""<a name="(\d+)""").findAll.saveAs("ProductIDAll"))
         // .check(regex("""<a name="(\d+)""").count.saveAs("Img_id_count"))
        //  .check(css("a[id='image-url-${ProductID}']","href").saveAs("url"))
          .check(regex("""\t\t\t"(\S+)",""").findAll.saveAs("ProductIDAll"))
          .check(regex("""content="storeId_(\d+)""").find.saveAs("StoreID"))
          .check(regex("""setCommonParameters\('-\d+','\d+','(\d+)""").find.saveAs("catalogID"))
          .check(regex("""orderBy=(\d+)""").findAll.saveAs("orderBy"))
          .check(regex("""searchResultsPageNum']='(\d+)""").findAll.saveAs("searchResultsPageNum"))
          .check(regex("""top_category=(\d+)""").find.saveAs("top_category"))
          .check(regex("""categoryId=(\d+)""").find.saveAs("categoryId"))
          .check(status.is(200))
          //   .check(bodyString.saveAs("fullBody1"))
      )

        .exec(session => {
          val rand = new Random(System.currentTimeMillis());
          //println(session("status").as[String])
        //  println(session)
          val ProductIDList: List[String] = session("ProductIDAll").as[List[String]]
          val random_index = rand.nextInt(ProductIDList.length-1) + 1;
          val result = ProductIDList(random_index);
          val orderBy: List[String] = session("orderBy").as[List[String]]
          val random_index2 = rand.nextInt(orderBy.length);
          val orderBY = orderBy(random_index2);
          val searchResultsPageNum: List[String] = session("searchResultsPageNum").as[List[String]]
          val mapp = (orderBy zip searchResultsPageNum).toMap
        /*  println(result)
          println(session("StoreID").as[String])
          println(session("catalogID").as[String])*/
          session.set("ProductID",result).set("orderByVal",orderBY).set("searchResultsPageNumVal",mapp(orderBY))

        })





    }

  }
