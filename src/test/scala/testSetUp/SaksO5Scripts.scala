package testSetUp

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains._
import SaksChains.SaksFacetSearchChains.SaksFacetSearch
import SaksChains.SaksPDPChains._
import SaksChains.SaksPagnation._
import SaksChains.SaksSearchStringChains.SaksSearchString
import SaksO5Chains.SaksO5CheckoutChains._
import SaksO5Chains.SaksO5FacetSearchChains.SaksO5FacetSearch
import SaksO5Chains.SaksO5PDPChains.{SaksO5SearchPDPData, SaksO5SearchPDPDirect, SaksO5SearchPA}
import SaksO5Chains.SaksO5PDPMultiple.{SaksO5SearchPDPDirect3, SaksO5SearchPDPDirect2, SaksO5SearchPDPDirect1}
import SaksO5Chains.SaksO5Pagnation.SaksO5Pagnation
import SaksO5Chains.SaksO5SearchStringChains.SaksO5SearchString
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class SaksO5Scripts extends BaseTest {



/*
  val numOfUsers: Int = System.getProperty("UserCount").toInt
  val PDPUsers: Int = (System.getProperty("PDPPercent").toInt*numOfUsers)/100
  val FSUsers: Int = (System.getProperty("FSPercent").toInt*numOfUsers)/100
  val PNUsers: Int = (System.getProperty("PNPercent").toInt*numOfUsers)/100
  val SSUsers: Int = (System.getProperty("SSPercent").toInt*numOfUsers)/100
  val CHUsers: Int = (System.getProperty("CHPercent").toInt*numOfUsers)/100
  val SOUsers: Int = (System.getProperty("SOPercent").toInt*numOfUsers)/100
  val ABUsers: Int = (System.getProperty("ABPercent").toInt*numOfUsers)/100*


  val duration: Int = System.getProperty("Duration").toInt

  val rampUp: Int = System.getProperty("rampUp").toInt
*/

  val numOfUsers: Int = 50
  val HomeUsers: Int = (10*numOfUsers)/100
  val SSUsers: Int = (20*numOfUsers)/100
  val PDPUsers: Int = (20*numOfUsers)/100
  val ABUsers: Int = (20*numOfUsers)/100
  val FSUsers: Int = (5*numOfUsers)/100
  val PNUsers: Int = (5*numOfUsers)/100

  val SOUsers: Int = (5*numOfUsers)/100
  val SOUsers3: Int = (1*numOfUsers)/100
  val SOUsers2: Int = (4*numOfUsers)/100
  val CHUsers: Int = (10*numOfUsers)/100

  val duration: Int = 3600 * 8

  val rampUp: Int = 600

  val PDP = scenario("PDP")
      .during(duration){
        exec(SaksO5SearchPA.SaksSearchStringPA)
          .exec(SaksO5SearchPDPDirect.SaksSearch)
          .exec(SaksO5SearchPDPDirect1.SaksSearch)
          .exec(SaksO5SearchPDPDirect2.SaksSearch)
          .exec(SaksO5SearchPDPDirect3.SaksSearch)
      }


  val PDPSearchString = scenario("PDP")
    .during(duration){
      exec(SaksO5SearchString.SaksSearchString)
        .exec(SaksO5SearchPDPDirect.SaksSearch)
        .exec(SaksO5SearchPDPDirect1.SaksSearch)
        .exec(SaksO5SearchPDPDirect2.SaksSearch)
        .exec(SaksO5SearchPDPDirect3.SaksSearch)
    }


  val PDPData = scenario("PDP")
    .during(duration){
        exec(SaksO5SearchPDPData.SaksSearch)
        .exec(SaksO5SearchPDPData.SaksSearch)
        .exec(SaksO5SearchPDPData.SaksSearch)
        .exec(SaksO5SearchPDPData.SaksSearch)
    }

  val FacetSearch = scenario("FacetSearch")
      .during(duration){
          exec(SaksO5SearchPA.SaksSearchStringPA)
          .exec(SaksO5FacetSearch.SaksFacetSearch)
      }

  val Pagnation = scenario("Pagnation")
    .during(duration){
        exec(SaksO5SearchPA.SaksSearchStringPA)
        .exec(SaksO5Pagnation.SaksPagnation)
    }

  val SS = scenario("SearchString")
    .during(duration){
     // exec(clearSession.clearSession)
      //  .exec(SaksO5HomePage.SaksHome)
        exec(SaksO5SearchString.SaksSearchString)
    }

  val HomePage = scenario("HomePage")
    .during(duration){
     // exec(clearSession.clearSession)
          exec(SaksO5HomePage.SaksHome)

    }

  val AddToBag = scenario("AddToBag")
    .during(duration) {
      exitBlockOnFail {
        exec(clearSession.clearSession)
       //   .exec(SaksO5HomePage.SaksHome)
          .exec(SaksO5SearchStringPA.SaksSearchStringPA)
          .exec(SaksO5SearchPDP.SaksSearch)
          .doIf(session => session("SoldOut").as[Int] == 0) {
            doIf(session => session("sku_idCount").as[Int] > 0) {
              doIf(session => session("availableItems").as[Int] == 1) {
                exec(SaksO5AddToBag.AddToBag)
              }
            }
          }
      }
    }

  val Checkout = scenario("Checkout")
    .during(duration) {
      exitBlockOnFail {
        exec(clearSession.clearSession)
       //   .exec(SaksO5HomePage.SaksHome)
          .exec(SaksO5SearchStringPA.SaksSearchStringPA)
          .exec(SaksO5SearchPDP.SaksSearch)
          .doIf(session => session("SoldOut").as[Int] == 0) {
            doIf(session => session("sku_idCount").as[Int] > 0) {
              doIf(session => session("availableItems").as[Int] == 1) {
                  exec(SaksO5AddToBag.AddToBag)
                  .doIf(session => session("AddToBag_valid").as[Int] != 0) {
                    exec(SaksO5ShoppingBag.SaksshoppingBag)
                    .exec(SaksO5ChangeQty.ChangeQty)
                    .exec(SaksO5Checkout.SaksCheckout)
                }
              }
            }
          }
      }
    }

  val SO_2 = scenario("MultipleCheckout_2")
    .during(duration) {
      exitBlockOnFail {
        exec(clearSession.clearSession)
      //    .exec(SaksO5HomePage.SaksHome)
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
     //                 .exec(SaksO5SubmitOrder.SubmitOrder)
                }
              }
            }
          }

      }
    }

  val SO_3 = scenario("MultipleCheckout_3")
    .during(duration) {
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
     //                 .exec(SaksO5SubmitOrder.SubmitOrder)
                }
              }
            }
          }

      }
    }

  val SO = scenario("SubmitOrder")
   .during(duration){
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
     //               .exec(SaksO5SubmitOrder.SubmitOrder)
              }
            }
          }


      }
   }



  setUp(


    PDPData.inject(
      rampUsers(PDPUsers) over(rampUp)
    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf1)

/*    Pagnation.inject(
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
      rampUsers(SOUsers) over(rampUp)

  )
    //  .throttle(holdFor(3 minutes))
    .protocols(httpConf),

    HomePage.inject(
      rampUsers(HomeUsers) over(rampUp)

    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf),

    SO_2.inject(
      rampUsers(SOUsers2) over(rampUp)

    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf),

    SO_3.inject(
      rampUsers(SOUsers3) over(rampUp)

    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf),

    SS.inject(
      rampUsers(SSUsers) over(rampUp)

    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf)*/

  )

}

