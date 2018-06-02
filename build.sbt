enablePlugins(GatlingPlugin)

//fork in run := true

scalaVersion := "2.11.7"

scalacOptions := Seq(
  "-encoding", "UTF-8", "-target:jvm-1.7", "-deprecation",
  "-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

//javaOptions in Gatling := Seq("-Xms2G", "-Xmx5G")

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.1.7" % "test"
libraryDependencies += "io.gatling"            % "gatling-test-framework"    % "2.1.7" % "test"
libraryDependencies += "org.seleniumhq.selenium" % "selenium-java" % "2.53.0" % "test"
libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
libraryDependencies += "org.scalaj" % "scalaj-http_2.11" % "2.3.0"
libraryDependencies += "net.liftweb" %% "lift-json" % "2.6"
libraryDependencies += "com.jayway.restassured" % "scala-support" % "2.9.0"
libraryDependencies += "net.debasishg" %% "redisclient" % "3.0"
libraryDependencies += "org.json4s" %% "json4s-native" % "3.3.0"
libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.3.0"
libraryDependencies += "com.google.code.gson" % "gson" % "2.7"

unmanagedJars in Compile <<= baseDirectory map { base =>
  val libs = base / "libraries"
  val dirs = (libs / "jars")
  (dirs ** "*.jar").classpath }