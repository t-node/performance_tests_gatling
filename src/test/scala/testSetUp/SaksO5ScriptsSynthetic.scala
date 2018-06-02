package testSetUp

import BaseTest.BaseTest
import NewRelic.NewRelicMetrics
import SaksChains.SaksCheckoutChains._
import SaksChains.SaksFacetSearchChains.SaksFacetSearch
import SaksChains.SaksPDPChains._
import SaksChains.SaksPagnation._
import SaksChains.SaksSearchStringChains.SaksSearchString
import SaksO5Chains.SaksO5CheckoutChains._
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class SaksO5ScriptsSynthetic extends BaseTest {

  /*val numOfUsers: Int = System.getProperty("UserCount").toInt

  val duration: Int = System.getProperty("Duration").toInt

  val rampUp: Int = System.getProperty("rampUp").toInt
*/

  val PDP = scenario("SaksO5PDP")
        .exec(clearSession.clearSession)
          .exec(SaksSearchPA.SaksSearchStringPA)
          .exec(SaksSearchPDPDirect.SaksSearch)



  val FacetSearch = scenario("SaksO5FacetSearch")
      .exec(clearSession.clearSession)
          .exec(SaksSearchPA.SaksSearchStringPA)
          .exec(SaksFacetSearch.SaksFacetSearch)


  val Pagnation = scenario("SaksO5Pagnation")
    .exec(clearSession.clearSession)
        .exec(SaksSearchPA.SaksSearchStringPA)
        .exec(SaksPagnation.SaksPagnation)


  val SS = scenario("SaksO5SearchString")
    .exec(clearSession.clearSession)
        .exec(SaksHomePage.SaksHome)
        .exec(SaksSearchString.SaksSearchString)


  val SearchCheckout = scenario("SaksO5SearchCheckout")
   .exitBlockOnFail {
        exec(clearSession.clearSession)
          .exec(SaksO5HomePage.SaksHome)
          .exec(SaksO5SearchStringPA.SaksSearchStringPA)
          .exec(SaksO5SearchPDP.SaksSearch)
          .doIf(session => session("SoldOut").as[Int] == 0) {
            doIf(session => session("sku_idCount").as[Int] > 0) {
              doIf(session => session("availableItems").as[Int] == 1) {
                exec(SaksO5AddToBag.AddToBag)
                  .exec(SaksO5ShoppingBag.SaksshoppingBag)
                  .exec(SaksO5ChangeQty.ChangeQty)
                  .exec(SaksO5Checkout.SaksCheckout)
                  .exec(SaksO5ShippingAddress.Shipping)
                  .exec(SaksO5BillingAddress.Billing)
                //    .exec(SaksO5SubmitOrder.SubmitOrder)
              }
            }
          }

      }

  val MultipleCheckout = scenario("SaksO5MultipleCheckout")
    .exitBlockOnFail {
      exec(clearSession.clearSession)
        .exec(SaksO5HomePage.SaksHome)
          .repeat(3) {
            exec(SaksO5SearchStringPA.SaksSearchStringPA)
              .exec(SaksO5SearchPDP.SaksSearch)
              .doIf(session => session("SoldOut").as[Int] == 0) {
                doIf(session => session("sku_idCount").as[Int] > 0) {
                  doIf(session => session("availableItems").as[Int] == 1) {
                    exec(SaksO5AddToBag.AddToBag)
                  }
                }
              }
          }
        .doIf(session => session("SoldOut").as[Int] == 0) {
          doIf(session => session("sku_idCount").as[Int] > 0) {
            doIf(session => session("availableItems").as[Int] == 1) {
                exec(SaksO5ShoppingBag.SaksshoppingBag)
                .exec(SaksO5ChangeQty.ChangeQty)
                .exec(SaksO5Checkout.SaksCheckout)
                .exec(SaksO5ShippingAddress.Shipping)
                .exec(SaksO5BillingAddress.Billing)
              //    .exec(SaksO5SubmitOrder.SubmitOrder)
            }
          }
        }

    }

//  val allScenarios = PDP.exec(FacetSearch).exec(Pagnation).exec(SS).exec(SearchCheckout)

  val allScenarios = SearchCheckout

  setUp(

    allScenarios.inject(
      atOnceUsers(1)
    ).protocols(httpConf)
    )


}

