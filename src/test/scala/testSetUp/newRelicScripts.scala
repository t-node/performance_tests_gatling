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
class newRelicScripts extends BaseTest {






  val newRel = scenario("newRel")
          .exec(NewRelicMetrics.NewRelicMetrics)






  setUp(




    newRel.inject(
      atOnceUsers(20)

    )
      //  .throttle(holdFor(3 minutes))
      .protocols(httpConf)

  )

}

