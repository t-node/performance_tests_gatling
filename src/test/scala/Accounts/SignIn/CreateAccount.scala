package Accounts.SignIn

import Accounts.AccountsTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import com.google.gson._

import scala.util.parsing.json.JSONObject

/**
  * Created by 461967 on 6/22/2016.
  */
object CreateAccount extends AccountsTest {
  val url1 = baseUrlHttps + "/v1/account-service/register"
  val url2 = baseUrlHttps + "/v1/account-service/register-action"
  val url3 = baseUrlHttps + "/v1/account-service/account/summary"
  val body = new JsonObject()
  body.addProperty("first_name", "fname")
  body.addProperty("last_name", "lname")
  body.addProperty("email", "${email}")
  body.addProperty("password", "test123")
  body.addProperty("confirm_password", "test123")
  body.addProperty("phone_number", "")
  body.addProperty("zip", "")
  body.addProperty("canadian_customer", "F")
  body.addProperty("saks_opt_status", "T")
  body.addProperty("off5th_opt_status", "T")
  body.addProperty("saks_canada_opt_status", "T")
  body.addProperty("off5th_canada_opt_status", "T")
  val CreateAccTransaction = group("CreateAccTransaction") {
    feed(UserEmail)
      .exec(session => {
        val startTime = System.currentTimeMillis()
        session.set("startTime", startTime)
      })
      .exec(http("OpenCreateAccPage")
      .get(url1)
      .check(status.is(200))
    )
      .exec(http("CreateAccAction")
        .post(url2)
        .body(StringBody(body.toString)).asJSON
        .check(status.is(200))
      )
      .exec(http("GetAccSummary")
        .get(url3)
        .check(status.is(200))
      )
      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session.toString(),"Accounts_CreateAccount")
        session
      })
  }


  //  val CreateAccAction = group("CreateAccAction") {
  //    exec(http("CreateAccAction")
  //        .post(url2)
  //        .body(StringBody(body.toString)).asJSON
  ////        .body(StringBody("{\"first_name\":\"test\",\"last_name\":\"test\",\"email\":\"xyz2@email.test\",\"password\":\"test123\",\"confirm_password\":\"test123\",\"phone_number\":\"\",\"zip\":\"\",\"canadian_customer\":\"F\",\"saks_opt_status\":\"T\",\"off5th_opt_status\":\"T\",\"saks_canada_opt_status\":\"F\",\"off5th_canada_opt_status\":\"F\"}")).asJSON
  //          .check(status.is(200))
  //    )
  //  }


  //  val GetAccountSummary = group("GetAccSummary") {
  //    exec(http("GetAccSummary")
  //      .get(url3)
  //      .check(status.is(200))
  //    )
  //  }
}
