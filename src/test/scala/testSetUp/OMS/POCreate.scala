package testSetUp.OMS

import OMSBase.OMSBaseTest
import S5AOMS.DataCreation.{POFunctions, SalesTickle, clearSession}
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class POCreate extends OMSBaseTest {


  val POCreate = scenario("POCreate")
      .exec(clearSession.clearSession)
    .asLongAs(session => session("flag").as[Int] == 1) {
      exec(POFunctions.POCreate)
    }



  setUp(

    POCreate.inject(
      atOnceUsers(10)
    ).protocols(httpConf)
  )

}

