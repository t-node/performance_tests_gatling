package testSetUp.OMS

import OMSBase.OMSBaseTest
import S5AOMS.DataCreation.{CreateOrderFunctions, clearSession}
import S5AOMS.FoundationScripts.CreateOrder
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class CreateOrdersDC extends OMSBaseTest {

  //val userCount : Int = 1

  val numOrderDC: Int = 20000



  val DCCO = scenario("DCCO")
    .exec(clearSession.clearSession)
    .repeat(numOrderDC) {
      exec(clearSession.clearSession)
        .exec(CreateOrderFunctions.createOrder2ItemsWebDC)
        .exec(CreateOrder.createOrder)
    }




  setUp(

    DCCO.inject(
      atOnceUsers(5)
    ).protocols(httpConf)

  )


}

