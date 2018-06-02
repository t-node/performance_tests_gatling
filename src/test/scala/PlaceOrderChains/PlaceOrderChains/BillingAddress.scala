package PlaceOrderChains.PlaceOrderChains

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains.SaksAddToBag._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object BillingAddress extends BaseTest{
//  val random = new util.Random
//  val feeder = Iterator.continually(Map("email" -> (random.nextString(20) + "@foo.com")))

  val chkUrl = baseUrlHttps + "/checkout/checkout.jsp"
  val BillingDetails_Transaction = instance + "BillingDetails_Transaction"
  val submit_payment_service = "submit_payment_service"
  val Billing = group(BillingDetails_Transaction) {
    feed(BillingDetailsPO)
      .exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

      .exec(http(submit_payment_service)
      .post(chkUrl)
          .body(StringBody("bmForm=submit_payment_service&CREDIT_CARD%3C%3EcardBrand_cd=VISA&CREDIT_CARD%3C%3EcardNum=4445222299990007&CREDIT_CARD%3C%3EcardholderName=Test&CREDIT_CARD%3C%3EcardMonth_cd=7&CREDIT_CARD%3C%3EcardYear_cd=2017&card_cvNumber=123&ACCOUNT%3C%3EaccountNumber=&ACCOUNT%3C%3EnotificationEmail=&promoCode=&USER_ACCOUNT%3C%3Epassword=&USER_ACCOUNT%3C%3EconfirmPassword=&USER_ACCOUNT%3C%3EATR_passwordHint="))
        //.check(bodyString.saveAs("BillingDetails"))
          .check(regex("""cardName":"${CardHolderName}""").exists)
        .check(status.is(200))
      )
      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session("startTime").as[Long],EndTime,instance + "SaksO5_BillingAddress")
        session

      })

    }



}
