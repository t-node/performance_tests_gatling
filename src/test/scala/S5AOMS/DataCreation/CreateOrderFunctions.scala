package S5AOMS.DataCreation

import OMSBase.OMSBaseTest
import io.gatling.core.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object CreateOrderFunctions extends OMSBaseTest {


  val createOrder2ItemsWebSOM = exec(session => {


    val store = readRedis("StoresSOM")

    session.set("numOfItems","2").set("InvList",store).set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderWebStore")

  })


  val createOrder2ItemsWebDC = exec(session => {

    session.set("numOfItems","2").set("InvList","ItemsDC").set("EnteredBy","Web").set("Org",org).set("EntryType","Online").set("Transaction","CreateOrderDC")

  })

}


