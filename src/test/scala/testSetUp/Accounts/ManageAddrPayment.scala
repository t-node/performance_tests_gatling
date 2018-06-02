package testSetUp.Accounts

import Accounts.{PaymentMethod, ShippingAddress, BillingAddress, AccountsTest}
import Accounts.SignIn.SignIn
import SaksChains.SaksCheckoutChains.clearSession
import io.gatling.core.Predef._

/**
  * Created by 461967 on 9/21/2016.
  */
class ManageAddrPayment extends AccountsTest {

  val manageAddr = scenario("ManageAddr")
    .during(duration) {
      exec(clearSession.clearSession)
        .exec(SignIn.openSignInPage)
        .exitBlockOnFail({
          exec(SignIn.performSignIn)
            .exec(SignIn.getAccountSummary)
        .exec(BillingAddress.AddBillAddr)
        .exec(ShippingAddress.AddShipAddr)
        .exec(PaymentMethod.AddPayment)})
    }

  setUp(
    manageAddr.inject(
      atOnceUsers(numOfUsers)
    ).protocols(httpConf1)
  )
}
