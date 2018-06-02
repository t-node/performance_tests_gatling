package testSetUp

import BaseTest.BaseTest
import LTChains.LTCheckout._
import LTChains.LTFacetSearch.LTFacetSearch
import LTChains.LTPDPChains.{LTPDP, LTSearchPA}
import LTChains.LTPageNavigation.LTPageNavigation
import SaksChains.SaksCheckoutChains._
import TheBayChains.TheBayCheckout._
import TheBayChains.TheBayFacetSearch.TheBayFacetSearch
import TheBayChains.TheBayPDPChains.{TheBayPDP, TheBaySearchPA}
import TheBayChains.TheBayPageNavigation.TheBayPageNavigation
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class LTScripts extends BaseTest {

  val numOfUsers: Int = System.getProperty("UserCount").toInt/4

  val duration: Int = System.getProperty("Duration").toInt

  val rampUp: Int = System.getProperty("rampUp").toInt


  val Facet = scenario("LTFacet")
    .during(duration) {
      exec(clearSession.clearSession)
        .exec(LTSearchPA.LTSearchPA)
        .exec(LTFacetSearch.LTFacetSearch)
    }

  val PageNavigation = scenario("LTPageNavigation")
    .during(duration) {
      exec(clearSession.clearSession)
        .exec(LTSearchPA.LTSearchPA)
        .exec(LTPageNavigation.LTPageNavigation)
    }

  val PDP = scenario("LTPDP")
      .during(duration){
      exec(clearSession.clearSession)
         .exec(LTSearchPA.LTSearchPA)
            .exec(LTPDP.LTPDP)
      }

  val Checkout = scenario("LTCheckout")
    .during(duration) {
      exitBlockOnFail {
        exec(clearSession.clearSession)
          .exec(LTHomePage.LTHomePage)
          .exec(LTSearchPA.LTSearchPA)
          .exec(LTPDP.LTPDP)
          .doIf(session => session("NoResults").as[Int] == 0) {
            doIf(session => session("MultipleResults").as[Int] == 0) {
              exec(LTAddToBag.LTAddToBag)
                .exec(LTChgQty.LTChgQty)
                .exec(LTCheckout.LTCheckout)
                .exec(LTShipping.LTShipping)
                .exec(LTBilling.LTBilling)
            }
          }
      }
    }

  setUp(

    PDP.inject(
      atOnceUsers(numOfUsers)
    ).protocols(httpConf),


    Checkout.inject(
      atOnceUsers(numOfUsers)
    ).protocols(httpConf),

    PageNavigation.inject(
      atOnceUsers(numOfUsers)
    ).protocols(httpConf),

    Facet.inject(
      atOnceUsers(numOfUsers)
    ).protocols(httpConf)
  )


}

