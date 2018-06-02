package SaksChains.SaksCheckoutChains

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains.SaksAddToBag._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SaksCheckout extends BaseTest{
//  val random = new util.Random
//  val feeder = Iterator.continually(Map("email" -> (random.nextString(20) + "@foo.com")))

  val chkUrl = baseUrlHttps + "/checkout/checkout.jsp"

  val SaksCheckout = group("Checkout_Transaction") {
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

      .exec(http("login_as_guest_user")
      .post(chkUrl)
        .body(StringBody("bmForm=login_as_guest_user&LOGIN<>userid="))
      .check(status.is(200))

     //   .check(regex("""soldOut":false""").exists)

      )

      .exec(http("Get_Checkout")
      .get(chkUrl)
      .check(regex("""value="${ProductID}""").exists)
        .check(status.is(200))
    )

      .exec(http("continue_to_checkout_service")
        .post(chkUrl)
        .body(StringBody("bmForm=continue_to_checkout_service"))
          .check(status.is(200))
      )
      .exec(http("get_option_list_service&listName=titles")
        .post(chkUrl)
        .body(StringBody("bmForm=get_option_list_service&listName=titles"))
        .check(regex("""Select a Title""").exists)
        .check(status.is(200))
      )
      .exec(http("get_option_list_service&listName=states")
        .post(chkUrl)
        .body(StringBody("bmForm=get_option_list_service&listName=states"))
        .check(regex("""Choose a State""").exists)
        .check(status.is(200))
      )
      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session("startTime").as[Long],EndTime,"Saks_Checkout")
        session

      })


  }



}
