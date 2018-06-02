package testSetUp.Accounts

import Accounts.Checkout._
import Accounts.SignIn.{CreateAccount, SignIn}
import Accounts._
import SaksChains.SaksCheckoutChains.clearSession
import io.gatling.core.Predef._

/**
  * Created by 461967 on 6/22/2016.
  */
class SignIn extends AccountsTest {

  val SI = scenario("SignIn")
    .during(duration) {
      exec(clearSession.clearSession)
        .exec(SignIn.openSignInPage)
          .exitBlockOnFail({
        exec(SignIn.performSignIn)
        .exec(SignIn.getAccountSummary)
        .exec(OrderHistory.ViewOrderHistory)
        .exec(ShippingAddress.viewShipAddr)
        .exec(BillingAddress.ViewBillAddr)
        .exec(PaymentMethod.ViewPayment)
        .exec(AccountSettings.ViewAccountSettings)})
    }

  setUp(
    SI.inject(
      atOnceUsers(numOfUsers)
    ).protocols(httpConf1)
  )
}
