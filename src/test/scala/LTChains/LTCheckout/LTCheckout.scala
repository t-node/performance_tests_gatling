package LTChains.LTCheckout

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object LTCheckout extends BaseTest{
val url1 = baseUrlHttps + "/webapp/wcs/stores/servlet/HBCAjaxUpdateXordersCmd"
  val LTCheckout = group("LTCheckout_Transaction") {
      exec(http("ShopCartProcessCmd")
        .get(baseUrlHttps + "/webapp/wcs/stores/servlet/OrderShippingBillingCmd?catalogId=${catalogID}&langId=-1&storeId=${StoreID}&shipmentType=single&country=CA")
        .check(regex("""name="authToken" value="(.*?)"""").saveAs("authToken"))
        //.check(bodyString.saveAs("test"))
        .check(status.is(200))
            )

          .exec(http("HBCAjaxUpdateXordersCmd")
              .post(url1)
              .body(StringBody("bopisEligiblity=N&selectedStoreId=&orderIdForBopis=${orderId}&checkOutRemoveOrderAttr=Y&requesttype=ajax"))
             //.check(bodyString.saveAs("test"))
            .check(status.is(200))
          )

        .exec(http("order-shipping-billing-details.css")
          .get(baseUrlHttps + "/wcsstore/TheBay/css/dist/order-shipping-billing-details.css")
          //.check(bodyString.saveAs("test"))
          .check(status.is(200))
        )

        .exec(session => {
        //  println(session("test").as[String])
         /* println(session("authToken").as[String])
          println(System.currentTimeMillis)*/

          session.set("nickname", System.currentTimeMillis)


        })

  }

  }
