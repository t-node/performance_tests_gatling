package SaksChains.SaksCheckoutChains

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SaksHomePage extends BaseTest{

  val SaksHome = group("HomePage_Transaction") {
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
     val timestamp = System.currentTimeMillis()
      session.set("timestamp", timestamp)
    })

      .exec(http("HomePage")
      .get(baseUrlHttp + "/Entry.jsp")
          .check(responseTimeInMillis.saveAs("responseTime1"))
     //   .header("LoadTesting","Gatling")
      //.check(css("input[name='SearchString']", "value").exists)
      .check(regex("""bmUID=(\w+)""").saveAs("bmUID"))
   //   .check(regex("""name="bmFormID" value="(\w+)"""").saveAs("bmFormID"))
        .check(regex("""name="bmUID" value="(.*)"""").saveAs("bmUID"))
        .check(regex("""name="bmFormID" value="(.*)"""").saveAs("bmFormID"))
        .check(regex("""name="sid" value="(.*)"""").saveAs("sid"))
        .check(status.is(200))
    //  .check(bodyString.saveAs("fullBody1"))
    )

      .exec(http("EML1145Acollect.jsp")
      .get(baseUrlHttp + "/email_popup/EML1145Acollect.jsp")
        .check(responseTimeInMillis.saveAs("responseTime2"))
        .check(status.is(200))


     )

      .exec(session => {
         val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session.toString,"HomePage_Transaction")
        session

      })

    }


  }
