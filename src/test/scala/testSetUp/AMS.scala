package testSetUp

import AMS.AMSChain
import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains.clearSession
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class AMS extends BaseTest {



  val AMS = scenario("AMSTest")
    .exec(clearSession.clearSession)
     .exec(AMSChain.AMS)






  setUp(

      AMS.inject(
      atOnceUsers(1)
    ).protocols(httpConf)
    )


}

