package testSetUp

import BaseTest.BaseTest
import NewRelic.NewRelicMetrics
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
class SaksScriptsSynthetic extends BaseTest {

  /*val numOfUsers: Int = System.getProperty("UserCount").toInt

  val duration: Int = System.getProperty("Duration").toInt

  val rampUp: Int = System.getProperty("rampUp").toInt*/


  val PDP = scenario("SaksPDP")
        .exec(clearSession.clearSession)
          .exec(SaksSearchPA.SaksSearchStringPA)
          .exec(SaksSearchPDPDirect.SaksSearch)



  val FacetSearch = scenario("SaksFacetSearch")
      .exec(clearSession.clearSession)
          .exec(SaksSearchPA.SaksSearchStringPA)
          .exec(SaksFacetSearch.SaksFacetSearch)


  val Pagnation = scenario("SaksPagnation")
    .exec(clearSession.clearSession)
        .exec(SaksSearchPA.SaksSearchStringPA)
        .exec(SaksPagnation.SaksPagnation)


  val SS = scenario("SaksSearchString")
    .exec(clearSession.clearSession)
        .exec(SaksHomePage.SaksHome)
        .exec(SaksSearchString.SaksSearchString)


  val SearchCheckout = scenario("SaksSearchCheckout")
   .exitBlockOnFail {
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
  val newRel = scenario("newRel")
    .exec(NewRelicMetrics.NewRelicMetrics)

//  val allScenarios = PDP.exec(FacetSearch).exec(Pagnation).exec(SS).exec(SearchCheckout)

  val allScenarios = SearchCheckout

  setUp(

    allScenarios.inject(
      atOnceUsers(1)
    ).protocols(httpConf)
    )


}

