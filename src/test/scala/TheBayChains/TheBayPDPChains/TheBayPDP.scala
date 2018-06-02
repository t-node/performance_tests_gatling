package TheBayChains.TheBayPDPChains

import java.util.Random

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object TheBayPDP extends BaseTest{

  val TheBayPDP = group("TheBayPDP_Transaction") {
      exec(http("PDP Search")
        .get(baseUrlHttps + "/webapp/wcs/stores/servlet/en/SearchDisplay?storeId=${StoreID}&catalogId=${catalogID}&langId=-24&pageSize=12&beginIndex=0&sType=SimpleSearch&resultCatEntryType=2&showResultsPage=true&pageView=image&searchSource=Q&searchTerm=${ProductID}&x=0&y=0")
          .check(regex("""No Results for""").count.saveAs("NoResults"))
          .check(regex("""Search Results for""").count.saveAs("MultipleResults"))
          .check(regex("""canonical" href="(.*?)"""").saveAs("Url"))
          .check(regex("""offerPrice" : "(.*?)"""").saveAs("offerPrice"))
          .check(regex("""catentry_id" : "(\d+)""").saveAs("catentry_id"))
        .check(status.is(200))
      )

      .exec(http("PDP Search Url")
      .get("${Url}")
          .check(status.is(200))
     // .check(regex("""${ProductID}""").exists)

    )

        .exec(session => {
    //      println(session("Url").as[String])
          session
        })





    }

  }
