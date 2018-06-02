package Google

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object Google extends BaseTest{

  val Google = group("Google_Transaction") {
    forever(
      pace(2)
        .exec(http("google")
        .get("https://www.google.com")
        .check(status.is(200))

      )


)
  }

  }
