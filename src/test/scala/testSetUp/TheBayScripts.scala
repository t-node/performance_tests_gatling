package testSetUp

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains._
import SaksChains.SaksFacetSearchChains.SaksFacetSearch
import SaksChains.SaksPDPChains._
import SaksChains.SaksPagnation._
import SaksChains.SaksSearchStringChains.SaksSearchString
import TheBayChains.TheBayCheckout._
import TheBayChains.TheBayFacetSearch.TheBayFacetSearch
import TheBayChains.TheBayPDPChains.{TheBayPDP, TheBaySearchPA}
import TheBayChains.TheBayPageNavigation.TheBayPageNavigation
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class TheBayScripts extends BaseTest {

  val numOfUsers: Int = System.getProperty("UserCount").toInt/4

  val duration: Int = System.getProperty("Duration").toInt

  val rampUp: Int = System.getProperty("rampUp").toInt


  val Facet = scenario("BayFacet")
    .during(duration) {
      exec(clearSession.clearSession)
        .exec(TheBaySearchPA.TheBayStringPA)
        .exec(TheBayFacetSearch.TheBayFacetSearch)
    }

  val PageNavigation = scenario("BayPageNavigation")
    .during(duration) {
      exec(clearSession.clearSession)
        .exec(TheBaySearchPA.TheBayStringPA)
        .exec(TheBayPageNavigation.TheBayPageNavigation)
    }

  val PDP = scenario("BayPDP")
      .during(duration){
          exec(clearSession.clearSession)
          .exec(TheBaySearchPA.TheBayStringPA)
            .exec(TheBayPDP.TheBayPDP)
      }

  val Checkout = scenario("TheBayCheckout")
    .during(duration) {
      exitBlockOnFail {
        exec(clearSession.clearSession)
          .exec(TheBayHomePage.TheBayHomePage)
          .exec(TheBaySearchPA.TheBayStringPA)
          .exec(TheBayPDP.TheBayPDP)
          .doIf(session => session("NoResults").as[Int] == 0) {
            doIf(session => session("MultipleResults").as[Int] == 0) {
              exec(TheBayAddToBag.TheBayAddToBag)
                .exec(TheBayChgQty.TheBayChgQty)
                .exec(TheBayCheckout.TheBayCheckout)
                .exec(TheBayShipping.TheBayShipping)
                .exec(TheBayBilling.TheBayBilling)
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

