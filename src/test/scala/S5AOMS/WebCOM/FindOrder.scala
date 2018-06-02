package S5AOMS.WebCOM

import OMSBase.OMSBaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object FindOrder extends OMSBaseTest {

  /* val httpProtocol = http
    .baseURL("https://s5aoms.hbc.com")*/

  val headers_12 = Map(
    "SCIAjax" -> "Y",
    "SCIVer" -> "0.9",
    "X-NewRelic-ID" -> "VQQAUl5XDxABXFRbAAQHVg==",
    "X-Requested-With" -> "XMLHttpRequest")


  //val uri1 = "https://s5aoms.hbc.com:443/isccs"

  val FindOrder = group("FindOrder_Transaction") {
    exec(session => {

      //  println(session("fullBody1").as[String])
      val OrderNumber = readRedis("CreateOrderDC2ItemsDC")

      session.set("Org",org).set("OrderNumber",OrderNumber)

    })

      .exec(http("OrderPortletBehaviorController")
      .post("/isccs/scuiimpl/controller/default/isccs.home.portlets.OrderPortletBehaviorController.do")
      .headers(headers_12)
      .formParam("scControllerData", """{"isccs_home_portlets_OrderPortletBehaviorController":{"controllerId":"isccs_home_portlets_OrderPortletBehaviorController","MashupRefs":{"MashupRef":[{"mashupRefId":"getOrderList","Input":{"Order":{"OrderNo":"S5AP100081","MaximumRecords":"2"}}}]}}}""")
      .formParam("scCSRFToken", "${scCSRFToken}")
      .formParam("scFlag", "Y")
      .check(regex("""Order\":\[(.*)]""").saveAs("orderJson"))
      .check(regex("""LastOrderHeaderKey\":\"(\d+)\"""").saveAs("LastOrderHeaderKey"))
    )

      .exec(http("OrderSummaryWizard")
        .post("/isccs/scuiimpl/wizardController.do")
        .headers(headers_12)
        .formParam("scWizardDefId", "isccs.order.wizards.orderSummary.OrderSummaryWizard")
        .formParam("isWizard", "true")
        .formParam("wizardData", """{"scWizardDefId":"isccs.order.wizards.orderSummary.OrderSummaryWizard","isWizard":true,"scControllerInput":{"Order":${orderJson}}}""")
        .formParam("scCSRFToken", "${scCSRFToken}")
        .formParam("scFlag", "Y")
      )

      .exec(http("BusinessCardBehaviorController")
        .post("/isccs/scuiimpl/controller/default/isccs.common.customer.BusinessCardBehaviorController.do")
        .headers(headers_12)
        .formParam("scControllerData", """{"isccs_common_customer_BusinessCardBehaviorController":{"controllerId":"isccs_common_customer_BusinessCardBehaviorController","MashupRefs":{"MashupRef":[{"mashupRefId":"getCustomerDetails","Input":{"Customer":{"CustomerID":"GATLING@test.com","OrganizationCode":"${Org}"}}}]}}}""")
        .formParam("scCSRFToken", "${scCSRFToken}")
        .formParam("scFlag", "Y")
      )
      .exec(http("AddressDisplay")
        .get("/isccs/isccs/common/address/display/templates/AddressDisplay.html")
        .headers(headers_12)
      )
      .exec(http("OrderEditorBehaviorController")
        .post("/isccs/scuiimpl/controller/default/isccs.editors.OrderEditorBehaviorController.do")
        .headers(headers_12)
        .formParam("scControllerData", """{"isccs_editors_OrderEditorBehaviorController":{"controllerId":"isccs_editors_OrderEditorBehaviorController","MashupRefs":{"MashupRef":[{"mashupRefId":"getRequiredEditorAttributes","Input":{"Order":{"OrderHeaderKey":"${LastOrderHeaderKey}"}}}]}}}""")
        .formParam("scCSRFToken", "${scCSRFToken}")
        .formParam("scFlag", "Y")
      )
      .exec(http("OrderSummaryBehaviorController")
        .post("/isccs/scuiimpl/controller/default/isccs.order.details.OrderSummaryBehaviorController.do")
        .headers(headers_12)
        .formParam("scControllerData", """{"isccs_order_details_OrderSummaryBehaviorController":{"controllerId":"isccs_order_details_OrderSummaryBehaviorController","MashupRefs":{"MashupRef":[{"mashupRefId":"extn_getOrganizationHierarchy_ref","Input":{"Organization":{"OrganizationCode":"${Org}"}}}]}}}""")
        .formParam("scCSRFToken", "${scCSRFToken}")
        .formParam("scFlag", "Y")
      )
      .exec(http("AddressDisplay")
        .get("/isccs/isccs/common/address/display/identifiers/US/AddressDisplay.html")
        .headers(headers_12)
      )
      .exec(http("OrderSummaryBehaviorController_1")
        .post("/isccs/scuiimpl/controller/default/isccs.order.details.OrderSummaryBehaviorController.do")
        .headers(headers_12)
        .formParam("scControllerData", """{"isccs_order_details_OrderSummaryBehaviorController":{"controllerId":"isccs_order_details_OrderSummaryBehaviorController","MashupRefs":{"MashupRef":[{"mashupRefId":"extn_customerLanguageMashup","Input":{"Customer":{"CustomerID":"GATLING@test.com","OrganizationCode":"${Org}"}}}]}}}""")
        .formParam("scCSRFToken", "${scCSRFToken}")
        .formParam("scFlag", "Y")
      )
      .exec(http("OrderEditorBehaviorController_2")
        .post("/isccs/scuiimpl/controller/default/isccs.editors.OrderEditorBehaviorController.do")
        .headers(headers_12)
        .formParam("scControllerData", """{"isccs_editors_OrderEditorBehaviorController":{"controllerId":"isccs_editors_OrderEditorBehaviorController","MashupRefs":{"MashupRef":[{"mashupRefId":"getEnterpriseList","Input":{"Organization":{}}}]}}}""")
        .formParam("scCSRFToken", "${scCSRFToken}")
        .formParam("scFlag", "Y")
      )

  }

}
