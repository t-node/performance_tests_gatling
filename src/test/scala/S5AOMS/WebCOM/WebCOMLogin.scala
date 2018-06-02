package S5AOMS.WebCOM

import OMSBase.OMSBaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object WebCOMLogin extends OMSBaseTest {

 /* val httpProtocol = http
    .baseURL("https://s5aoms.hbc.com")*/

  val headers_2 = Map("Accept" -> "*/*")

  val headers_6 = Map(
    "X-NewRelic-ID" -> "VQQAUl5XDxABXFRbAAQHVg==",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers_12 = Map(
    "SCIAjax" -> "Y",
    "SCIVer" -> "0.9",
    "X-NewRelic-ID" -> "VQQAUl5XDxABXFRbAAQHVg==",
    "X-Requested-With" -> "XMLHttpRequest")

  val WebCOMLogin = group("WebCOMLogin_Transaction") {
    feed(WebCOMcredentials)
      .exec(http("WebCOMHome")
        .get("/isccs/isccs/login.do")
        .check(status.is(200))
        .check(regex("""IBM""").exists)
        //  .check(bodyString.saveAs("fullBody1"))
      )

      .exec(http("processLogin")
        .post("/isccs/isccs/processLogin.do")
        .formParam("DisplayUserID", "produser1")
        .formParam("Password", "produser1")
        .formParam("EnterpriseCode", "")
     //   .check(bodyString.saveAs("fullBody1"))
          .check(regex("""resource\.js\?scetag=(\S+)\"""").saveAs("resource"))
        .check(regex("""DataType\.js\?scetag=(\S+)\"""").saveAs("DataType"))
        .check(regex("""SCScreenExtnManager\.js\?scetag=(\S+)\"""").saveAs("SCScreenExtnManager"))
        .check(regex("""scExtnAll\.js\?scetag=(\S+)\"""").saveAs("scExtnAll"))
        .check(regex("""scCSRFToken=(\S+)\'""").saveAs("scCSRFToken"))
      )

      .exec(http("resource.js")
        .get("/isccs/dojo/permission/resource.js?scetag=${resource}")
        .headers(headers_2))
      .exec(http("DataType.js")
        .get("/isccs/dojoDataType/DataType.js?scetag=${DataType}")
        .headers(headers_2))
      .exec(http("SCScreenExtnManager.js")
        .get("/isccs/dojoScreenExtns/SCScreenExtnManager.js?scetag=${SCScreenExtnManager}")
        .headers(headers_2))
      .exec(http("scExtnAll.js")
        .get("/isccs/extn/scExtnAll.js?scetag=${scExtnAll}")
        .headers(headers_2))
      .exec(http("CheckBox.html")
        .get("/isccs/ibmjs/idx/form/templates/CheckBox.html")
        .headers(headers_6))
      .exec(http("Textarea")
        .get("/isccs/ibmjs/idx/form/templates/Textarea.html")
        .headers(headers_6))
      .exec(http("ComboBox")
        .get("/isccs/ibmjs/idx/form/templates/ComboBox.html")
        .headers(headers_6))
      .exec(http("RadioButtonSet")
        .get("/isccs/ibmjs/idx/form/templates/RadioButtonSet.html")
        .headers(headers_6))
      .exec(http("CheckBoxList")
        .get("/isccs/ibmjs/idx/form/templates/CheckBoxList.html")
        .headers(headers_6))
      .exec(http("CurrencyTextBox")
        .get("/isccs/ibmjs/idx/form/templates/CurrencyTextBox.html")
        .headers(headers_6))
      .exec(http("loading")
        .get("/isccs/dojo/dijit/nls/loading.js?scFlag=Y")
        .headers(headers_12))

      .exec(http("HomeInitController")
        .post("/isccs/scuiimpl/controller/default/isccs.home.HomeInitController.do")
        .headers(headers_12)
        .formParam("scControllerInput", "{}")
        .formParam("scControllerData", """{"isccs_home_HomeInitController":{"controllerId":"isccs_home_HomeInitController","MashupRefs":{"MashupRef":[]},"ChildControllers":{"ChildController":[{"controllerId":"isccs_home_portlets_OrderPortletInitController","MashupRefs":{"MashupRef":[{"mashupRefId":"getOrganizationList","Input":{},"mashupRefObj":{"sourceNamespace":"getOrganizationList_output","callSequence":"","mashupRefId":"getOrganizationList","sequence":"","sourceBindingOptions":"","mashupId":"orderPortlet_getOrganizationList"}}]}},{"controllerId":"isccs_home_portlets_CustomerPortletInitController","MashupRefs":{"MashupRef":[{"mashupRefId":"getCustomerOrganizationList","Input":{},"mashupRefObj":{"sourceNamespace":"getHomeCustomerOrganizationList_output","callSequence":"","mashupRefId":"getCustomerOrganizationList","sequence":"4","sourceBindingOptions":"","mashupId":"customerSearch_getOrganizationList"}},{"mashupRefId":"getDisplayRuleDetails","Input":{},"mashupRefObj":{"sourceNamespace":"getHomeDisplayCustomerTypeRule_output","callSequence":"","mashupRefId":"getDisplayRuleDetails","sequence":"6","sourceBindingOptions":"","mashupId":"customerSearch_getDisplayCustomerTypeRule"}},{"mashupRefId":"getDefaultRuleDetails","Input":{},"mashupRefObj":{"sourceNamespace":"getHomeDefaultCustomerTypeRule_output","callSequence":"","mashupRefId":"getDefaultRuleDetails","sequence":"5","sourceBindingOptions":"","mashupId":"customerSearch_getDefaultCustomerTypeRule"}}]}},{"controllerId":"isccs_home_portlets_ReturnsPortletInitController","MashupRefs":{"MashupRef":[{"mashupRefId":"getReturnOrganizationList","Input":{},"mashupRefObj":{"sourceNamespace":"getOrganizationList_output","callSequence":"","mashupRefId":"getReturnOrganizationList","sequence":"","sourceBindingOptions":"","mashupId":"orderPortlet_getOrganizationList"}}]}},{"controllerId":"isccs_home_portlets_ProductPortletInitController","MashupRefs":{"MashupRef":[{"mashupRefId":"getOrgList","Input":{},"mashupRefObj":{"sourceNamespace":"enterpriseList","callSequence":"","mashupRefId":"getOrgList","sequence":"","sourceBindingOptions":"","mashupId":"productBrowsing_getOrganizationList"}}]}}]}}}""")
        .formParam("scCSRFToken", "${scCSRFToken}")
        .formParam("scFlag", "Y"))

      .exec(http("MyAlertsPortletBehaviorController")
        .post("/isccs/scuiimpl/controller/default/isccs.home.portlets.MyAlertsPortletBehaviorController.do")
        .headers(headers_12)
        .formParam("scControllerData", """{"isccs_home_portlets_MyAlertsPortletBehaviorController":{"controllerId":"isccs_home_portlets_MyAlertsPortletBehaviorController","MashupRefs":{"MashupRef":[{"mashupRefId":"getMyAlertsAndQueues","Input":{"getAlertStatisticsForUser":{}}}]}}}""")
        .formParam("scCSRFToken", "${scCSRFToken}")
        .formParam("scFlag", "Y"))

      .exec(http("HomeEditorBehaviorController")
        .post("/isccs/scuiimpl/controller/default/isccs.editors.HomeEditorBehaviorController.do")
        .headers(headers_12)
        .formParam("scControllerData", """{"isccs_editors_HomeEditorBehaviorController":{"controllerId":"isccs_editors_HomeEditorBehaviorController","MashupRefs":{"MashupRef":[{"mashupRefId":"getEnterpriseList","Input":{"Organization":{}}}]}}}""")
        .formParam("scCSRFToken", "${scCSRFToken}")
        .formParam("scFlag", "Y"))

      .exec(session => {

    //  println(session("fullBody1").as[String])

      session.set("Org",org)

    })

  }

}
