package SaksO5Chains.SaksO5Pagnation

import java.util.Random

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains.SaksShoppingBag._
import SaksO5Chains.SaksO5CheckoutChains.SaksO5ShoppingBag._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SaksO5Pagnation extends BaseTest{
  val Pagnation_Transaction = instance + "Pagnation_Transaction"
  val Pagnation = "Pagnation"

  val SaksPagnation = group(Pagnation_Transaction) {
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

          .exec(http(Pagnation)
          .get(baseUrlHttp + "${Links}")
            .queryParam("FOLDER<>folder_id","${folder_id}")
            .queryParam("Nao","60")
          .check(status.is(200))
        )

      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session("startTime").as[Long],EndTime,instance + "SaksO5_Pagnation")
        session

      })

    }

  }
