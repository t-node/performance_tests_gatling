package MonitorInstances

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._


/**
  * Created by aindana on 3/14/2016.
  */
object MonitorInstances extends BaseTest{

  val MonitorInstances = group("MonitorInstances_Transaction") {
    forever(
      pace(180)
        .exec(http("prod-web-app1-so5.digital.hbc.com")
        .get("http://prod-web-app1-so5.digital.hbc.com/Entry.jsp")
        .check(status.is(200))
          .check(regex("""saksoff5th.com""").exists)

      )

      .exec(http("prod-web-app2-so5.digital.hbc.com")
      .get("http://prod-web-app2-so5.digital.hbc.com/Entry.jsp")
      .check(status.is(200))
        .check(regex("""saksoff5th.com""").exists)
    )
        .exec(http("prod-web-app3-so5.digital.hbc.com")
          .get("http://prod-web-app3-so5.digital.hbc.com/Entry.jsp")
          .check(status.is(200))
          .check(regex("""saksoff5th.com""").exists)
        )
        .exec(http("prod-web-app4-so5.digital.hbc.com")
          .get("http://prod-web-app4-so5.digital.hbc.com/Entry.jsp")
          .check(status.is(200))
          .check(regex("""saksoff5th.com""").exists)
        )


        .exec(http("prod-web-app5-so5.digital.hbc.com")
          .get("http://prod-web-app5-so5.digital.hbc.com/Entry.jsp")
          .check(status.is(200))
          .check(regex("""saksoff5th.com""").exists)
        )
        .exec(http("prod-web-app6-so5.digital.hbc.com")
          .get("http://prod-web-app6-so5.digital.hbc.com/Entry.jsp")
          .check(status.is(200))
          .check(regex("""saksoff5th.com""").exists)
        )

        .exec(http("m-prod-web-app1-so5.digital.hbc.com")
          .get("http://m-prod-web-app1-so5.digital.hbc.com/Entry.jsp")
          .check(status.is(200))
          .check(regex("""saksoff5th.com""").exists)
        )
        .exec(http("m-prod-web-app2-so5.digital.hbc.com")
          .get("http://m-prod-web-app2-so5.digital.hbc.com/Entry.jsp")
          .check(status.is(200))
          .check(regex("""saksoff5th.com""").exists)
        )
        .exec(http("m-prod-web-app3-so5.digital.hbc.com")
          .get("http://m-prod-web-app3-so5.digital.hbc.com/Entry.jsp")
          .check(status.is(200))
          .check(regex("""saksoff5th.com""").exists)
        )
        .exec(http("m-prod-web-app4-so5.digital.hbc.com")
          .get("http://m-prod-web-app4-so5.digital.hbc.com/Entry.jsp")
          .check(status.is(200))
          .check(regex("""saksoff5th.com""").exists)
        )
        .exec(http("m-prod-web-app5-so5.digital.hbc.com")
          .get("http://m-prod-web-app5-so5.digital.hbc.com/Entry.jsp")
          .check(status.is(200))
          .check(regex("""saksoff5th.com""").exists)
        )

        .exec(http("m-prod-web-app6-so5.digital.hbc.com")
          .get("http://m-prod-web-app6-so5.digital.hbc.com/Entry.jsp")
          .check(status.is(200))
          .check(regex("""saksoff5th.com""").exists)
        )
    )

  }

  }
