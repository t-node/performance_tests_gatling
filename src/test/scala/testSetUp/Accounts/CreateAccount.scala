package testSetUp.Accounts

import Accounts.AccountsTest
import Accounts.SignIn.CreateAccount
import SaksChains.SaksCheckoutChains.clearSession
import io.gatling.core.Predef._

/**
  * Created by 461967 on 9/21/2016.
  */
class CreateAccount extends AccountsTest {

  val CA = scenario("CreateAccount")
      .during(duration) {
        exec(clearSession.clearSession)
            .exec(CreateAccount.CreateAccTransaction)
      }

  setUp(
    CA.inject(
      atOnceUsers(numOfUsers)
    ).protocols(httpConf1))
}
