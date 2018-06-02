package BaseTest

import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.{Calendar, Date}
import org.apache.http.client.methods.HttpOptions
import org.openqa.selenium.firefox.FirefoxDriver

import scala.collection.JavaConversions._

import java.io.File
import com.typesafe.config.{ Config, ConfigFactory }
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration._
import scala.xml.XML
import org.scalatest.FlatSpec
import org.openqa.selenium._
import org.json4s._
import org.json4s.native.JsonMethods._
import scalaj.http._
import scalaj.http.HttpOptions

class BaseTest extends Simulation {

  val baseUrl = System.getProperty("BaseUrl").toString

  val instance = baseUrl + "_"

    val span =""
    /*val gatlingConfUrl: Path = getClass.getClassLoader.getResource("gatling.conf").toURI
    val projectRootDir = gatlingConfUrl.ancestor(3)


    //val configPath = System.getProperty("C:\\Gatling\\gatling-maven-plugin-demo\\src\\test\\resources\\")
    val config = ConfigFactory.parseFile(new File("src/test/resources/config.properties"))
 //   val conf = ConfigFactory.load(config)*/

  val baseUrlHttp = "http://" + baseUrl
  val baseUrlHttps = "https://" + baseUrl



 /* val saksUrlHttp = "http://www.qa.saks.com"
  val saksUrlHttps = "https://www.qa.saks.com"*/

  val firstName ="Performance"
  val lastName = "Script"
  val password = "Password123"
  val ProductsPA = jsonFile("src/test/resources/data/ProductsPA.json").circular
  val Links = jsonFile("src/test/resources/data/Links.json").random
  val saksO5Links = jsonFile("src/test/resources/data/saksoff5Links.json").random
  val saksO5LinksProd = jsonFile("src/test/resources/data/saksoff5LinksProd.json").random
  val ItemIDs = jsonFile("src/test/resources/data/ItemIDs.json").random
  val SingleMetric = jsonFile("src/test/resources/data/SingleMetric.json").circular
  val SearchString = jsonFile("src/test/resources/data/SearchString.json").random
  val BayLinks = jsonFile("src/test/resources/data/BayLinks.json").random
  val LTLinks = jsonFile("src/test/resources/data/LTLinks.json").random
//  val ProductID = jsonFile("src/test/resources/data/ProductID.json").circular
  val ShippingDetails = jsonFile("src/test/resources/data/ShippingDetails.json").circular
  val BillingDetails = jsonFile("src/test/resources/data/BillingDetails.json").circular



  val Products = jsonFile("src/test/resources/data/Products.json").circular




  // Place Orders

  val ShippingDetailsPO = jsonFile("src/test/resources/data/PlaceOrdersData/ShippingDetails.json").circular
  val BillingDetailsPO = jsonFile("src/test/resources/data/PlaceOrdersData/BillingDetails.json").circular
  val DCProducts = jsonFile("src/test/resources/data/PlaceOrdersData/DCProducts.json").circular
  val StoreProducts = jsonFile("src/test/resources/data/PlaceOrdersData/StoreProducts.json").circular
  val DCStoreProducts = jsonFile("src/test/resources/data/PlaceOrdersData/DCStoreProducts.json").circular
  //var email = ""

  //Session variables

 /* var bmUID =
  var bmFormID = ""*/

  var errorSet = scala.collection.immutable.HashSet("")

  val httpConf = http
    // .baseURL("http://www.qa.saks.com/") // Here is the root for all relative URLs
    .acceptHeader("application/json,text/javascript,text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptLanguageHeader("en-US,en;q=0.5")
    .header("LoadTesting","Gatling")
    .header("Cookie","PT=Gatling")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    .inferHtmlResources(WhiteList(""".*\.js""", """.*\.css"""),BlackList(""".*\.png"""))

  //    .silentResources

  val httpConfChrome = http
    // .baseURL("http://www.qa.saks.com/") // Here is the root for all relative URLs
    .acceptHeader("application/json,text/javascript,text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptLanguageHeader("en-US,en;q=0.5")
    .header("LoadTesting","Gatling")
    .header("Cookie","PT=Gatling")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    .inferHtmlResources(WhiteList(""".*\.js""", """.*\.css"""),BlackList(""".*\.png"""))
    .maxConnectionsPerHostLikeChrome
    //.silentResources

  val httpConfFireFox = http
    // .baseURL("http://www.qa.saks.com/") // Here is the root for all relative URLs
    .acceptHeader("application/json,text/javascript,text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptLanguageHeader("en-US,en;q=0.5")
    .header("LoadTesting","Gatling")
    .header("Cookie","PT=Gatling")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    .inferHtmlResources(WhiteList(""".*\.js""", """.*\.css"""),BlackList(""".*\.png"""))
    .maxConnectionsPerHostLikeFirefox
    //.silentResources

    /*  .extraInfoExtractor {
        extraInfo =>
          extraInfo.status match {
            case io.gatling.core.result.message.KO => {
              var errorLabel = extraInfo.requestName + "-" + extraInfo.response.statusCode.getOrElse("501")
              if (!errorSet.contains(errorLabel)) {
                errorSet += errorLabel
                println("Error Label: " + errorLabel)
                println("Response Body" + extraInfo.response.body.string)
              }
            }
              Nil
            case _ => Nil
          }
      }*/

  //val address1 = java.net.InetAddress.getByName("202.168.223.60")
  val httpConf1 = http
    // .baseURL("http://www.qa.saks.com/") // Here is the root for all relative URLs
    .acceptHeader("application/json,text/javascript,text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptLanguageHeader("en-US,en;q=0.5")
    .header("LoadTesting","Gatling")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    .inferHtmlResources(WhiteList(""".*\.js""", """.*\.css"""),BlackList(""".*\.png"""))
   //   .silentResources

 /* def setbmUID(value:String) = {

    bmUID = value

  }

  def setbmFormID(value:String) = {

    bmFormID = value

  }

  def getbmUID() : String= {

    return bmUID

  }

  def getbmFormID() : String = {

    return bmFormID

  }*/





  def generateRandomEmail() : String = {

    val timestamp: Long = System.currentTimeMillis
    val cremail = "test" + timestamp + "@script.com"
    println("creating " + cremail)

    return cremail
  }

  def insertGroupResponseTime(start:Long, end:Long, transaction:String) = {
/*
    val timestamp: Long = System.currentTimeMillis

    val diff = end-start
   /* var json: String = "[{\"points\":[[" +  diff+ ", 1, " + timestamp + "]"
    json += "],\"name\":\"" + transaction + "\",\"columns\":[\"value\",\"sequence_number\",\"time\"]}]"
   */// println("responseTime: " + diff)

    var json: String = transaction + " value=" + diff

    val result = Http("http://hd1qjra03lx.digital.hbc.com:8086/write")
        .param("db","gatling")
        .postData(json)
      //.header("Content-Type", "application/json")
      .header("Charset", "UTF-8").asString

   // println("result: " + result)*/

  }

  def getSOAPXml(): String = {
    val now = Calendar.getInstance().getTime()
    val minuteFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss")
    val currentDate = minuteFormat.format(now)

    val buf = new StringBuilder
    buf.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ams=\"http://localhost/yb/amssoap\">")
    buf.append("<soapenv:Header/>")
    buf.append("<soapenv:Body>")
    buf.append("<ams:processTrans>")
    buf.append("<request>")
    buf.append("<item><key>location</key><value>21-6199</value></item>")
    buf.append("<item><key>termnum</key><value>12345678</value></item>")
    buf.append("<item><key>transnum</key><value>7523676412345679</value></item>")
    buf.append("<item><key>querytype</key><value>1</value></item>")
    buf.append("<item><key>timestamp</key><value>" + currentDate + "</value></item>")
    buf.append("<item><key>languagecode</key><value>en-CA</value></item>")
    buf.append("<item><key>item10001</key><value>0|0500088469424|2|10000|0839|1|1|10001|1|0</value></item>")
    buf.append("</request>")
    buf.append("</ams:processTrans>")
    buf.append("</soapenv:Body>")
    buf.append("</soapenv:Envelope>")

    return buf.toString
  }


  def insertGroupResponseTime(session:String, transaction:String) = {

    val pattern = "GroupBlock\\(List\\(\\S+\\),\\d+,(\\d+)".r


    val diff: String = (pattern findAllIn session).mkString(",").split(",").last

    var json: String = transaction + " value=" + diff

    val result = Http("http://hd1qjra03lx.digital.hbc.com:8086/write")
      .param("db","gatling")
      .postData(json)
      //.header("Content-Type", "application/json")
      .header("Charset", "UTF-8").asString


  }


  /*def readXml(tagName:String, path:String) : String = {

    val data = XML.loadFile("repo/homePageRepo.xml");

  }*/




}
