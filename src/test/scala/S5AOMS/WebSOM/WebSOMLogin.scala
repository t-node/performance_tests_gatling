package S5AOMS.WebSOM

import OMSBase.OMSBaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object WebSOMLogin extends OMSBaseTest {

  val headers_0 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
    "NewHeaderName" -> "New header value",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_1 = Map(
    "Accept" -> "text/css,*/*;q=0.1",
    "NewHeaderName" -> "New header value")

  val headers_4 = Map("NewHeaderName" -> "New header value")

  val headers_5 = Map(
    "NewHeaderName" -> "New header value",
    "X-NewRelic-ID" -> "VQQAUl5XDxABXFRbAAQHVg==",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers_30 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
    "Accept-Encoding" -> "gzip, deflate, br",
    "NewHeaderName" -> "New header value",
    "Origin" -> "https://s5aoms.hbc.com",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_39 = Map(
    "NewHeaderName" -> "New header value",
    "SCIAjax" -> "Y",
    "SCIVer" -> "0.9",
    "X-NewRelic-ID" -> "VQQAUl5XDxABXFRbAAQHVg==",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers_44 = Map(
    "Accept-Encoding" -> "gzip, deflate, br",
    "NewHeaderName" -> "New header value",
    "Origin" -> "https://s5aoms.hbc.com",
    "SCIAjax" -> "Y",
    "SCIVer" -> "0.9",
    "X-NewRelic-ID" -> "VQQAUl5XDxABXFRbAAQHVg==",
    "X-Requested-With" -> "XMLHttpRequest")

  val uri1 = "https://s5aoms.hbc.com:443/wsc"

  val scn = scenario("WebSomLogin")
    .exec(http("request_0")
      .get("/wsc/store/login.do")
      .check(regex("""dojo_opt\.css\.scetag=\?scetag=(\S+)\"""").saveAs("dojo_opt"))
      .check(regex("""dijit_opt\.css\?scetag=(\S+)\"""").saveAs("dijit_opt"))
      .check(regex("""oneui_opt\.css\?scetag=(\S+)\"""").saveAs("oneui_opt"))
      .check(regex("""dojoAll\.js\?scetag=(\S+)\"""").saveAs("dojoAll"))
      .check(regex("""Gridx_opt\.css\?scetag=(\S+)\"""").saveAs("Gridx_opt"))
      .check(regex("""pagination_opt\.css\?scetag=(\S+)\"""").saveAs("pagination_opt"))
      .check(regex("""platform_opt\.css\?scetag=(\S+)\"""").saveAs("platform_opt"))
      .check(regex("""desktop_opt\.css\?scetag=(\S+)\"""").saveAs("desktop_opt"))
      .check(regex("""idxAll\.js\?scetag=(\S+)\"""").saveAs("idxAll"))
      .check(regex("""scDojoWscDesktop\.js\?scetag=(\S+)\"""").saveAs("scDojoWscDesktop"))
      .headers(headers_0))

    .exec(http("request_1")
      .get("/wsc/dojo/dojo/resources/dojo_opt.css?scetag=${dojo_opt}")
      .headers(headers_1)
      .check(status.is(304)))

    .exec(http("request_2")
      .get("/wsc/dojo/dijit/themes/dijit_opt.css?scetag=${dijit_opt}")
      .headers(headers_1)
      .check(status.is(304)))

    .exec(http("request_3")
      .get("/wsc/ibmjs/idx/themes/oneui/oneui_opt.css?scetag=${oneui_opt}")
      .headers(headers_1)
      .check(status.is(304)))

    .exec(http("request_4")
      .get("/wsc/dojo/dojo/dojoAll.js?scetag=${dojoAll}")
      .headers(headers_4)
      .check(status.is(304)))

    .exec(http("request_5")
      .get("/wsc/ibmjs/idx/widget/templates/Dialog.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_6")
      .get("/wsc/ibmjs/idx/app/templates/LoginFrame.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_7")
      .get("/wsc/ibmjs/idx/widget/templates/HoverHelpTooltip.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_8")
      .get("/wsc/ibmjs/idx/form/templates/TextBox.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_9")
      .get("/wsc/ibmjs/idx/widget/templates/ModalDialog.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_10")
      .get("/wsc/ibmjs/idx/widget/templates/ConfirmationDialog.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_11")
      .get("/wsc/ibmjs/idx/form/templates/CheckBox.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_12")
      .get("/wsc/platform/scripts/sc/plat/dojo/widgets/templates/Link.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_13")
      .get("/wsc/platform/scripts/sc/plat/dojo/widgets/templates/Label.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_14")
      .get("/wsc/platform/scripts/sc/plat/dojo/widgets/templates/DataLabel.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_15")
      .get("/wsc/platform/scripts/sc/plat/dojo/widgets/templates/Image.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_16")
      .get("/wsc/ibmjs/idx/widget/templates/SingleMessage.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_17")
      .get("/wsc/ibmjs/idx/form/templates/ComboBox.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_18")
      .get("/wsc/platform/scripts/sc/plat/dojo/widgets/templates/Padss.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_19")
      .get("/wsc/platform/scripts/sc/plat/dojo/widgets/templates/IFrame.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_20")
      .get("/wsc/ibmjs/idx/form/templates/Textarea.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_21")
      .get("/wsc/ibmjs/idx/form/templates/Link.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_22")
      .get("/wsc/platform/scripts/sc/plat/dojo/scDojoPlat.js?scetag=${scDojoPlat}")
      .headers(headers_4)
      .check(status.is(304)))

    .exec(http("request_23")
      .get("/wsc/dojo/gridx/resources/Gridx_opt.css?scetag=${Gridx_opt}")
      .headers(headers_4)
      .check(status.is(304)))

    .exec(http("request_24")
      .get("/wsc/ibmjs/idx/idxAll.js?scetag=${idxAll}")
      .headers(headers_4)
      .check(status.is(304)))

    .exec(http("request_26")
      .get("/wsc/wsc/desktop/scripts/scDojoWscDesktop.js?scetag=${scDojoWscDesktop}")
      .headers(headers_4)
      .check(status.is(304)))

    .exec(http("request_27")
      .get("/wsc/ibmjs/idx/themes/oneui/idx/gridx/pagination_opt.css?scetag=${pagination_opt}")
      .headers(headers_4)
      .check(status.is(304)))

    .exec(http("request_28")
      .get("/wsc/platform/scripts/sc/plat/dojo/themes/claro/platform_opt.css?scetag=${platform_opt}")
      .headers(headers_4)
      .check(status.is(304)))

    .exec(http("request_29")
      .get("/wsc/wsc/resources/css/desktop_opt.css?scetag=${desktop_opt}")
      .headers(headers_4)
      .check(status.is(304)))


    .exec(http("request_30")
      .post("/wsc/store/processLogin.do")
      .headers(headers_30)
      .formParam("DisplayUserID", "prod1")
      .formParam("Password", "prod1")
      .formParam("EnterpriseCode", "")
      .formParam("StoreId", "8805")
      .formParam("StoreLocaleCode", "")
      .formParam("embedded", "")
      .check(regex("""resource\.js\?scetag=(\S+)\"""").saveAs("resource"))
      .check(regex("""DataType\.js\?scetag=(\S+)\"""").saveAs("DataType"))
      .check(regex("""SCScreenExtnManager\.js\?scetag=(\S+)\"""").saveAs("SCScreenExtnManager"))
      .check(regex("""scExtnAll\.js\?scetag=(\S+)\"""").saveAs("scExtnAll"))
      .check(regex("""scDojoIas\.js\?scetag=(\S+)\"""").saveAs("scDojoIas"))
      .check(regex("""scCSRFToken=(\S+)\'""").saveAs("scCSRFToken")))

    .exec(http("request_31")
      .get("/wsc/dojo/permission/resource.js?scetag=${resource}")
      .headers(headers_4))

    .exec(http("request_32")
      .get("/wsc/dojoDataType/DataType.js?scetag=${DataType}")
      .headers(headers_4)
      .check(status.is(304)))

    .exec(http("request_33")
      .get("/wsc/dojoScreenExtns/SCScreenExtnManager.js?scetag=${SCScreenExtnManager}")
      .headers(headers_4)
      .check(status.is(304)))

    .exec(http("request_34")
      .get("/wsc/extn/scExtnAll.js?scetag=${scExtnAll}")
      .headers(headers_4)
      .check(status.is(304)))

    .exec(http("request_35")
      .get("/wsc/ias/scripts/scDojoIas.js?scetag=${scDojoIas}")
      .headers(headers_4)
      .check(status.is(304)))

    .exec(http("request_36")
      .get("/wsc/ibmjs/idx/form/templates/CurrencyTextBox.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_37")
      .get("/wsc/ibmjs/idx/form/templates/RadioButtonSet.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_38")
      .get("/wsc/ibmjs/idx/form/templates/CheckBoxList.html")
      .headers(headers_5)
      .check(status.is(304)))

    .exec(http("request_39")
      .get("/wsc/dojo/dijit/nls/loading.js?scFlag=Y")
      .headers(headers_39)
      .check(status.is(304)))

    .exec(http("request_40")
      .get("/wsc/extn/nls/Extension_bundle.js?scFlag=Y")
      .headers(headers_39)
      .check(status.is(304)))

    .exec(http("request_41")
      .get("/wsc/extn/desktop/home/templates/HomeExtn.html")
      .headers(headers_39)
      .check(status.is(304)))

    .exec(http("request_42")
      .get("/wsc/extn/customScreen/templates/StoreContextPortlet.html")
      .headers(headers_39)
      .check(status.is(304)))

    .exec(http("request_43")
      .get("/wsc/extn/customScreen/templates/CustomerServicePortlet.html")
      .headers(headers_39)
      .check(status.is(304)))

    .exec(http("request_44")
      .post("/wsc/scuiimpl/controller/default/extn.customScreen.CustomerServicePortletBehaviorController.do")
      .headers(headers_44)
      .formParam("scControllerData", """{"extn_customScreen_CustomerServicePortletBehaviorController":{"controllerId":"extn_customScreen_CustomerServicePortletBehaviorController","MashupRefs":{"MashupRef":[{"mashupRefId":"getUserData_ref","Input":{"User":{"UserId":"prod1","StoreContext":"isccs"}}}]}}}""")
      .formParam("scCSRFToken", "${scCSRFToken}")
      .formParam("scFlag", "Y"))

    .exec(http("request_45")
      .get("/wsc/extn/desktop/home/portlets/templates/PackPortletExtn.html")
      .headers(headers_39)
      .check(status.is(304)))

    .exec(http("request_46")
      .get("/wsc/extn/desktop/home/portlets/templates/ConfirmShipmentPortletExtn.html")
      .headers(headers_39)
      .check(status.is(304)))

    .exec(http("request_47")
      .get("/wsc/extn/desktop/home/portlets/templates/BackroomPickPortletExtn.html")
      .headers(headers_39)
      .check(status.is(304)))

    .exec(http("request_48")
      .post("/wsc/scuiimpl/controller/default/wsc.desktop.home.portlets.ConfirmShipmentPortletInitController.do")
      .headers(headers_44)
      .formParam("scControllerInput", "{}")
      .formParam("scControllerData", """{"wsc_desktop_home_portlets_ConfirmShipmentPortletInitController":{"controllerId":"wsc_desktop_home_portlets_ConfirmShipmentPortletInitController","MashupRefs":{"MashupRef":[{"mashupRefId":"getScaclist","Input":{}}]}}}""")
      .formParam("scCSRFToken", "${scCSRFToken}")
      .formParam("scFlag", "Y"))

    .exec(http("request_49")
      .post("/wsc/scuiimpl/controller/default/wsc.desktop.home.portlets.BackroomPickPortletInitController.do")
      .headers(headers_44)
      .formParam("scControllerInput", "{}")
      .formParam("scControllerData", """{"wsc_desktop_home_portlets_BackroomPickPortletInitController":{"controllerId":"wsc_desktop_home_portlets_BackroomPickPortletInitController","MashupRefs":{"MashupRef":[{"mashupRefId":"getShipmentListReadyForPickInit","Input":{}},{"mashupRefId":"getShipmentListReadyForShipInit","Input":{}}]}}}""")
      .formParam("scCSRFToken", "${scCSRFToken}")
      .formParam("scFlag", "Y"))

    .exec(http("request_50")
      .post("/wsc/scuiimpl/controller/default/wsc.desktop.home.portlets.PackPortletBehaviorController.do")
      .headers(headers_44)
      .formParam("scControllerData", """{"wsc_desktop_home_portlets_PackPortletBehaviorController":{"controllerId":"wsc_desktop_home_portlets_PackPortletBehaviorController","MashupRefs":{"MashupRef":[{"mashupRefId":"getShipmentListPackCount","Input":{"Shipment":{}}}]}}}""")
      .formParam("scCSRFToken", "${scCSRFToken}")
      .formParam("scFlag", "Y"))

    .exec(http("request_51")
      .get("/wsc/extn/components/shipment/container/pack/templates/ItemPackExtn.html")
      .headers(headers_39)
      .check(status.is(304)))

    .exec(http("request_52")
      .post("/wsc/scuiimpl/controller/default/wsc.components.shipment.container.pack.ItemPackInitController.do")
      .headers(headers_44)
      .formParam("scControllerInput", "{}")
      .formParam("scControllerData", """{"wsc_components_shipment_container_pack_ItemPackInitController":{"controllerId":"wsc_components_shipment_container_pack_ItemPackInitController","MashupRefs":{"MashupRef":[{"mashupRefId":"pack_getRuleForItemPack","Input":{}}]}}}""")
      .formParam("scCSRFToken", "${scCSRFToken}")
      .formParam("scFlag", "Y"))

    .exec(http("request_53")
      .post("/wsc/scuiimpl/controller/default/extn.customScreen.StoreContextPortletBehaviorController.do")
      .headers(headers_44)
      .formParam("scControllerData", """{"extn_customScreen_StoreContextPortletBehaviorController":{"controllerId":"extn_customScreen_StoreContextPortletBehaviorController","MashupRefs":{"MashupRef":[{"mashupRefId":"getUserHierarchy_ref","Input":{"User":{"Loginid":"prod1"}}}]}}}""")
      .formParam("scCSRFToken", "${scCSRFToken}")
      .formParam("scFlag", "Y"))

  }

