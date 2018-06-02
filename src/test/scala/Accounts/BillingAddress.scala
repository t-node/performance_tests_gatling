package Accounts

import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by 461967 on 7/12/2016.
  */
object BillingAddress extends AccountsTest{

  val url1 = AddressMethods.CreateAddrUrl("billing")
  val url2 = baseUrlHttps + "/v1/account-service/account/address"
  val ViewBillAddr = group("ViewBillingAddress"){
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })
      .exec(http("ViewBillingAddress")
      .get(url1)
      .check(status.is(200))
    )
      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session.toString(), "Accounts_BillingAddress")
        session

      })
  }

  val AddBillAddr = group("AddBillingAddress"){
    feed(Address)
      .exec(session =>{
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })
      .exec(http("AddBillingAddress")
        .post(url2)
          .body(StringBody(AddressMethods.CreateAddrBody("billing").toString)).asJSON
        .check(status.is(200)))
      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session.toString(), "Accounts_AddBillingAddress")
        session
      })
  }

}
