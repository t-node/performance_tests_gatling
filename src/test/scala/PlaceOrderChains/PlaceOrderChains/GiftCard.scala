package PlaceOrderChains.PlaceOrderChains

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object GiftCard extends BaseTest{
//  val random = new util.Random
//  val feeder = Iterator.continually(Map("email" -> (random.nextString(20) + "@foo.com")))

  val chkUrl = baseUrlHttps + "/checkout/checkout.jsp"
  val GC_Transaction = instance + "GC_Transaction"
  val GC = group(GC_Transaction) {
      exec(http("GC")
      .post(chkUrl)
      .body(StringBody("bmForm=add_gift_card_service&CREDIT_CARD<>cardBrand_cd=&CREDIT_CARD<>cardNum=&CREDIT_CARD<>cardholderName=&CREDIT_CARD<>cardMonth_cd=&CREDIT_CARD<>cardYear_cd=&card_cvNumber=&ACCOUNT<>accountNumber=6015990000316325&ACCOUNT<>notificationEmail=9714"))
        .check(status.is(200))
      )


    }



}
