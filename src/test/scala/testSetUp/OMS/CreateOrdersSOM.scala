package testSetUp.OMS

import OMSBase.OMSBaseTest
import S5AOMS.DataCreation._
import S5AOMS.FoundationScripts.{CreateOrder}
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class CreateOrdersSOM extends OMSBaseTest {

  //val userCount : Int = 1

  val allStoresCO = scenario("allStoresCO")
      .exec(clearSession.clearSession)
      .exec(CreateOrderFunctions.createOrder2ItemsWebSOM)
          .exec(CreateOrderSOM.CreateOrderSOM)



  setUp(

  allStoresCO.inject(
      atOnceUsers(1)
    ).protocols(httpConf)

  )


}

