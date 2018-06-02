package TheBayChains.TheBayCheckout

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object TheBayShipping extends BaseTest{
val url1 = baseUrlHttps + "/webapp/wcs/stores/servlet/HBCStorefrontAssetStore/qas/qas_proxy.jsp"
  val url2 = baseUrlHttps + "/webapp/wcs/stores/servlet/AjaxPersonChangeServiceAddressAdd"
  val url3 = baseUrlHttps + "/webapp/wcs/stores/servlet/AjaxOrderChangeServiceShipInfoUpdate"
  val url4 = baseUrlHttps + "/webapp/wcs/stores/servlet/HBCPIRemoveCmd"
  val url5 = baseUrlHttps + "/webapp/wcs/stores/servlet/CreditAmount"
  val url6 = baseUrlHttps + "/webapp/wcs/stores/servlet/GC1Amount"
  val url7 = baseUrlHttps + "/webapp/wcs/stores/servlet/AjaxTenderType"
  val url8 = baseUrlHttps + "/webapp/wcs/stores/servlet/GC2Amount"
  val url9 = baseUrlHttps + "/webapp/wcs/stores/servlet/TraditionalAjaxShippingDetailsURL"
  val url10 = baseUrlHttps + "/webapp/wcs/stores/servlet/CurrentOrderInformationView"
  val TheBayShipping = group("TheBayShipping_Transaction") {
      exec(http("qas")
        .post(url1)
          .formParam("action","search")
        .formParam("addlayout","Database layout")
        .formParam("country","CAN")
        .formParam("searchstring","981 East St|||CUMBERLAND|ON|")
        //.check(bodyString.saveAs("test"))
        .check(status.is(200))
            )

          .exec(http("AjaxPersonChangeServiceAddressAdd")
              .post(url2)
            .formParam("storeId","${StoreID}")
            .formParam("catalogId","${catalogID}")
            .formParam("langId","-24")
            .formParam("status","Billing")
            .formParam("addressType","ShippingAndBilling")
            .formParam("authToken","${authToken}")
            .formParam("nickName","${nickname}")
            .formParam("phone1","2123335555")
            .formParam("zipCode","K4C 1R2")
            .formParam("curentstate","")
            .formParam("uKEnableFlagforEditAddress","false")
            .formParam("emptyaddress","")
            .formParam("firstName","Test")
            .formParam("lastName","Test")
            .formParam("address1","981 East St")
            .formParam("address2","")
            .formParam("city","CUMBERLAND")
            .formParam("country","CA")
            .formParam("state","ON")
            .formParam("state","")
            .formParam("zipCode2","K4C 1R2")
            .formParam("bphone5","212")
            .formParam("bphone6","333")
            .formParam("bphone7","5555")
            .formParam("bphone8","")
            .formParam("ukphone","")
            .formParam("userField1","")
            .formParam("email1","test@aj.com")
            .formParam("fax2","")
            .formParam("fax1","${StoreID}")
            .formParam("requesttype","ajax")
            .check(regex("""addressId": \["(\d+)""").saveAs("addressID"))
            .check(regex("""userId": \["(\d+)""").saveAs("userId"))
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


        .exec(http("AjaxOrderChangeServiceShipInfoUpdate")
          .post(url3)
          .body(StringBody("storeId=${StoreID}&catalogId=${catalogID}&langId=-24&orderId=.&calculationUsage=-1%2C-2%2C-3%2C-4%2C-5%2C-6%2C-7&allocate=***&backorder=***&remerge=***&check=*n&addressId=${addressID}&requesttype=ajax"))
          .check(regex("""orderId": \["(\d+)""").saveAs("orderId"))
          .check(bodyString.saveAs("fullBody"))
          //.check(regex("""orderItemId": \["(\d+)""").saveAs("orderItemId"))
          .check(status.is(200))
        )

        .exec(http("OrderShippingBillingCmd")
          .get(baseUrlHttps + "/webapp/wcs/stores/servlet/OrderShippingBillingCmd?orderId=${orderId}&langId=-24&storeId=${StoreID}&catalogId=${catalogID}&showRegTag=T")
          .check(regex("""GrandOrderTotal" value='(.*?)'""").saveAs("total"))
          .check(status.is(200))
        )

        .exec(http("HBCPIRemoveCmd")
          .post(url4)
          .body(StringBody("url=HBCPIRemoveCmd&pitype=all&orderid=${orderId}&requesttype=ajax"))
          .check(status.is(200))

        )

        .exec(http("CreditAmount")
          .post(url5)
          .queryParam("piAmount","${total}")
          .queryParam("catalogId","${catalogID}")
          .queryParam("langId","-24")
          .queryParam("CurrencySymbolToFormat","$")
          .queryParam("paymentAreaNumber","1")
          .queryParam("storeId","${StoreID}")
          .body(StringBody("objectId=&requesttype=ajax"))
          .check(status.is(200))

        )

        .exec(http("GC1Amount")
          .post(url6)
          .body(StringBody("objectId=&requesttype=ajax"))
          .check(status.is(200))

        )

        .exec(http("GC1Amount")
          .post(url6)
          .body(StringBody("objectId=&requesttype=ajax"))
          .check(status.is(200))

        )
        .exec(http("AjaxTenderType")
          .post(url7)
          .body(StringBody("orderId=${orderId}&requesttype=ajax"))
          .check(status.is(200))

        )
        .exec(http("CreditAmount_2")
          .post(url5)
          .queryParam("piAmount","${total}")
          .queryParam("catalogId","${catalogID}")
          .queryParam("langId","-24")
          .queryParam("CurrencySymbolToFormat","$")
          .queryParam("paymentAreaNumber","1")
          .queryParam("storeId","${StoreID}")
          .queryParam("amount","undefined")
          .body(StringBody("objectId=&requesttype=ajax"))
          .check(status.is(200))

        )

        .exec(http("GC1Amount")
          .post(url6)
          .queryParam("piAmount","")
          .body(StringBody("objectId=&requesttype=ajax"))
          .check(status.is(200))

        )

        .exec(http("GC2Amount")
          .post(url8)
          .queryParam("piAmount","")
          .body(StringBody("objectId=&requesttype=ajax"))
          .check(status.is(200))

        )

        .exec(http("TraditionalAjaxShippingDetailsURL")
          .post(url9)
          .queryParam("storeId","${StoreID}")
          .queryParam("catalogId","${catalogID}")
          .queryParam("langId","-24")
          .body(StringBody("shipmentDetailsArea=update&objectId=&requesttype=ajax"))
          .check(status.is(200))

        )

        .exec(http("CurrentOrderInformationView")
          .post(url10)
          .queryParam("storeId","${StoreID}")
          .queryParam("catalogId","${catalogID}")
          .queryParam("langId","-24")
          .queryParam("orderId","")
          .queryParam("isFromPayment","true")
          .body(StringBody("objectId=&requesttype=ajax"))
          .check(status.is(200))

        )


        .exec(http("CreditAmount_3")
          .post(url5)
          .queryParam("piAmount","${total}")
          .queryParam("catalogId","${catalogID}")
          .queryParam("langId","-24")
          .queryParam("CurrencySymbolToFormat","$")
          .queryParam("paymentAreaNumber","1")
          .queryParam("storeId","${StoreID}")
          .queryParam("amount","${total}")
          .body(StringBody("objectId=&requesttype=ajax"))
          .check(status.is(200))

        )

        .exec(http("CreditAmount_4")
          .post(url5)
          .queryParam("piAmount","${total}")
          .queryParam("catalogId","${catalogID}")
          .queryParam("langId","-24")
          .queryParam("CurrencySymbolToFormat","$")
          .queryParam("paymentAreaNumber","1")
          .queryParam("storeId","${StoreID}")
          .queryParam("amount","${total}")
          .body(StringBody("objectId=&requesttype=ajax"))
          .check(status.is(200))

        )


        .exec(session => {
        //  println(session("test").as[String])
      //    println(session("url").as[String])
          session


        })

  }

  }
