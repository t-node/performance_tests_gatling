package Accounts

import BaseTest.BaseTest
import io.gatling.core.Predef._

/**
  * Created by 461967 on 5/17/2016.
  */
class AccountsTest extends BaseTest{

  val UserCredentials = jsonFile("src/test/resources/data/AccountsData/UserCredentials.json").circular
  val UserEmail = jsonFile("src/test/resources/data/AccountsData/CreateAcc.json").circular
  val Address = jsonFile("src/test/resources/data/AccountsData/Address.json").circular
  val PaymentMethods = jsonFile("src/test/resources/data/AccountsData/PaymentMethods.json").circular

  val numOfUsers: Int = System.getProperty("UserCount").toInt

  val duration: Int = System.getProperty("Duration").toInt

  //  val rampUp: Int = System.getProperty("rampUp").toInt

}
