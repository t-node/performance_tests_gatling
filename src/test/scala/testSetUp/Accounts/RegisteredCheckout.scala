package testSetUp.Accounts

import Accounts.AccountsTest
import Accounts.Checkout._
import Accounts.SignIn.SignIn
import SaksChains.SaksCheckoutChains.clearSession
import io.gatling.core.Predef._

/**
  * Created by 461967 on 9/21/2016.
  */
class RegisteredCheckout extends AccountsTest {

  val checkout_Reg = scenario("Registered_Checkout")
      .during(duration) {
        exec(clearSession.clearSession)
          .exec(SaksO5HomePage.SaksHome)
          .exec(SignIn.openSignInPage)
          .exitBlockOnFail({
            exec(SignIn.performSignIn)
              .exec(SignIn.getAccountSummary)
          .exec(SaksO5SearchStringPA.SaksSearchStringPA)
          .exec(SaksO5SearchPDP.SaksSearch)
          .doIf(session => session("SoldOut").as[Int] == 0) {
            doIf(session => session("sku_idCount").as[Int] > 0) {
              exec(SaksO5AddToBag.AddToBag)
                .doIf(session => session("AddToBag_valid").as[Int] != 0) {
                  exec(SaksO5ShoppingBag.SaksshoppingBag)
                    .exec(SaksO5Checkout.SaksCheckout)
                    .exec(SaksO5SubmitOrder.SubmitOrder)
                }
            }
          }})
      }

  setUp(
    checkout_Reg.inject(
      atOnceUsers(numOfUsers)
    ).protocols(httpConf1)
  )
}
