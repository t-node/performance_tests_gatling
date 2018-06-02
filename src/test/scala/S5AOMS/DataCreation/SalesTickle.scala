package S5AOMS.DataCreation

import OMSBase.OMSBaseTest
import io.gatling.core.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SalesTickle extends OMSBaseTest {


  val SalesTickle_DC = exec(session => {
    var flag = 1
        val len: Int = lengthRedis("ItemsDC")
    if(len > 0) {
      val item = readRedisLPOP("ItemsDC")
      inputRedis("ItemsDCNew", item)
      // val item = "72109771"
      val messageText = getSalesTickleXml("SAKS", "ONHAND", "DC-LVG-689", "999999", item)
      //println(messageText)
      sendMessage(messageText, "Order Queue")
      println("sentMessage...")
    }
    else
    {
    //  renameRedis("itemsDCNew", "itemsDC")
      flag = 0
    }
      session.set("flag",flag)

    })


  val SalesTickle_SOM = exec(session => {
    var flag = 1
    val len: Int = lengthRedis("StoresSOM")
    if(len > 0) {
      val store = readRedisLPOP("StoresSOM")
      inputRedis("StoresSOMNew", store)
      val lengthItems: Int = lengthRedis(store)
      var b = 0;

      for (b <- 1 to lengthItems) {
        val item = readRedis(store)
        val messageText = getSalesTickleXml("SAKS", "ONHAND", store, "999999", item)
        //println(messageText)
        sendMessage(messageText, "Order Queue")
        println("sentMessage...")
      }
    }
    else {
      //    renameRedis("StoresNew", "Stores")

      flag = 0

    }
    session.set("flag", flag)

  })


  val SalesTickle_Store = exec(session => {
    var flag = 1
    val len: Int = lengthRedis("Stores")
    if(len > 0) {
      val store = readRedisLPOP("Stores")
      inputRedis("StoresNew", store)
      val lengthItems: Int = lengthRedis(store)
      var b = 0;

      for (b <- 1 to lengthItems) {
        val item = readRedis(store)
        val messageText = getSalesTickleXml("SAKS", "ONHAND", store, "999999", item)
        //println(messageText)
        sendMessage(messageText, "Order Queue")
        println("sentMessage...")
      }
    }
    else {
   //    renameRedis("StoresNew", "Stores")

      flag = 0

    }
      session.set("flag", flag)

    })


 /* val SalesTickle_DSV = group("SalesTickleDSV_Transaction") {
    exec(session => {
      val length : Int = lengthRedis("ItemsDSV")

      var a = 0;

      for( a <- 1 to length){
        val item = readRedis("ItemsDSV")
        // val item = "72109771"
        val messageText = getSalesTickleXml("SAKS", "ONHAND", "DC-LVG-689", "999999", item)

        sendMessage(messageText, "MCC.INVADJ.DC.MW.OMS.INPUT.PRD")
      }



      session

    })



  }*/

}