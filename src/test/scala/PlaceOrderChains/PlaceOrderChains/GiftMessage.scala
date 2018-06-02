package PlaceOrderChains.PlaceOrderChains

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object GiftMessage extends BaseTest{
//  val random = new util.Random
//  val feeder = Iterator.continually(Map("email" -> (random.nextString(20) + "@foo.com")))

  val chkUrl = baseUrlHttps + "/checkout/checkout.jsp"
  val Gift_Transaction = instance + "GiftMsg_Transaction"
  val GiftMsg = group(Gift_Transaction) {
      exec(http("giftWrapTypes")
      .post(chkUrl)
      .body(StringBody("bmForm=get_option_list_service&listName=giftWrapTypes"))
      .check(status.is(200))
      )

      .exec(http("giftMessage")
        .post(chkUrl)
        .body(StringBody("bmForm=update_cart_item_gifting_service&giftingName=Test&giftingMsg1=Test&giftingMsg2=&giftingMsg3=&giftingMsg4=&giftingType=N&cartItemId=${cartID}"))
        .check(status.is(200))
      )


    }



}
