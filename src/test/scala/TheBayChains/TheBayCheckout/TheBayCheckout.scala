package TheBayChains.TheBayCheckout

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object TheBayCheckout extends BaseTest{
val url1 = baseUrlHttps + "/webapp/wcs/stores/servlet/HBCAjaxUpdateXordersCmd"
  val TheBayCheckout = group("TheBayCheckout_Transaction") {
      exec(http("ShopCartProcessCmd")
        .get(baseUrlHttps + "/webapp/wcs/stores/servlet/OrderShippingBillingCmd?catalogId=${catalogID}&langId=-24&storeId=${StoreID}&shipmentType=single&country=CA")
        .check(regex("""name="authToken" value="(.*?)"""").saveAs("authToken"))
        //.check(bodyString.saveAs("test"))

            )

          .exec(http("HBCAjaxUpdateXordersCmd")
              .post(url1)
              .body(StringBody("bopisEligiblity=N&selectedStoreId=&orderIdForBopis=${orderId}&checkOutRemoveOrderAttr=Y&requesttype=ajax"))
            .check(status.is(200))
             //.check(bodyString.saveAs("test"))

          )

        .exec(http("order-shipping-billing-details.css")
          .get(baseUrlHttps + "/wcsstore/TheBay/css/dist/order-shipping-billing-details.css")
          .check(status.is(200))
          //.check(bodyString.saveAs("test"))

        )

        .exec(session => {
        //  println(session("test").as[String])
       /*   println(session("authToken").as[String])
          println(System.currentTimeMillis)
*/
          session.set("nickname", System.currentTimeMillis)


        })

  }

  }
