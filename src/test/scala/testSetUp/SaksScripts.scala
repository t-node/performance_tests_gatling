package testSetUp

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains._
import SaksChains.SaksFacetSearchChains.SaksFacetSearch
import SaksChains.SaksPDPChains._
import SaksChains.SaksPagnation._
import SaksChains.SaksSearchStringChains.SaksSearchString
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class SaksScripts extends BaseTest {

  val numOfUsers: Int = System.getProperty("UserCount").toInt / 5

  val duration: Int = System.getProperty("Duration").toInt

  val rampUp: Int = System.getProperty("rampUp").toInt

  /*val checkout = scenario("SaksCheckout")
    // .asLongAs(true) {
    //     .during(120) {
    .exitBlockOnFail {
    exec(SaksHomePage.SaksHome)
      .exec(SaksSearchPDP.SaksSearch)

      .doIf(session => session("SoldOut").as[Int] == 0) {
        doIf(session => session("sku_idCount").as[Int] > 0) {
          exec(SaksAddToBag.AddToBag)


            .exec(SaksShoppingBag.SaksshoppingBag)
            .exec(SaksChangeQty.ChangeQty)
            .exec(SaksCheckout.SaksCheckout)
            .exec(SaksShippingAddress.Shipping)
            .exec(SaksBillingAddress.Billing)
     //       .exec(SaksSubmitOrder.SubmitOrder)
        }
      }


  }*/
  //}


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

  val SearchCheckout = scenario("SaksSearchCheckout")
   .during(duration){
        exitBlockOnFail {
        exec(clearSession.clearSession)
          .exec(SaksHomePage.SaksHome)
          .exec(SaksSearchStringPA.SaksSearchStringPA)
          .exec(SaksSearchPDP.SaksSearch)
          .doIf(session => session("SoldOut").as[Int] == 0) {
            doIf(session => session("sku_idCount").as[Int] > 0) {
              exec(SaksAddToBag.AddToBag)


                .exec(SaksShoppingBag.SaksshoppingBag)
                .exec(SaksChangeQty.ChangeQty)
                .exec(SaksCheckout.SaksCheckout)
                .exec(SaksShippingAddress.Shipping)
                .exec(SaksBillingAddress.Billing)
              // .exec(SaksSubmitOrder.SubmitOrder)
            }
          }


      }
   }



  setUp(
    /*checkout.inject(
      //    nothingFor(4 seconds), // 1
      atOnceUsers(numOfUsers) // 2
      //    rampUsers(3) over(5 seconds)
      //   nothingFor(15 seconds)
      //  constantUsersPerSec(0) during(60 seconds) // 4
      //  constantUsersPerSec(20) during(15 seconds) randomized, // 5
      //   rampUsersPerSec(10) to(20) during(10 minutes), // 6
      //   rampUsersPerSec(10) to(20) during(10 minutes) randomized, // 7
      //   splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy(10 seconds), // 8
      //   splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy(atOnceUsers(30)), // 9
      //  heavisideUsers(1000) over(20 seconds) // 10
    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf),*/
    // .maxDuration(2 minutes)

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


    SearchCheckout.inject(
      rampUsers(numOfUsers) over(rampUp)

  )
    //  .throttle(holdFor(3 minutes))
    .protocols(httpConf),

    SS.inject(
      rampUsers(numOfUsers) over(rampUp)

    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf)

  )

}

