package BaseTest

import java.util.Date

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.openqa.selenium.Cookie
import org.openqa.selenium.firefox.FirefoxDriver
import org.scalatest.selenium.Firefox

import scala.collection.mutable.ArrayBuffer

/**
  * Created by aindana on 3/14/2016.
  */
object BaseObj extends BaseTest{

  val driver = new FirefoxDriver

  def syncCookies(cookieList :ArrayBuffer[String]) =  {

    try{
      println(cookieList.length)
      for ( i <- 0 to (cookieList.length - 1)) {
        if(cookieList.get(i)!=null||(!cookieList.get(i).equalsIgnoreCase(""))){
          println(cookieList.get(i).split(";").get(0).split("=").get(0))
          println(cookieList.get(i).split(";").get(0).split("=").get(1))
          val cookie = new Cookie(cookieList.get(i).split(";").get(0).split("=").get(0), cookieList.get(i).split(";").get(0).split("=").get(1))

          driver.manage().addCookie(cookie)

        }
      }
    } catch {
      case e: Exception => println("");
    }


  }


}
