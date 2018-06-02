package testSetUp

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains._
import SaksO5Chains.SaksO5CheckoutChains._
import SaksO5Chains.SaksO5FacetSearchChains.SaksO5FacetSearch
import SaksO5Chains.SaksO5PDPChains.{SaksO5SearchPA, SaksO5SearchPDPDirect}
import SaksO5Chains.SaksO5PDPMultiple.{SaksO5SearchPDPDirect1, SaksO5SearchPDPDirect2, SaksO5SearchPDPDirect3}
import SaksO5Chains.SaksO5Pagnation.SaksO5Pagnation
import SaksO5Chains.SaksO5SearchStringChains.SaksO5SearchString
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class OrdersSaksChrome extends BaseTest {



  /*val SO_2 = scenario("MultipleCheckout_2")
    .repeat(3) {
      exitBlockOnFail {
        exec(clearSession.clearSession)
              .exec(SaksO5HomePage.SaksHome)
          .repeat(2) {
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
                doIf(session => session("AddToBag_valid").as[Int] != 0) {
                  exec(SaksO5ShoppingBag.SaksshoppingBag)
                    .exec(SaksO5ChangeQty.ChangeQty)
                    .exec(SaksO5Checkout.SaksCheckout)
                    .exec(SaksO5ShippingAddress.Shipping)
                    .exec(SaksO5BillingAddress.Billing)
                    .exec(SaksO5SubmitOrderChrome.SubmitOrder)
                }
              }
            }
          }

      }
    }

  val SO_3 = scenario("MultipleCheckout_3")
    .repeat(3) {
      exitBlockOnFail {
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
                doIf(session => session("AddToBag_valid").as[Int] != 0) {
                  exec(SaksO5ShoppingBag.SaksshoppingBag)
                    .exec(SaksO5ChangeQty.ChangeQty)
                    .exec(SaksO5Checkout.SaksCheckout)
                    .exec(SaksO5ShippingAddress.Shipping)
                    .exec(SaksO5BillingAddress.Billing)
                    .exec(SaksO5SubmitOrderChrome.SubmitOrder)
                }
              }
            }
          }

      }
    }
*/
  val SO = scenario("SubmitOrder")
    .repeat(2){
      exitBlockOnFail {
        exec(clearSession.clearSession)
          .exec(SaksO5HomePage.SaksHome)
          .exec(SaksO5SearchStringPA.SaksSearchStringPA)
          .exec(SaksO5SearchPDP.SaksSearch)
          .doIf(session => session("SoldOut").as[Int] == 0) {
            doIf(session => session("sku_idCount").as[Int] > 0) {
              exec(SaksO5AddToBag.AddToBag)
                .doIf(session => session("AddToBag_valid").as[Int] != 0) {
                  exec(SaksO5ShoppingBag.SaksshoppingBag)
                    .exec(SaksO5ChangeQty.ChangeQty)
                    .exec(SaksO5Checkout.SaksCheckout)
                    .exec(SaksO5ShippingAddress.Shipping)
                    .exec(SaksO5BillingAddress.Billing)
                    .exec(SaksO5SubmitOrderChrome.SubmitOrder)
                }
            }
          }


      }
    }



  setUp(



    SO.inject(
      atOnceUsers(1)

    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConfChrome)
    //,


   /* SO_2.inject(
      atOnceUsers(15)

    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConfChrome),

    SO_3.inject(
      atOnceUsers(15)

    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConfChrome)*/

  )

}

