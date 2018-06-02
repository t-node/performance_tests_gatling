package SaksO5Chains.SaksO5CheckoutChains

import java.io.{FileOutputStream, OutputStreamWriter, _}
import java.text.SimpleDateFormat
import java.util.Calendar

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scalaj.http.Http

/**
  * Created by aindana on 3/14/2016.
  */
object SaksO5SubmitOrderChrome extends BaseTest{
  //  val random = new util.Random
  //  val feeder = Iterator.continually(Map("email" -> (random.nextString(20) + "@foo.com")))
  val SubmitOrder_Transaction = instance + "SubmitOrder_Transaction"
  val submit_order_service = "submit_order_service"
  val chkUrl = baseUrlHttps + "/checkout/checkout.jsp"

  val SubmitOrder = group(SubmitOrder_Transaction) {
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

      .exec(http(submit_order_service)
        .post(chkUrl)
        .body(StringBody("bmForm=submit_order_service&&"))
        //.check(bodyString.saveAs("OrderNumber"))
        .check(regex("""orderNumber":"(\d+)""").saveAs("OrderNumber"))
        .check(status.is(200))

      )
/*
      .exec(http("Omniture")
        .post("https://s.thebrighttag.com/tag")
        .queryParam("site","${signalKey}")
        .queryParam("docReferrer","${checkoutUrl2}")
        .queryParam("H","-4h45fc")
        .queryParam("referrer","${referer}#init1")
        .queryParam("mode","v2")
        .queryParam("cf","2215729,3954754,3954887,3954888,3954891,3954898,3954901,3954908,3954909,3954923,3954926,3954930")
        .queryParam("_cb_bt_data('order shipping country')","US")
        .queryParam("_cb_bt_data('products array')","[{\"brand\":\"Equipment\",\"name\":\"Valentino by Mario Valentino\",\"color\":\"MALBEC\",\"size\":\"XS\",\"quantity\":\"1\",\"price\":\"99.99\",\"discount\":\"-218.01\",\"id\":\"${ProductID}\",\"product_url\":\"${checkoutUrl2}\",\"product_image_url\":\"https://image.s5a.com/is/image/saksoff5th/${ProductID}_75x100.jpg\",\"selected_sku\":\"${sku_id}\",\"gift_wrap_amount\":\"\",\"gift_wrap_type\":\"\",\"ship_from_store\":\"false\",\"ship_to_country\":\"US\",\"ship_to_state\":\"${State}\",\"ship_to_zip\":\"${Zip}\",\"shipping_method\":\"Standard\",\"availability_dc\":\"3\",\"availability_store\":\"0\"}]")
        .queryParam("_cb_bt_data('order billing state')","${State}")
        .queryParam("_cb_bt_data('order billing zip code')","${Zip}")
        .queryParam("_cb_bt_data('order billing country')","US")
        .queryParam("_cb_bt_data('order shipping zip code')","${Zip}")
        .queryParam("_cb_bt_data('order shipping state')","${State}")
        .queryParam("_cb_bt_data('loyalty id')","N")
        .queryParam("_cb_bt_data('order amount')","${grandTotal}")
        .queryParam("_cb_bt_data('tellapart guest id')","")
        .queryParam("_cb_bt_data('order id')","${OrderNumber}")
        .queryParam("_cb_bt_data('promo code')","")
        .check(bodyString.saveAs("fullResp"))
        .check(status.is(200))
      )*/

      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session.toString,instance + "SaksO5_SubmitOrder")
        session

      })
      .exec(session =>
      {
        /*var json: String = "OrderNumber value=" + session("OrderNumber").as[String]
        val result = Http("http://hd1qjra03lx.digital.hbc.com:8086/write")
          .param("db","gatling")
          .postData(json)
          //.header("Content-Type", "application/json")
          .header("Charset", "UTF-8").asString*/
        println("Response: " + session("fullResp").as[String])

        session
      }

      )



      .exec(session =>
      {
        println("=================================")
        println("OrderNumber: " + session("OrderNumber").as[String])
        println("grandTotal: " + session("grandTotal").as[String])
        println("Browser: Chrome")
        println("CardBrand: " + session("CardBrand").as[String])
        val now = Calendar.getInstance().getTime()
        val minuteFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm")
        val currentDate = minuteFormat.format(now)
        println("Time: " + currentDate)
        println("=================================")

        session
      }

      )
  }


}
