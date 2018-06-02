package testSetUp

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains._
import SaksChains.SaksFacetSearchChains.SaksFacetSearch
import SaksChains.SaksPDPChains._
import SaksChains.SaksPagnation._
import SaksChains.SaksSearchStringChains.SaksSearchString
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class SaksLoadTest extends BaseTest {

  val numOfUsers: Int = System.getProperty("UserCount").toInt

  val PDPUsers: Int = (System.getProperty("PDPPercent").toInt*numOfUsers)/100
  val FSUsers: Int = (System.getProperty("FSPercent").toInt*numOfUsers)/100
  val PNUsers: Int = (System.getProperty("PNPercent").toInt*numOfUsers)/100
  val SSUsers: Int = (System.getProperty("SSPercent").toInt*numOfUsers)/100
  val CHUsers: Int = (System.getProperty("CHPercent").toInt*numOfUsers)/100
  val SOUsers: Int = (System.getProperty("SOPercent").toInt*numOfUsers)/100
  val ABUsers: Int = (System.getProperty("ABPercent").toInt*numOfUsers)/100


  val duration: Int = System.getProperty("Duration").toInt

  val rampUp: Int = System.getProperty("rampUp").toInt



  val PDP = scenario("SaksPDP")
      .during(duration){
        exec(clearSession.clearSession)
          .exec(SaksSearchPA.SaksSearchStringPA)
          .exec(SaksSearchPDPDirect.SaksSearch)
      }


  val FacetSearch = scenario("SaksFacetSearch")
      .during(duration){
        exec(clearSession.clearSession)
          .exec(SaksSearchPA.SaksSearchStringPA)
          .exec(SaksFacetSearch.SaksFacetSearch)
      }

  val Pagnation = scenario("SaksPagnation")
    .during(duration){
      exec(clearSession.clearSession)
        .exec(SaksSearchPA.SaksSearchStringPA)
        .exec(SaksPagnation.SaksPagnation)
    }

  val SS = scenario("SaksSearchString")
    .during(duration){
      exec(clearSession.clearSession)
        .exec(SaksHomePage.SaksHome)
        .exec(SaksSearchString.SaksSearchString)
    }

  val SO_3 = scenario("MultipleCheckout_3")
    .exitBlockOnFail {
      exec(clearSession.clearSession)
        .exec(SaksHomePage.SaksHome)
        .repeat(3) {
          exec(SaksSearchStringPA.SaksSearchStringPA)
            .exec(SaksSearchPDP.SaksSearch)
            .doIf(session => session("SoldOut").as[Int] == 0) {
              doIf(session => session("sku_idCount").as[Int] > 0) {
                doIf(session => session("availableItems").as[Int] == 1) {
                  exec(SaksAddToBag.AddToBag)
                }
              }
            }
        }
        .doIf(session => session("SoldOut").as[Int] == 0) {
          doIf(session => session("sku_idCount").as[Int] > 0) {
            doIf(session => session("availableItems").as[Int] == 1) {
              doIf(session => session("AddToBag_valid").as[Int] != 0) {
                exec(SaksShoppingBag.SaksshoppingBag)
                  .exec(SaksChangeQty.ChangeQty)
                  .exec(SaksCheckout.SaksCheckout)
                  .exec(SaksShippingAddress.Shipping)
                  .exec(SaksBillingAddress.Billing)
                //    .exec(SaksSubmitOrder.SubmitOrder)
              }
            }
          }
        }

    }

  val SO_5 = scenario("MultipleCheckout_5")
    .exitBlockOnFail {
      exec(clearSession.clearSession)
        .exec(SaksHomePage.SaksHome)
        .repeat(5) {
          exec(SaksSearchStringPA.SaksSearchStringPA)
            .exec(SaksSearchPDP.SaksSearch)
            .doIf(session => session("SoldOut").as[Int] == 0) {
              doIf(session => session("sku_idCount").as[Int] > 0) {
                doIf(session => session("availableItems").as[Int] == 1) {
                  exec(SaksAddToBag.AddToBag)
                }
              }
            }
        }
        .doIf(session => session("SoldOut").as[Int] == 0) {
          doIf(session => session("sku_idCount").as[Int] > 0) {
            doIf(session => session("availableItems").as[Int] == 1) {
              doIf(session => session("AddToBag_valid").as[Int] != 0) {
                exec(SaksShoppingBag.SaksshoppingBag)
                  .exec(SaksChangeQty.ChangeQty)
                  .exec(SaksCheckout.SaksCheckout)
                  .exec(SaksShippingAddress.Shipping)
                  .exec(SaksBillingAddress.Billing)
                //    .exec(SaksSubmitOrder.SubmitOrder)
              }
            }
          }
        }

    }

  val SO = scenario("SubmitOrder")
    .during(duration) {
      exitBlockOnFail {
        exec(clearSession.clearSession)
          .exec(SaksHomePage.SaksHome)
          .exec(SaksSearchStringPA.SaksSearchStringPA)
          .exec(SaksSearchPDP.SaksSearch)
          .doIf(session => session("SoldOut").as[Int] == 0) {
            doIf(session => session("sku_idCount").as[Int] > 0) {
              doIf(session => session("availableItems").as[Int] == 1) {
                exec(SaksAddToBag.AddToBag)

                  .doIf(session => session("AddToBag_valid").as[Int] != 0) {
                    exec(SaksShoppingBag.SaksshoppingBag)
                      .exec(SaksChangeQty.ChangeQty)
                      .exec(SaksCheckout.SaksCheckout)
                      .exec(SaksShippingAddress.Shipping)
                      .exec(SaksBillingAddress.Billing)
                    // .exec(SaksSubmitOrder.SubmitOrder)
                  }
              }
            }


          }
      }
    }

  val AddToBag = scenario("AddToBag")
    .during(duration) {
      exitBlockOnFail {
        exec(clearSession.clearSession)
          .exec(SaksHomePage.SaksHome)
          .exec(SaksSearchStringPA.SaksSearchStringPA)
          .exec(SaksSearchPDP.SaksSearch)
          .doIf(session => session("SoldOut").as[Int] == 0) {
            doIf(session => session("sku_idCount").as[Int] > 0) {
              doIf(session => session("availableItems").as[Int] == 1) {
                exec(SaksAddToBag.AddToBag)
              }
            }
          }
      }
    }

  val Checkout = scenario("Checkout")
    .during(duration) {
      exitBlockOnFail {
        exec(clearSession.clearSession)
          .exec(SaksHomePage.SaksHome)
          .exec(SaksSearchStringPA.SaksSearchStringPA)
          .exec(SaksSearchPDP.SaksSearch)
          .doIf(session => session("SoldOut").as[Int] == 0) {
            doIf(session => session("sku_idCount").as[Int] > 0) {
              doIf(session => session("availableItems").as[Int] == 1) {
                exec(SaksAddToBag.AddToBag)
                  .doIf(session => session("AddToBag_valid").as[Int] != 0) {
                    exec(SaksShoppingBag.SaksshoppingBag)
                      .exec(SaksChangeQty.ChangeQty)
                      .exec(SaksCheckout.SaksCheckout)
                  }
              }
            }
          }
      }
    }


  setUp(


    PDP.inject(
      rampUsers(PDPUsers) over(rampUp)
    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf),

    Pagnation.inject(
      rampUsers(PNUsers) over(rampUp)
    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf),

    FacetSearch.inject(
      rampUsers(FSUsers) over(rampUp)
    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf),

    AddToBag.inject(
      rampUsers(ABUsers) over(rampUp)

    )
      .protocols(httpConf),

    Checkout.inject(
      rampUsers(CHUsers) over(rampUp)

    )
      .protocols(httpConf),

    SO.inject(
      rampUsers(SOUsers/3) over(rampUp)

    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf),

    SO_3.inject(
      rampUsers(SOUsers/3) over(rampUp)

    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf),

    SO_5.inject(
      rampUsers(SOUsers/3) over(rampUp)

    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf),

    SS.inject(
      rampUsers(SSUsers) over(rampUp)

    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf)

  )
}

