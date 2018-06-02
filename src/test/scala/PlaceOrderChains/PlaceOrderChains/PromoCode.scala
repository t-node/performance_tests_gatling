package PlaceOrderChains.PlaceOrderChains

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object PromoCode extends BaseTest{
//  val random = new util.Random
//  val feeder = Iterator.continually(Map("email" -> (random.nextString(20) + "@foo.com")))

  val chkUrl = baseUrlHttps + "/checkout/checkout.jsp"
  val Promo_Transaction = instance + "Promo_Transaction"
  val PromoCode = group(Promo_Transaction) {
      exec(http("PromoCode")
      .post(chkUrl)
      .body(StringBody("bmForm=applyPromoSaksBag&promoCode=${PromoCode}"))
        .check(regex("""${PromoCode}""").exists)
      .check(status.is(200))
      )


    }



}
