package SaksO5Chains.SaksO5CheckoutChains

import java.io.{FileWriter, PrintWriter}

import BaseTest.BaseTest
import SaksO5Chains.SaksO5CheckoutChains.SaksO5ShoppingBag._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scalaj.http.Http

/**
  * Created by aindana on 3/14/2016.
  */
object SaksO5SubmitOrder extends BaseTest{
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

      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session("startTime").as[Long],EndTime,instance + "SaksO5_SubmitOrder")
        session

      })
      .exec(session =>
      {
        var json: String = "OrderNumber value=" + session("OrderNumber").as[String]
        val result = Http("http://hd1qjra03lx.digital.hbc.com:8086/write")
          .param("db","gatling")
          .postData(json)
          //.header("Content-Type", "application/json")
          .header("Charset", "UTF-8").asString
        session
      }

      )
    }


}
