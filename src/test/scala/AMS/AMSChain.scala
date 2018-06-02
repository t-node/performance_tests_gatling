package AMS

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object AMSChain extends BaseTest{

  val url = baseUrlHttp + "/ams"

  val AMS = group("AMS_Transaction") {
     exec(session => {
     val xml = getSOAPXml()
       println(xml)
      session.set("xml",xml)
    })

      .exec(http("AMS_SOAP")
        .post(url)
        .body(StringBody("${xml}"))
          .check(regex("""status""").exists)
          .check(status.is(200))
      )




  }

  }
