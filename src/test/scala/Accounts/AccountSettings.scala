package Accounts

import io.gatling.core.Predef._
import io.gatling.http.Predef._


/**
  * Created by 461967 on 7/12/2016.
  */
object AccountSettings extends AccountsTest{
  val url1 = baseUrlHttps + "/v1/account-service/account/settings"
  val ViewAccountSettings = group("ViewAccountSettings"){
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })
      .exec(http("ViewAccountSettings")
      .get(url1)
      .check(status.is(200))
    )
      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session.toString(), "Accounts_AccountSettings")
        session

      })
  }
}
