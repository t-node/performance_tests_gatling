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
class off5Scripts extends BaseTest {


  val numOfUsers: Int = System.getProperty("UserCount").toInt / 5

  val duration: Int = System.getProperty("Duration").toInt

  val rampUp: Int = System.getProperty("rampUp").toInt


  val PDP = scenario("PDP")
      .during(duration){
        exec(clearSession.clearSession)
          .exec(SaksO5HomePage.SaksHome)
          .exec(SaksO5SearchPA.SaksSearchStringPA)
          .exec(SaksO5SearchPDPDirect.SaksSearch)
      }


  val FacetSearch = scenario("FacetSearch")
      .during(duration){
        exec(clearSession.clearSession)
          .exec(SaksO5HomePage.SaksHome)
            .exec(SaksO5SearchPA.SaksSearchStringPA)
          .exec(SaksO5FacetSearch.SaksFacetSearch)
      }

  val Pagnation = scenario("Pagnation")
    .during(duration){
      exec(clearSession.clearSession)
        .exec(SaksO5HomePage.SaksHome)
          .exec(SaksO5SearchPA.SaksSearchStringPA)
        .exec(SaksO5Pagnation.SaksPagnation)
    }

  val SS = scenario("SearchString")
    .during(duration){
      exec(clearSession.clearSession)
        .exec(SaksO5HomePage.SaksHome)
          .exec(SaksO5SearchString.SaksSearchString)
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


    PDP.inject(
      rampUsers(numOfUsers) over(rampUp)
    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf),

    Pagnation.inject(
      rampUsers(numOfUsers) over(rampUp)
    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf),

    FacetSearch.inject(
      rampUsers(numOfUsers) over(rampUp)
    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf),

    SS.inject(
      rampUsers(numOfUsers) over(rampUp)
    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf),

    SO.inject(
      rampUsers(numOfUsers) over(rampUp)

  )

      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf)

  )

}

