package BaseTest

import java.util.Random

import io.gatling.core.Predef._
import org.openqa.selenium.Cookie
import org.openqa.selenium.firefox.FirefoxDriver

import scala.collection.mutable.ArrayBuffer

/**
  * Created by aindana on 3/14/2016.
  */
object BaseTestObj{


  var result = System.getProperty("BaseUrl")
  //val span = System.getProperty("span")
  /*val gatlingConfUrl: Path = getClass.getClassLoader.getResource("gatling.conf").toURI
  val projectRootDir = gatlingConfUrl.ancestor(3)


  //val configPath = System.getProperty("C:\\Gatling\\gatling-maven-plugin-demo\\src\\test\\resources\\")
  val config = ConfigFactory.parseFile(new File("src/test/resources/config.properties"))
//   val conf = ConfigFactory.load(config)*/
  var baseUrlHttp = "http://" + result
  var baseUrlHttps = "https://" + result
  if(result.contains(",")) {

    val urls = result.split(",").map(_.toString).distinct
    val rand = new Random(System.currentTimeMillis());
    val random_index = rand.nextInt(urls.length);
    result = urls(random_index);
    baseUrlHttp = "http://" + result
    baseUrlHttps = "https://" + result
  }
  var instance = result + "_"
  var uri3 = baseUrlHttps + "/account/login.jsp"
  var uri4 = baseUrlHttps + "/account/registration.jsp"

}
