package Accounts

import com.google.gson.JsonObject
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by 461967 on 7/12/2016.
  */
object PaymentMethod extends AccountsTest{
  val url1 = baseUrlHttps + "/v1/account-service/account/payment"
  val url2 = baseUrlHttps + "/v1/account-service/account/payment-action"
  val body = new JsonObject()
  body.addProperty("brand", "${brand}")
  body.addProperty("is_default", true)
  body.addProperty("month", 2)
  body.addProperty("year", 2020)
  body.addProperty("name", "${name}")
  body.addProperty("number", "${number}")
  val ViewPayment = group("ViewPayment") {
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })
      .exec(http("ViewPayment")
        .get(url1)
        .check(status.is(200)))
      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session.toString(), "Accounts_PaymentMethod")
        session

      })
  }

    val AddPayment = group("AddPayment") {
      feed(PaymentMethods)
        .exec(session => {
        val startTime = System.currentTimeMillis()
        session.set("startTime", startTime)
      })
        .exec(http("AddPayment")
          .post(url2)
            .body(StringBody(body.toString)).asJSON
          .check(status.is(200)))
        .exec(session => {
          val EndTime = System.currentTimeMillis()
          insertGroupResponseTime(session.toString(), "Accounts_AddPaymentMethod")
          session
        })
    }
}
