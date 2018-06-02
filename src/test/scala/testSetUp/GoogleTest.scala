package testSetUp

import BaseTest.BaseTest
import Google.Google
import MonitorInstances._
import SaksChains.SaksCheckoutChains._
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class GoogleTest extends BaseTest {



  val MI = scenario("GoogleTest")
    .exec(clearSession.clearSession)
     .exec(Google.Google)




//  val allScenarios = PDP.exec(FacetSearch).exec(Pagnation).exec(SS).exec(SearchCheckout)


  setUp(

      MI.inject(
      atOnceUsers(2000)
    ).protocols(httpConf1)
    )


}

