package Accounts.SignIn

import Accounts.AccountsTest
import com.google.gson.JsonObject
import io.gatling.core.Predef._
import io.gatling.http.Predef._


/**
  * Created by 461967 on 5/17/2016.
  */
object SignIn extends AccountsTest {
  val url1 = baseUrlHttp + "/account/new"
  val url2 = baseUrlHttps + "/v1/account-service/sign-in-action"
  val url3 = baseUrlHttps + "/v1/account-service/account/summary"
  val body = new JsonObject()
  body.addProperty("username", "${username}")
  body.addProperty("password", "${password}")
  val openSignInPage = group("OpenSignInPage"){
      exec(session => {
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })
      .exec(http("OpenSignInPage")
        .get(url1)
        .check(status.is(200))
      )
      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session.toString(), "Accounts_OpenSignInPage")
        session
      })
  }

  val performSignIn = group("PerformSignIn"){
    feed(UserCredentials)
      .exec(session => {
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })
      .exec(http("PerformSignIn")
        .post(url2)
        .body(StringBody(body.toString)).asJSON
        .check(status.is(200))
      )
      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session.toString(),"Accounts_SignInAction")
        session
      })
  }

  val getAccountSummary = group("GetAccountSummary") {
      exec(session => {
        val startTime = System.currentTimeMillis()
        session.set("startTime", startTime)
      })
      .exec(http("GetAccSummary")
        .get(url3)
        .check(status.is(200))
      )
      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session.toString(),"Accounts_GetAccountSummary")
        session
      })
  }
}
