package LTChains.LTCheckout

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object LTHomePage extends BaseTest{

  val LTHomePage = group("LTHomePage_Transaction") {
      exec(http("HomePage")
        .get(baseUrlHttp)
        .check(status.is(200))
      )

        .exec(session => {
        //  println(session)
          session


        })





    }

  }
