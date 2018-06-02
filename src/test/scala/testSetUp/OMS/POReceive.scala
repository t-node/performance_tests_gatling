package testSetUp.OMS

import OMSBase.OMSBaseTest
import S5AOMS.DataCreation.{POFunctions, clearSession}
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class POReceive extends OMSBaseTest {


  val POReceive = scenario("POReceive")
      .exec(clearSession.clearSession)
    .asLongAs(session => session("flag").as[Int] == 1) {
      exec(POFunctions.POReceive)
    }



  setUp(

    POReceive.inject(
      atOnceUsers(10)
    ).protocols(httpConf)
  )

}

