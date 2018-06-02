package S5AOMS.FoundationScripts

import OMSBase.OMSBaseTest
import io.gatling.core.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object inventoryLookupFunctions extends OMSBaseTest {



  val inventoryLookup2ItemsWeb = exec(session => {

    session.set("numOfItems","2").set("InvList","Items").set("Org",org).set("EntryType","Online").set("Transaction","inventoryLookup").set("FulfillmentType","SHIP")

  })

  val inventoryLookup5ItemsWeb = exec(session => {

    session.set("numOfItems","5").set("InvList","Items").set("Org",org).set("EntryType","Online").set("Transaction","inventoryLookup").set("FulfillmentType","SHIP")

  })
  val inventoryLookup10ItemsWeb = exec(session => {

    session.set("numOfItems","10").set("InvList","Items").set("Org",org).set("EntryType","Online").set("Transaction","inventoryLookup").set("FulfillmentType","SHIP")

  })

  val inventoryLookup2ItemsPOS = exec(session => {

    session.set("numOfItems","2").set("InvList","Items").set("Org",org).set("EntryType","POS").set("Transaction","inventoryLookupPOS").set("FulfillmentType","SHIP")

  })

  val inventoryLookup5ItemsPOS = exec(session => {

    session.set("numOfItems","5").set("InvList","Items").set("Org",org).set("EntryType","POS").set("Transaction","inventoryLookupPOS").set("FulfillmentType","SHIP")

  })
  val inventoryLookup10ItemsPOS = exec(session => {

    session.set("numOfItems","10").set("InvList","Items").set("Org",org).set("EntryType","POS").set("Transaction","inventoryLookupPOS").set("FulfillmentType","SHIP")

  })



  val inventoryLookupDSV2ItemsWeb = exec(session => {

    session.set("numOfItems","2").set("InvList","ItemsDSV").set("Org",org).set("EntryType","Online").set("Transaction","inventoryLookupDSV").set("FulfillmentType","SHIP")

  })

  val inventoryLookupDSV5ItemsWeb = exec(session => {

    session.set("numOfItems","5").set("InvList","ItemsDSV").set("Org",org).set("EntryType","Online").set("Transaction","inventoryLookupDSV").set("FulfillmentType","SHIP")

  })
  val inventoryLookupDSV10ItemsWeb = exec(session => {

    session.set("numOfItems","10").set("InvList","ItemsDSV").set("Org",org).set("EntryType","Online").set("Transaction","inventoryLookupDSV").set("FulfillmentType","SHIP")

  })

  val inventoryLookupPO2ItemsWeb = exec(session => {

    session.set("numOfItems","2").set("InvList","ItemsPO").set("Org",org).set("EntryType","Online").set("Transaction","inventoryLookupPO").set("FulfillmentType","SHIP")

  })

  val inventoryLookupPO5ItemsWeb = exec(session => {

    session.set("numOfItems","5").set("InvList","ItemsPO").set("Org",org).set("EntryType","Online").set("Transaction","inventoryLookupPO").set("FulfillmentType","SHIP")

  })

  val inventoryLookupPO10ItemsWeb = exec(session => {

    session.set("numOfItems","10").set("InvList","ItemsPO").set("Org",org).set("EntryType","Online").set("Transaction","inventoryLookupPO").set("FulfillmentType","SHIP")

  })

  val inventoryLookupMixedItemsWeb = exec(session => {

    session.set("numOfItems","4").set("InvList","ItemsPO,ItemsDSV,ItemsStore,ItemsDC").set("Org",org).set("EntryType","Online").set("Transaction","inventoryLookupMixed").set("FulfillmentType","SHIP")

  })

}


