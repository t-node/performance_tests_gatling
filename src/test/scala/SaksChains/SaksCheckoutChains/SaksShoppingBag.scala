package SaksChains.SaksCheckoutChains

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains.SaksAddToBag._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SaksShoppingBag extends BaseTest{
//  val random = new util.Random
//  val feeder = Iterator.continually(Map("email" -> (random.nextString(20) + "@foo.com")))

  val shoppingBag = baseUrlHttps + "/checkout/saksBag.jsp"

  val SaksshoppingBag = group("shoppingBag_Transaction") {
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

      .exec(http("shoppingBag")
      .get(shoppingBag)
         .queryParam("PRODUCT<>prd_id","${prd_id}")
      .queryParam("FOLDER<>folder_id","${folder_id}")
      .queryParam("bmUID","${bmUID}")
      .check(status.is(200))
     )

      .exec(http("shoppingBag1")
      .post(shoppingBag)
        .body(StringBody("bmForm=initialize_saks_bag_service"))
        .check(regex(""""quantity":${ItemQty}""").exists)
        .check(status.is(200))
    )

      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session("startTime").as[Long],EndTime,"Saks_ShoppingBag")
        session

      })

    }



}
