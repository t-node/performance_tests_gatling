package Accounts

import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by 461967 on 7/12/2016.
  */
object ShippingAddress extends AccountsTest{
  val url1 = AddressMethods.CreateAddrUrl("shipping")
  val url2 = baseUrlHttps + "/v1/account-service/account/address"
  val viewShipAddr = group("ViewShippingAddress"){
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })
      .exec(http("ViewShippingAddress")
      .get(url1)
        .check(status.is(200)))
      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session.toString(), "Accounts_ShippingAddress")
        session
      })
  }

  val AddShipAddr = group("AddShippingAddress"){
    feed(Address)
      .exec(session =>{
        val startTime = System.currentTimeMillis()
        session.set("startTime", startTime)
      })
      .exec(http("AddShippingAddress")
        .post(url2)
        .body(StringBody(AddressMethods.CreateAddrBody("shipping").toString)).asJSON
        .check(status.is(200)))
      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session.toString(), "Accounts_AddShippingAddress")
        session
      })
  }
}
