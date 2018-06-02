package TheBayChains.TheBayCheckout

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object TheBayHomePage extends BaseTest{

  val TheBayHomePage = group("HomePage_Transaction") {
      exec(http("HomePage")
        .get(baseUrlHttps)
        .check(currentLocation.saveAs("Test"))

      )

        .exec(session => {
          println(session("Test").as[String])
          session


        })





    }

  }
