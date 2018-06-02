package Accounts

import com.google.gson.JsonObject

/**
  * Created by 461967 on 9/22/2016.
  */
object AddressMethods extends AccountsTest{

  def CreateAddrBody(addrType : String) : JsonObject = {
    val body = new JsonObject()
    body.addProperty("is_default", true)
    body.addProperty("address1", "${address1}")
    body.addProperty("address2", "")
    body.addProperty("city", "${city}")
    body.addProperty("state", "${state}")
    body.addProperty("zip", "${zip}")
    body.addProperty("country", "${country}")
    body.addProperty("title","")
    body.addProperty("first_name", "fname")
    body.addProperty("last_name", "lname")
    body.addProperty("middle_name", "T")
    body.addProperty("phone", "1111111111")
    body.addProperty("address_type", addrType)
    body.addProperty("company", "HBC")

    return body
  }

  def CreateAddrUrl(addrType : String) : String = {
    return baseUrlHttps + "/v1/account-service/account/address-book?address_type=" + addrType
  }

}
