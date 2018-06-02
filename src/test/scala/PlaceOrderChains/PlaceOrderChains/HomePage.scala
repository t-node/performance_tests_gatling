package PlaceOrderChains.PlaceOrderChains

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object HomePage extends BaseTest{
  val HomePage_Transaction = instance + "HomePage_Transaction"
  val HomePage = "HomePage"
  val EML1145Acollect = "EML1145Acollect.jsp"
  val SaksHome = group(HomePage_Transaction) {
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
     val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

      .exec(http(HomePage)
      .get(baseUrlHttp + "/Entry.jsp")
          .check(responseTimeInMillis.saveAs("responseTime1"))
     //   .header("LoadTesting","Gatling")
      //.check(css("input[name='SearchString']", "value").exists)
      .check(regex("""bmUID=(.*)""").saveAs("bmUID"))
   //   .check(regex("""name="bmFormID" value="(\w+)"""").saveAs("bmFormID"))
      .check(regex("""name="bmUID" value="(.*)"""").saveAs("bmUID"))
      .check(regex("""name="bmFormID" value="(.*)"""").saveAs("bmFormID"))
      .check(regex("""name="sid" value="(.*)"""").saveAs("sid"))
        .check(status.is(200))
    //  .check(bodyString.saveAs("fullBody1"))
    )

      .exec(session => {
         val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session("startTime").as[Long],EndTime,instance + "SaksO5_HomePage")
        session

      })

    }


  }
