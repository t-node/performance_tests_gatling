package testSetUp.OMS

import OMSBase.OMSBaseTest
import S5AOMS.DataCreation.{SalesTickle, clearSession}
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class inventoryUpdateStores extends OMSBaseTest {


  val allStoresCO = scenario("allStoresCO")
      .exec(clearSession.clearSession)
    .asLongAs(session => session("flag").as[Int] == 1) {
      exec(SalesTickle.SalesTickle_Store)
    }




  setUp(

    allStoresCO.inject(
      atOnceUsers(34)
    ).protocols(httpConf)
  )


}
