package S5AOMS.DataCreation

import OMSBase.OMSBaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object clearSession extends OMSBaseTest {
  //  val random = new util.Random
  //  val feeder = Iterator.continually(Map("email" -> (random.nextString(20) + "@foo.com")))


  val clearSession = group("clearing cache") {
    exec(flushCookieJar)
      .exec(flushSessionCookies)
      .exec(flushHttpCache)
      .exec(session => {

        session.set("flag",1)
      })

  }
}
