package S5AOMS.FoundationScripts

import OMSBase.OMSBaseTest
import io.gatling.core.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object FindInStoreFunctions extends OMSBaseTest {

  /*val FindInStore1ItemsWeb = exec(session => {

    session.set("numOfItems","1").set("InvList","ItemsStore").set("Org",org).set("EntryType","Online").set("Transaction","FindInStore").set("FulfillmentType","AVAILABLE")

  })*/

  val FindInStore2ItemsWeb = exec(session => {

    session.set("numOfItems","2").set("InvList","Items").set("Org",org).set("EntryType","Online").set("Transaction","FindInStore").set("FulfillmentType","AVAILABLE")

  })

  val FindInStore5ItemsWeb = exec(session => {

    session.set("numOfItems","5").set("InvList","Items").set("Org",org).set("EntryType","Online").set("Transaction","FindInStore").set("FulfillmentType","AVAILABLE")

  })
  val FindInStore10ItemsWeb = exec(session => {

    session.set("numOfItems","10").set("InvList","Items").set("Org",org).set("EntryType","Online").set("Transaction","FindInStore").set("FulfillmentType","AVAILABLE")

  })

  val FindInStore2ItemsPOS = exec(session => {

    session.set("numOfItems","2").set("InvList","Items").set("Org",org).set("EntryType","POS").set("Transaction","FindInStorePOS").set("FulfillmentType","AVAILABLE")
  })

  val FindInStore5ItemsPOS = exec(session => {

    session.set("numOfItems","5").set("InvList","Items").set("Org",org).set("EntryType","POS").set("Transaction","FindInStorePOS").set("FulfillmentType","AVAILABLE")

  })
  val FindInStore10ItemsPOS = exec(session => {

    session.set("numOfItems","10").set("InvList","Items").set("Org",org).set("EntryType","POS").set("Transaction","FindInStorePOS").set("FulfillmentType","AVAILABLE")
  })

}


