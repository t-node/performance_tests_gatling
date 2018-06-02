package SaksChains.SaksPagnation

import java.util.Random

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains.SaksShoppingBag._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object SaksPagnation extends BaseTest{

  val SaksPagnation = group("Pagnation_Transaction") {
    exec(session => {
      // print the Session for debugging, don't do that on real Simulations
      val startTime = System.currentTimeMillis()
      session.set("startTime", startTime)
    })

          .exec(http("Pagnation")
          .get(baseUrlHttp + "${Links}")
            .queryParam("FOLDER<>folder_id","${folder_id}")
            .queryParam("Nao","60")
          .check(status.is(200))
        )

      .exec(session => {
        val EndTime = System.currentTimeMillis()
        insertGroupResponseTime(session("startTime").as[Long],EndTime,"Saks_Pagnation")
        session

      })

    }

  }
