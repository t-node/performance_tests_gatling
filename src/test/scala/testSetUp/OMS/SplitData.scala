package testSetUp.OMS

import OMSBase.OMSBaseTest
import S5AOMS.DataCreation.{SplitData, SplitFunctions, clearSession}
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class SplitData extends OMSBaseTest {



  val SD = scenario("SD")
    .exec(clearSession.clearSession)
    .repeat(5000) {
      exec(SplitFunctions.SplitFunctions)
        .exec(SplitData.SplitData)
    }



  setUp(

    SD.inject(
      atOnceUsers(1)
    ).protocols(httpConf)

  )


}

