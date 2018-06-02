package S5AOMS.DataCreation

import OMSBase.OMSBaseTest
import io.gatling.core.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SplitFunctions extends OMSBaseTest {

  val SplitFunctions = exec(session => {

    session.set("numOfItems","1").set("InvList","ItemsStore").set("Org",org).set("EntryType","Online").set("Transaction","FindInStore").set("FulfillmentType","AVAILABLE")

  })

}


