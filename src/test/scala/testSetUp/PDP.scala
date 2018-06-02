package testSetUp

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains._
import SaksChains.SaksFacetSearchChains.SaksFacetSearch
import SaksChains.SaksPDPChains._
import SaksChains.SaksPagnation._
import SaksChains.SaksSearchStringChains.SaksSearchString
import SaksO5Chains.SaksO5PDPChains.SaksO5SearchPDPDirect
import SaksO5Chains.SaksO5PDPMultiple.{SaksO5SearchPDPDirect3, SaksO5SearchPDPDirect2, SaksO5SearchPDPDirect1}
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class PDP extends BaseTest {

  val numOfUsers: Int = System.getProperty("UserCount").toInt

  val duration: Int = System.getProperty("Duration").toInt

  val rampUp: Int = System.getProperty("rampUp").toInt


  val PDP = scenario("SaksPDP")
      .during(duration){
        exec(clearSession.clearSession)
          .exec(SaksHomePage.SaksHome)
          .exec(SaksSearchString.SaksSearchString)
          .exec(SaksO5SearchPDPDirect.SaksSearch)
          .exec(SaksO5SearchPDPDirect1.SaksSearch)
          .exec(SaksO5SearchPDPDirect2.SaksSearch)
          .exec(SaksO5SearchPDPDirect3.SaksSearch)
      }



  setUp(


    PDP.inject(
      rampUsers(numOfUsers) over(rampUp)
    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf)

  )

}

