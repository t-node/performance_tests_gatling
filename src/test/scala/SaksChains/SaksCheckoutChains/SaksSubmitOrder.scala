package SaksChains.SaksCheckoutChains

import java.io.{FileWriter, PrintWriter}

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SaksSubmitOrder extends BaseTest{
//  val random = new util.Random
//  val feeder = Iterator.continually(Map("email" -> (random.nextString(20) + "@foo.com")))

/*
  val chkUrl = baseUrlHttps + "/checkout/checkout.jsp"

  val SubmitOrder = group("SubmitOrder_Transaction") {
    exec(http("submit_order_service")
      .post(chkUrl)
      .body(StringBody("bmForm=submit_order_service&&"))
      //.check(bodyString.saveAs("OrderNumber"))
      .check(regex("""orderNumber":"(\d+)""").saveAs("OrderNumber"))
      .check(status.is(200))

      )

      .exec(session =>
      {
        println("ORDER NUMBER")
      //  new FileWriter("target/Orders.txt",true) { write(session("OrderNumber").as[String] + "\n"); close }
        // println(session.attributes)
     //   println(session("gatling.http.referer").as[String])
        println(session("OrderNumber").as[String])
        session
      }

      )
    }


*/

}
