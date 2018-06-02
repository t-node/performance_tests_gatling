package SaksChains.SaksCheckoutChains

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains.SaksAddToBag._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SaksChangeQty extends BaseTest{
//  val random = new util.Random
//  val feeder = Iterator.continually(Map("email" -> (random.nextString(20) + "@foo.com")))

  val chkUrl = baseUrlHttps + "/checkout/checkout.jsp"

  val ChangeQty = group("ChangeQty_Transaction") {
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

      .exec(http("update_cart_item_quantity_service")
      .post(chkUrl)
      .body(StringBody("bmForm=update_cart_item_quantity_service&cartItemId=${cartID}&itemQuantity=${ChangeItemQty}"))
        .check(regex(""""quantity":${ChangeItemQty}""").exists)
      .check(status.is(200))
      )

      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session("startTime").as[Long],EndTime,"Saks_ChangeQty")
        session

      })



    }



}
