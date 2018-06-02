package testSetUp.OMS

import OMSBase.OMSBaseTest
import S5AOMS.DataCreation.{CreateOrderFunctions, POFunctions, SalesTickle, clearSession}
import S5AOMS.FoundationScripts.CreateOrder
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class inventoryUpdateDC extends OMSBaseTest {


  val DCCO = scenario("DCCO")
    .exec(clearSession.clearSession)
    .asLongAs(session => session("flag").as[Int] == 1) {
      exec(SalesTickle.SalesTickle_DC)
    }


  setUp(

    DCCO.inject(
      atOnceUsers(30)
    ).protocols(httpConf)
  )

}

