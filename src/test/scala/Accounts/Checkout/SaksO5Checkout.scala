package Accounts.Checkout

import Accounts.AccountsTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by 461967 on 8/29/2016.
  */
object SaksO5Checkout extends AccountsTest{
  //  val random = new util.Random
  //  val feeder = Iterator.continually(Map("email" -> (random.nextString(20) + "@foo.com")))

  val chkUrl = baseUrlHttps + "/checkout/checkout.jsp"
  val Checkout_Transaction = instance + "Checkout_Transaction"
  val login_as_guest_user = "login_as_guest_user"
  val Get_Checkout = "Get_Checkout"
  val continue_to_checkout_service = "continue_to_checkout_service"
  val titles = "get_option_list_service&listName=titles"
  val states = "get_option_list_service&listName=states"
  val SaksCheckout = group(Checkout_Transaction) {
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

//      .exec(http(login_as_guest_user)
//        .post(chkUrl)
//        .body(StringBody("bmForm=login_as_guest_user&LOGIN<>userid="))
//        .check(status.is(200))
//
//        //   .check(regex("""soldOut":false""").exists)
//
//      )

      .exec(http(Get_Checkout)
        .get(chkUrl)
        .check(regex("""value="${ProductID}""").exists)
        .check(status.is(200))
      )

      .exec(http(continue_to_checkout_service)
        .post(chkUrl)
        .body(StringBody("bmForm=continue_to_checkout_service"))
        .check(status.is(200))
      )
      .exec(http(titles)
        .post(chkUrl)
        .body(StringBody("bmForm=get_option_list_service&listName=titles"))
        .check(regex("""Select a Title""").exists)
        .check(status.is(200))
      )
      .exec(http(states)
        .post(chkUrl)
        .body(StringBody("bmForm=get_option_list_service&listName=states"))
        .check(regex("""Choose a State""").exists)
        .check(status.is(200))
      )
      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session("startTime").as[Long],EndTime,instance + "SaksO5_Checkout")
        session

      })


  }



}
