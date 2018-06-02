package S5AOMS.FoundationScripts

import OMSBase.OMSBaseTest
import io.gatling.core.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object ATPFunctions extends OMSBaseTest {



  val ATP2Items = exec(session => {

    session.set("numOfItems","2").set("InvList","Items").set("Org",org).set("EntryType","Web").set("Transaction","ATP")

  })

  val ATP5Items = exec(session => {

    session.set("numOfItems","5").set("InvList","Items").set("Org",org).set("EntryType","Web").set("Transaction","ATP")

  })
  val ATP10Items = exec(session => {

    session.set("numOfItems","10").set("InvList","Items").set("Org",org).set("EntryType","Web").set("Transaction","ATP")

  })
}


