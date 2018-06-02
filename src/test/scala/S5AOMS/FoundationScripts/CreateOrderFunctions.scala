package S5AOMS.FoundationScripts

import OMSBase.OMSBaseTest
import io.gatling.core.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object CreateOrderFunctions extends OMSBaseTest {



  val createOrder2ItemsWebDC = exec(session => {

    session.set("numOfItems","2").set("InvList","ItemsDC").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderDC")

  })

  val createOrder5ItemsWebDC = exec(session => {

    session.set("numOfItems","5").set("InvList","ItemsDC").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderDC")

  })
  val createOrder10ItemsWebDC = exec(session => {

    session.set("numOfItems","10").set("InvList","ItemsDC").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderDC")

  })

  val createOrder2ItemsPOSDC = exec(session => {

    session.set("numOfItems","2").set("InvList","ItemsDC").set("EnteredBy","POS").set("Org",org).set("EntryType","POS").set("Transaction","CreateOrderPOS")

  })
  val createOrder5ItemsPOSDC = exec(session => {

    session.set("numOfItems","5").set("InvList","ItemsDC").set("EnteredBy","POS").set("Org",org).set("EntryType","POS").set("Transaction","CreateOrderPOS")

  })
  val createOrder10ItemsPOSDC = exec(session => {

    session.set("numOfItems","10").set("InvList","ItemsDC").set("EnteredBy","POS").set("Org",org).set("EntryType","POS").set("Transaction","CreateOrderPOS")

  })

  val createOrder2ItemsWebStore = exec(session => {

    session.set("numOfItems","2").set("InvList","ItemsStore").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderWebStore")

  })
  val createOrder5ItemsWebStore = exec(session => {

    session.set("numOfItems","5").set("InvList","ItemsStore").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderWebStore")

  })

  val createOrder10ItemsWebStore = exec(session => {

    session.set("numOfItems","10").set("InvList","ItemsStore").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderWebStore")

  })

  val createOrder2ItemsPOSStore = exec(session => {

    session.set("numOfItems","2").set("InvList","ItemsStore").set("EnteredBy","POS").set("Org",org).set("EntryType","POS").set("Transaction","CreateOrderPOSStore")

  })
  val createOrder5ItemsPOSStore = exec(session => {

    session.set("numOfItems","5").set("InvList","ItemsStore").set("EnteredBy","POS").set("Org",org).set("EntryType","POS").set("Transaction","CreateOrderPOSStore")

  })
  val createOrder10ItemsPOSStore = exec(session => {

    session.set("numOfItems","10").set("InvList","ItemsStore").set("EnteredBy","POS").set("Org",org).set("EntryType","POS").set("Transaction","CreateOrderPOSStore")

  })
  val createOrder4ItemsWebMix = exec(session => {

    session.set("numOfItems","4").set("InvList","ItemsStore,ItemsDC,ItemsPO,ItemsDSV").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderWebMix")

  })
  val createOrder8ItemsWebMix = exec(session => {

    session.set("numOfItems","8").set("InvList","ItemsStore,ItemsDC,ItemsPO,ItemsDSV").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderWebMix")

  })

  val createOrder4ItemsPOSMix = exec(session => {

    session.set("numOfItems","4").set("InvList","ItemsStore,ItemsDC,ItemsPO,ItemsDSV").set("EnteredBy","POS").set("Org",org).set("EntryType","POS").set("Transaction","CreateOrderPOSMix")

  })
  val createOrder8ItemsPOSMix = exec(session => {

    session.set("numOfItems","8").set("InvList","ItemsStore,ItemsDC,ItemsPO,ItemsDSV").set("EnteredBy","POS").set("Org",org).set("EntryType","POS").set("Transaction","CreateOrderPOSMix")

  })
  val createOrder10ItemsPOSMix = exec(session => {

    session.set("numOfItems","10").set("InvList","ItemsStore,ItemsDC").set("EnteredBy","POS").set("Org",org).set("EntryType","POS").set("Transaction","CreateOrderPOSMix")

  })

  val createOrder2ItemsWebDSV = exec(session => {

    session.set("numOfItems","2").set("InvList","ItemsDSV").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderDSV")

  })

  val createOrder5ItemsWebDSV = exec(session => {

    session.set("numOfItems","5").set("InvList","ItemsDSV").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderDSV")

  })
  val createOrder10ItemsWebDSV = exec(session => {

    session.set("numOfItems","10").set("InvList","ItemsDSV").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderDSV")

  })

  val createOrder2ItemsInternational = exec(session => {

    session.set("numOfItems","2").set("InvList","ItemsDC").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderInternational")

  })
  val createOrder5ItemsInternational = exec(session => {

    session.set("numOfItems","5").set("InvList","ItemsDC").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderInternational")

  })
  val createOrder10ItemsInternational = exec(session => {

    session.set("numOfItems","10").set("InvList","ItemsDC").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderInternational")

  })


  val createOrder2ItemsPO = exec(session => {

    session.set("numOfItems","2").set("InvList","ItemsPO").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderPO")

  })

  val createOrder5ItemsPO = exec(session => {

    session.set("numOfItems","5").set("InvList","ItemsPO").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderPO")

  })

  val createOrder10ItemsPO = exec(session => {

    session.set("numOfItems","10").set("InvList","ItemsPO").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderPO")

  })




}


