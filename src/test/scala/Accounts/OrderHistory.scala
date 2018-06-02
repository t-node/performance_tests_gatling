package Accounts

import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by 461967 on 6/28/2016.
  */
object OrderHistory extends AccountsTest{

  val url1 = baseUrlHttps + "/v1/account-service/account/order-history?page_num=1"
  val ViewOrderHistory = group("ViewOrderHistory"){
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })
      .exec(http("OpenOrderHistoryPage")
        .get(url1)
        .check(status.is(200))
    )
      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session.toString(), "Accounts_OrderHistory")
        session

      })
  }
}
