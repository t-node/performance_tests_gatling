package testSetUp

import BaseTest.BaseTest
import Google.Google
import SaksChains.SaksCheckoutChains._
import TheBayChains.TheBayCheckout.TheBayHomePage
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class theBayTest extends BaseTest {



  val MI = scenario("GoogleTest")
    .exec(clearSession.clearSession)
     .exec(TheBayHomePage.TheBayHomePage)




//  val allScenarios = PDP.exec(FacetSearch).exec(Pagnation).exec(SS).exec(SearchCheckout)


  setUp(

      MI.inject(
      atOnceUsers(1)
    ).protocols(httpConf1)
    )


}

