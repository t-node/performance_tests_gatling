package NewRelic

import BaseTest.BaseTest
import SaksChains.SaksCheckoutChains.SaksSearchStringPA._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object NewRelicMetrics extends BaseTest{

  val NewRelicMetrics = group("NewRelicMetrics_Transaction") {
    feed(SingleMetric)
      .exec(http("newRelicReq")
      .get("${Url}" + span)
        .basicAuth("Ajay_Indana@s5a.com","NewRelic7!")
        .check(regex(""""y":(\d+\.*\d*)""").findAll.saveAs("yMetric"))
        .check(regex(""""x":(14\d+)""").findAll.saveAs("xMetric"))
        .check(status.is(200))
        .check(bodyString.saveAs("fullBody1"))
    )



      .exec(sessionFunction = session => {

        val xMetric: List[String] = session("xMetric").as[List[String]]
        val yMetric: List[String] = session("yMetric").as[List[String]]
        val mapp = (xMetric zip yMetric).toMap


       // println(mapp)

        var json: String = "[{\"points\":["
        mapp.foreach((e: (String, String)) => json += "[" +  e._2+ ", 1, " + e._1 + "],")
        json = json.dropRight(1)
        json += "],\"name\":\"" + session("titleGraphana").as[String] + "\",\"columns\":[\"value\",\"sequence_number\",\"time\"]}]"
       // println(json)
        // println("response1 : " + session("yMetric").as[List[String]])
        //  println("response2 : " + session("xMetric").as[List[String]])
        // println("cartID Info")
        // println(session("fullBody1").as[String])
        /* println("Diff : " + (System.currentTimeMillis()-session("startTime").as[Long]))
         println("response1 : " + session("responseTime1").as[Long])
         println("response2 : " + session("responseTime2").as[Long])
 */
        session.set("json",json)

      })

      .exec(http("Update InfluxDB")
        .post("http://hd1qjra03lx.digital.hbc.com:8086/db/gatling/series")
        .queryParam("u","root")
        .queryParam("p","root")
        .body(StringBody("""${json}""")).asJSON
        .check(bodyString.saveAs("fullBody1"))
          .check(status.is(200))
      )

    }

  }
