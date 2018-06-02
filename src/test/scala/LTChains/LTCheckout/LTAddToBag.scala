package LTChains.LTCheckout

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object LTAddToBag extends BaseTest{
  val url1 = baseUrlHttp + "/webapp/wcs/stores/servlet/AjaxOrderChangeServiceItemAdd"
  val url2 = baseUrlHttp + "/webapp/wcs/stores/servlet/en/thebay/AjaxPromoGiftItemView"
  val url3 = baseUrlHttp + "/webapp/wcs/stores/servlet/en/thebay/AjaxQuickCartDisplay"
  val url4 = baseUrlHttp + "/webapp/wcs/stores/servlet/PromotionFreeGiftChoicesView"
  val LTAddToBag = group("LTAddToBag_Transaction") {
      exec(http("AjaxOrderChangeServiceItemAdd")
        .post(url1)
        .formParam("storeId","${StoreID}")
        .formParam("catalogId","${catalogID}")
        .formParam("langId","-1")
        .formParam("orderId",".")
        .formParam("calculationUsage","-1,-2,-5,-6,-7")
        .formParam("price","${offerPrice}")
        .formParam("intlNonUsFlag","N")
        .formParam("catEntryId","${catentry_id}")
        .formParam("quantity","1")
        .formParam("tagData","")
        .formParam("requesttype","ajax")
          .check(regex("""orderItemId": \["(\d+)""").saveAs("orderItemId"))
          .check(regex("""orderId": \["(\d+)""").saveAs("orderId"))
        .check(status.is(200))
      )

        .exec(session => {
       //   val url2 = baseUrlHttp + "/webapp/wcs/stores/servlet/en/thebay/AjaxPromoGiftItemView"
          val url3 = baseUrlHttp + "/webapp/wcs/stores/servlet/en/thebay/AjaxQuickCartDisplay?storeId=${StoreID}&catalogId=${catalogID}&langId=-1"
          val url4 = baseUrlHttp + "/webapp/wcs/stores/servlet/PromotionFreeGiftChoicesView?catalogId=${catalogID}&langId=-1&storeId=${StoreID}"
          println(session("orderItemId").as[String])
          session
        })

        .exec(http("AjaxPromoGiftItemView")
          .post(url2)
            .queryParam("orderItemId","${orderItemId}")
          .queryParam("storeId","${StoreID}")
          .queryParam("catalogId","${catalogID}")
          .queryParam("langId","-1")
          .body(StringBody("objectId=&requesttype=ajax"))
          .check(status.is(200))
         )

        .exec(http("AjaxQuickCartDisplay")
          .post(url3)
          .queryParam("storeId","${StoreID}")
          .queryParam("catalogId","${catalogID}")
          .queryParam("langId","-1")
          .body(StringBody("addedOrderItemId=${orderItemId}&objectId=&requesttype=ajax"))
          .check(status.is(200))
        )

        .exec(http("PromotionFreeGiftChoicesView")
          .post(url4)
          .queryParam("storeId","${StoreID}")
          .queryParam("catalogId","${catalogID}")
          .queryParam("langId","-1")
          .body(StringBody("rewardOptionID=&unacceptedOffers=&isShoppingCartPage=false&objectId=&requesttype=ajax"))
          .check(status.is(200))
        )

      .exec(http("ShopCartProcessCmd")
      .get(baseUrlHttp + "/webapp/wcs/stores/servlet/ShopCartProcessCmd?catalogId=${catalogID}&orderId=.&langId=-1&storeId=${StoreID}")
        .check(regex("""OrderItemDisplay.jsp""").exists)
        .check(status.is(200))
        //.check(bodyString.saveAs("test"))

    )


    }

  }
