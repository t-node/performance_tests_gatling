package LTChains.LTCheckout

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object LTBilling extends BaseTest{
  val url1 = baseUrlHttps + "/webapp/wcs/stores/servlet/HBCPIAddCmd"
  val url2 = baseUrlHttps + "/webapp/wcs/stores/servlet/AjaxTenderType"
  val url3 = baseUrlHttps + "/webapp/wcs/stores/servlet/TraditionalAjaxShippingDetailsURL"
  val url4 = baseUrlHttps + "/webapp/wcs/stores/servlet/CurrentOrderInformationView"
  val url5 = baseUrlHttps + "/webapp/wcs/stores/servlet/CreditAmount"
  val url6 = baseUrlHttps + "/webapp/wcs/stores/servlet/HBCPIAddCmd"
  val url7 = baseUrlHttps + "/webapp/wcs/stores/servlet/AjaxOrderProcessServiceOrderPrepare"
  val url8 = baseUrlHttps + "/webapp/wcs/stores/servlet/OrderShippingBillingSummaryView"

  val LTBilling = group("LTBilling_Transaction") {
      exec(http("HBCPIAddCmd")
        .post(url1)
          .formParam("storeId","${StoreID}")
        .formParam("catalogId","${catalogID}")
        .formParam("langId","-1")
        .formParam("orderId","${orderId}")
        .formParam("payMethodId","VISA")
        .formParam("ordersubmit","0")
        .formParam("checkorder","0")
        .formParam("url","HBCPIAddCmd")
        .formParam("requesttype","ajax")
        .check(status.is(200))
        //.check(bodyString.saveAs("test"))

            )

          .exec(http("AjaxTenderType")
              .post(url2)
            .formParam("orderId","${orderId}")
            .formParam("requesttype","ajax")
            .check(status.is(200))
            //    .check(bodyString.saveAs("fullBody"))
          )

        .exec(session => {
          //  println(session("test").as[String])
       //   println(session("fullBody").as[String])
       //   println(session("nickname").as[String])
     //    println(System.currentTimeMillis)

          session


        })


        .exec(http("TraditionalAjaxShippingDetailsURL")
          .post(url3)
          .queryParam("storeId","${StoreID}")
          .queryParam("catalogId","${catalogID}")
          .queryParam("langId","-1")
          .formParam("shipmentDetailsArea","update")
          .formParam("objectId","")
          .formParam("requesttype","ajax")
          .check(status.is(200))
        )

        .exec(http("CurrentOrderInformationView")
          .post(url4)
          .queryParam("storeId","${StoreID}")
          .queryParam("catalogId","${catalogID}")
          .queryParam("langId","-1")
          .body(StringBody("objectId=&requesttype=ajax"))
          .check(status.is(200))
        )



        .exec(http("CreditAmount")
          .post(url5)
          .queryParam("piAmount","${total}")
          .queryParam("catalogId","${catalogID}")
          .queryParam("langId","-1")
          .queryParam("CurrencySymbolToFormat","$")
          .queryParam("paymentAreaNumber","1")
          .queryParam("storeId","${StoreID}")
          .queryParam("amount","${total}")
          .queryParam("refresh","R")
          .body(StringBody("objectId=&requesttype=ajax"))
          .check(status.is(200))

        )

        .exec(http("CreditAmount_2")
          .post(url5)
          .queryParam("piAmount","${total}")
          .queryParam("catalogId","${catalogID}")
          .queryParam("langId","-1")
          .queryParam("CurrencySymbolToFormat","$")
          .queryParam("paymentAreaNumber","1")
          .queryParam("storeId","${StoreID}")
          .queryParam("amount","${total}")
          .queryParam("refresh","R")
          .body(StringBody("objectId=&requesttype=ajax"))
          .check(status.is(200))

        )

        .exec(http("HBCPIAddCmd")
          .post(url6)
          .formParam("storeId","${StoreID}")
          .formParam("catalogId","${catalogID}")
          .formParam("langId","-1")
          .formParam("orderId","${orderId}")
          .formParam("payMethodId","VISA")
          .formParam("expire_month","05")
          .formParam("expire_year","2017")
          .formParam("account","4445222299990007")
          .formParam("cc_cvc","123")
          .formParam("url","HBCPIAddCmd")
          .formParam("cc_brand","VISA")
          .formParam("billing_address_id","${addressID}")
          .formParam("CCpiAmount","${total}")
          .formParam("checkorder","0")
          .formParam("requesttype","ajax")
          .check(status.is(200))


        )



        .exec(http("AjaxOrderProcessServiceOrderPrepare")
          .post(url7)
          .formParam("storeId","${StoreID}")
          .formParam("catalogId","${catalogID}")
          .formParam("langId","-1")
          .formParam("orderId","${orderId}")
          .formParam("OrderTotal","${total}")
          .formParam("notifyMerchant","1")
          .formParam("notifyShopper","1")
          .formParam("notifyOrderSubmitted","1")
          .formParam("requesttype","ajax")
          .check(status.is(200))
        )





        .exec(http("OrderShippingBillingSummaryView")
          .get(url8)
          .queryParam("storeId","${StoreID}")
          .queryParam("catalogId","${catalogID}")
          .queryParam("langId","-1")
          .queryParam("orderId","${orderId}")
          .queryParam("shipmentTypeId","1")
          .check(status.is(200))


        )



        .exec(session => {
        //  println(session("test").as[String])
      //    println(session("url").as[String])
          session


        })

  }

  }
