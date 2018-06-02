package testSetUp

import BaseTest.BaseTest
import MonitorInstances._
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
class MonitorInstances extends BaseTest {



  val MI = scenario("SaksO5SearchString")
    .exec(clearSession.clearSession)
     .exec(MonitorInstances.MonitorInstances)




//  val allScenarios = PDP.exec(FacetSearch).exec(Pagnation).exec(SS).exec(SearchCheckout)


  setUp(

      MI.inject(
      atOnceUsers(1)
    ).protocols(httpConf1)
    )


}

