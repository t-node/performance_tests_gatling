package OMSBase

import java.text.SimpleDateFormat
import java.util.{Hashtable => JHashtable, Calendar, Date}
import javax.jms.Session
import javax.jms._
import javax.naming.{NamingException, InitialContext, Context}

import com.redis.RedisClient
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.collection.mutable.ListBuffer
import scala.xml.Elem
import scalaj.http._


class OMSBaseTest extends Simulation {

  val org = "SAKS"

  // val baseUrl = System.getProperty("BaseUrl").toString

  val r = new RedisClient("hd1qjra03lx.digital.hbc.com", 6379)

  val httpConf = http
    // .baseURL("http://www.qa.saks.com/") // Here is the root for all relative URLs
    .acceptHeader("application/json,text/javascript,text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptLanguageHeader("en-US,en;q=0.5")
    .header("LoadTesting", "Gatling")
    .header("Cookie", "PT=Gatling")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    .inferHtmlResources(white = WhiteList(""".*\.*"""))
    .baseURL("https://s5aoms.hbc.com")
    .silentResources


  def sendMessage(messageText: String, Queue: String) {

    val DEFAULT_QUEUE_NAME = Queue
    val DEFAULT_QCF_NAME = "QCF_OMS"
     // val DEFAULT_URL = "file:/C:\\PT\\performance-testing-gatling\\binding\\saks_release2\\prod"
    val DEFAULT_USER = "omssaks"
    val DEFAULT_PASSWORD = "omssaks"
    val DEFAULT_URL = "file:binding/saks_release2/prod"

    // create InitialContext
    val properties = new JHashtable[String, String]
    properties.put(Context.INITIAL_CONTEXT_FACTORY,
      "com.sun.jndi.fscontext.RefFSContextFactory")
    properties.put(Context.PROVIDER_URL, DEFAULT_URL)
    properties.put(Context.SECURITY_PRINCIPAL, DEFAULT_USER)
    properties.put(Context.SECURITY_CREDENTIALS, DEFAULT_PASSWORD)

    try {
      val ctx = new InitialContext(properties)
      // println("Got InitialContext " + ctx.toString())
      // create QueueConnectionFactory
      val qcf = (ctx.lookup(DEFAULT_QCF_NAME)).asInstanceOf[QueueConnectionFactory]
      // println("Got QueueConnectionFactory " + qcf.toString())
      // create QueueConnection
      val qc = qcf.createQueueConnection()
      // println("Got QueueConnection " + qc.toString())
      // create QueueSession
      val qsess = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE)
      //  println("Got QueueSession " + qsess.toString())
      // lookup Queue
      val q = (ctx.lookup(DEFAULT_QUEUE_NAME)).asInstanceOf[Queue]
      // println("Got Queue " + q.toString())
      // create QueueSender
      val qsndr = qsess.createSender(q)
      //  println("Got QueueSender " + qsndr.toString())
      // create TextMessage
      val message = qsess.createTextMessage()

      message.setJMSType("xml")
      message.setText(messageText)
      message.setJMSTimestamp(new Date().getTime());
      message.setJMSDeliveryMode(DeliveryMode.PERSISTENT)
      message.setJMSDestination(qsndr.getDestination)
      message.setJMSMessageID("1")
      message.setJMSCorrelationID("abc")
      message.setJMSRedelivered(true)

      // val message2 = MessageBuilder.withPayload("Hello").setHeader("foo", "foo").setHeader("bar", "bar").build
      // messageFlow.send(message)
      //   println(message.getJMSDeliveryMode);
      //     println("Got TextMessage " + message.toString)
      /* // set message text in TextMessage
       message.setText(messageText)
       println("Set text in TextMessage " + message.toString())*/
      // send message
      qsndr.send(message)
      //    println("Sent message ")
      //     println(message.getJMSDeliveryMode);
      qsndr.close()
      qsess.close()
      qc.close()
    } catch {
      case ne: NamingException =>
        ne.printStackTrace(System.err)
        System.exit(0)
      case jmse: JMSException =>
        jmse.printStackTrace(System.err)
        System.exit(0)
      case e: Exception =>
        println(e)
        System.exit(0)
    }
  }


  def getArg0(userId: String, password: String): String = {
    val arg0 = "&lt;YFSEnvironment userId=\"" + userId + "\" password=\"" + password + "\"/&gt;"
    return arg0
  }

  def getReserveArg1(Items: List[String], OrderNumber: String, zipCode: String, state: String): String = {
    val arg1 = new StringBuilder
    var LineId = 1
    arg1.append("&lt;Promise Action=\"Create\" CheckInventory=\"Y\" CheckCapacity=\"Y\" DistanceToConsider=\"5000\" DistanceUOMToConsider=\"MILE\" EnterpriseCode=\"SAKS\" EntryType=\"Online\" FulfillmentType=\"SHIP\" OrganizationCode=\"SAKS\"&gt;")
    arg1.append("&lt;ReservationParameters ExpirationDate=\"2018-12-02T12:53:46-04:00\" ReservationID=\"" + OrderNumber + "\"/&gt;")
    arg1.append("&lt;PromiseLines&gt;")
    Items.foreach {
      item =>
        arg1.append("&lt;PromiseLine FulfillmentType=\"SHIP\" ItemID=\"" + item + "\" LineId=\"" + LineId + "\" RequiredQty=\"1\" UnitOfMeasure=\"EACH\"&gt;&lt;ShipToAddress Country=\"US\" State=\"" + state + "\" ZipCode=\"" + zipCode + "\"/&gt;&lt;/PromiseLine&gt;")
        LineId = LineId + 1
    }
    arg1.append("&lt;/PromiseLines&gt;&lt;/Promise&gt;")
    return arg1.toString
  }

  def getOrderLines(Items: List[String], OrderNumber: String): String = {
    val orderLines = new StringBuilder
    var LineId = 1
    orderLines.append("<OrderLines>")
    Items.foreach {
      item =>
        orderLines.append("<OrderLine PrimeLineNo=\"" + LineId + "\" SubLineNo=\"1\" OrderedQty=\"1\" CarrierServiceCode=\"Standard\" ReqDeliveryDate=\"2016-06-29T13:51:34.325Z\" DeliveryMethod=\"SHP\" SCAC=\"FEDX\" GiftWrap=\"N\" GiftFlag=\"N\" Createuserid=\"7001C1233334,7002C2743784\" FulfillmentType=\"FT_DCS\">")
        orderLines.append("<Item ItemID=\"" + item + "\" UnitOfMeasure=\"EACH\"/>")
        orderLines.append("<LinePriceInfo UnitPrice=\"1\" ListPrice=\"\" RetailPrice=\"1\" IsLinePriceForInformationOnly=\"N\" PricingQuantityStrategy=\"FIX\" IsPriceLocked=\"Y\"/>")
        orderLines.append("<LineCharges><LineCharge ChargePerLine=\"0.00\" ChargeCategory=\"DISCOUNT\" ChargeName=\"DISCOUNT\" Reference=\"\"/><LineCharge ChargePerLine=\"0.00\" ChargeCategory=\"SHIPPINGCHARGE\" ChargeName=\"SHIPPINGCHARGE\" Reference=\"\"/></LineCharges>")
        orderLines.append("<LineTaxes><LineTax ChargeCategory=\"Price\" ChargeName=\"\" Tax=\"0.00\" TaxName=\"General Sales and Use Tax\" TaxPercentage=\"0.00\"/><LineTax ChargeCategory=\"SHIPPINGCHARGE\" ChargeName=\"\" Tax=\"0.00\" TaxName=\"General Sales and Use Tax\" TaxPercentage=\"0.00\"/></LineTaxes>")
        orderLines.append("<Extn ExtnPackSlpPrcSuppress=\"N\" ExtnRegistrantAddrsIndictr=\"\" ExtnRegistrantStore=\"\" ExtnIsReturnable=\"N\" ExtnShipZone=\"\" ExtnWebLineNumber=\"3038052\" ExtnSignatureRequired=\"N\"/>")
        orderLines.append("<OrderLineReservations><OrderLineReservation ItemID=\"" + item + "\" Node=\"\" ProductClass=\"\" RequestedReservationDate=\"\" Quantity=\"1\" ReservationID=\"" + OrderNumber + "\" UnitOfMeasure=\"EACH\"/></OrderLineReservations>")
        orderLines.append("<Instructions><Instruction InstructionType=\"WRAP_TYPE\" InstructionText=\"B\"/><Instruction InstructionType=\"GIFT_MSG1\" InstructionText=\"GiftMessage1\"/></Instructions></OrderLine>")
        LineId = LineId + 1
    }
    orderLines.append("</OrderLines>")
    return orderLines.toString
  }

  def getMixOrderLines(Items: List[String], OrderNumber: String): String = {
    val orderLines = new StringBuilder
    var LineId = 1
    orderLines.append("<OrderLines>")
    Items.foreach {
      item =>
        if (LineId%Items.length == 3) {
          orderLines.append("<OrderLine PrimeLineNo=\"" + LineId + "\" SubLineNo=\"1\" OrderedQty=\"1\" CarrierServiceCode=\"Standard\" ReqDeliveryDate=\"2016-06-29T13:51:34.325Z\" DeliveryMethod=\"SHP\" SCAC=\"FEDX\" GiftWrap=\"N\" GiftFlag=\"N\" Createuserid=\"7001C1233334,7002C2743784\" FulfillmentType=\"FT_DCS\" LineType=\"PREORDER\">")
        }
        else {
          orderLines.append("<OrderLine PrimeLineNo=\"" + LineId + "\" SubLineNo=\"1\" OrderedQty=\"1\" CarrierServiceCode=\"Standard\" ReqDeliveryDate=\"2016-06-29T13:51:34.325Z\" DeliveryMethod=\"SHP\" SCAC=\"FEDX\" GiftWrap=\"N\" GiftFlag=\"N\" Createuserid=\"7001C1233334,7002C2743784\" FulfillmentType=\"FT_DCS\">")
        }
        orderLines.append("<Item ItemID=\"" + item + "\" UnitOfMeasure=\"EACH\"/>")
        orderLines.append("<LinePriceInfo UnitPrice=\"1\" ListPrice=\"\" RetailPrice=\"1\" IsLinePriceForInformationOnly=\"N\" PricingQuantityStrategy=\"FIX\" IsPriceLocked=\"Y\"/>")
        orderLines.append("<LineCharges><LineCharge ChargePerLine=\"0.00\" ChargeCategory=\"DISCOUNT\" ChargeName=\"DISCOUNT\" Reference=\"\"/><LineCharge ChargePerLine=\"0.00\" ChargeCategory=\"SHIPPINGCHARGE\" ChargeName=\"SHIPPINGCHARGE\" Reference=\"\"/></LineCharges>")
        orderLines.append("<LineTaxes><LineTax ChargeCategory=\"Price\" ChargeName=\"\" Tax=\"0.00\" TaxName=\"General Sales and Use Tax\" TaxPercentage=\"0.00\"/><LineTax ChargeCategory=\"SHIPPINGCHARGE\" ChargeName=\"\" Tax=\"0.00\" TaxName=\"General Sales and Use Tax\" TaxPercentage=\"0.00\"/></LineTaxes>")
        orderLines.append("<Extn ExtnPackSlpPrcSuppress=\"N\" ExtnRegistrantAddrsIndictr=\"\" ExtnRegistrantStore=\"\" ExtnIsReturnable=\"N\" ExtnShipZone=\"\" ExtnWebLineNumber=\"3038052\" ExtnSignatureRequired=\"N\"/>")
        orderLines.append("<OrderLineReservations><OrderLineReservation ItemID=\"" + item + "\" Node=\"\" ProductClass=\"\" RequestedReservationDate=\"\" Quantity=\"1\" ReservationID=\"" + OrderNumber + "\" UnitOfMeasure=\"EACH\"/></OrderLineReservations>")
        orderLines.append("<Instructions><Instruction InstructionType=\"WRAP_TYPE\" InstructionText=\"B\"/><Instruction InstructionType=\"GIFT_MSG1\" InstructionText=\"GiftMessage1\"/></Instructions></OrderLine>")
        LineId = LineId + 1
    }
    orderLines.append("</OrderLines>")
    return orderLines.toString
  }


  def getPreOrderLines(Items: List[String], OrderNumber: String): String = {
    val orderLines = new StringBuilder
    var LineId = 1
    orderLines.append("<OrderLines>")
    Items.foreach {
      item =>
        orderLines.append("<OrderLine PrimeLineNo=\"" + LineId + "\" SubLineNo=\"1\" OrderedQty=\"1\" CarrierServiceCode=\"Standard\" ReqDeliveryDate=\"2016-06-29T13:51:34.325Z\" DeliveryMethod=\"SHP\" SCAC=\"FEDX\" GiftWrap=\"N\" GiftFlag=\"N\" Createuserid=\"7001C1233334,7002C2743784\" FulfillmentType=\"FT_DCS\" LineType=\"PREORDER\">")
        orderLines.append("<Item ItemID=\"" + item + "\" UnitOfMeasure=\"EACH\"/>")
        orderLines.append("<LinePriceInfo UnitPrice=\"1\" ListPrice=\"\" RetailPrice=\"1\" IsLinePriceForInformationOnly=\"N\" PricingQuantityStrategy=\"FIX\" IsPriceLocked=\"Y\"/>")
        orderLines.append("<LineCharges><LineCharge ChargePerLine=\"0.00\" ChargeCategory=\"DISCOUNT\" ChargeName=\"DISCOUNT\" Reference=\"\"/><LineCharge ChargePerLine=\"0.00\" ChargeCategory=\"SHIPPINGCHARGE\" ChargeName=\"SHIPPINGCHARGE\" Reference=\"\"/></LineCharges>")
        orderLines.append("<LineTaxes><LineTax ChargeCategory=\"Price\" ChargeName=\"\" Tax=\"0.00\" TaxName=\"General Sales and Use Tax\" TaxPercentage=\"0.00\"/><LineTax ChargeCategory=\"SHIPPINGCHARGE\" ChargeName=\"\" Tax=\"0.00\" TaxName=\"General Sales and Use Tax\" TaxPercentage=\"0.00\"/></LineTaxes>")
        orderLines.append("<Extn ExtnPackSlpPrcSuppress=\"N\" ExtnRegistrantAddrsIndictr=\"\" ExtnRegistrantStore=\"\" ExtnIsReturnable=\"N\" ExtnShipZone=\"\" ExtnWebLineNumber=\"3038052\" ExtnSignatureRequired=\"N\"/>")
        orderLines.append("<OrderLineReservations><OrderLineReservation ItemID=\"" + item + "\" Node=\"\" ProductClass=\"\" RequestedReservationDate=\"\" Quantity=\"1\" ReservationID=\"" + OrderNumber + "\" UnitOfMeasure=\"EACH\"/></OrderLineReservations>")
        orderLines.append("<Instructions><Instruction InstructionType=\"WRAP_TYPE\" InstructionText=\"B\"/><Instruction InstructionType=\"GIFT_MSG1\" InstructionText=\"GiftMessage1\"/></Instructions></OrderLine>")
        LineId = LineId + 1
    }
    orderLines.append("</OrderLines>")
    return orderLines.toString
  }

  def getPaymentMethod(itemsNum: String): String = {
    val payment = new StringBuilder
    payment.append("<PaymentMethods>")
    payment.append("<PaymentMethod PaymentType=\"CREDIT_CARD_SAKS\" CreditCardType=\"VISA\" MaxChargeLimit=\"10000\" PaymentReference1=\"4779\" DisplayCreditCardNo=\"0005\" PaymentReference3=\"\" CreditCardNo=\"4055010000000005\" SvcNo=\"\" FirstName=\"GATLING\" LastName=\"PT\" CreditCardExpDate=\"06/2018\" UnlimitedCharges=\"N\" CreditCardName=\"Gatling PT\"><PaymentDetails ChargeType=\"CHARGE\" AuthorizationExpirationDate=\"2500-01-01T00:00:00-04:00\" AuthorizationID=\"51958B\" ProcessedAmount=\"" + itemsNum + ".00\" RequestAmount=\"" + itemsNum + ".00\" AuthAvs=\"111\" AuthReturnMessage=\"Approved\" CVVAuthCode=\"M\" AuthReturnCode=\"APPROVED\"/></PaymentMethod>")
    payment.append("</PaymentMethods>")
    return payment.toString
  }

  def getOrderXml(OrderNumber: String, EnteredBy: String, Org: String, EntryType: String, OrderLines: String, PaymentMethods: String, zipCode: String, address1: String, city: String, state: String, country: String): String = {
    val now = Calendar.getInstance().getTime()
    val minuteFormat = new SimpleDateFormat("yyyy-MM-dd")
    val currentDate = minuteFormat.format(now)

    val orderXml = new StringBuilder
    orderXml.append("<Order xmlns=\"http://www.sterling.com/document/YFS/createOrder/input\" OrderNo=\"" + OrderNumber + "\" DocumentType=\"0001\" EnteredBy=\"" + EnteredBy + "\" EnterpriseCode=\"" + Org + "\" SellerOrganizationCode=\"" + Org + "\" EntryType=\"" + EntryType + "\" OrderDate=\"" + currentDate + "\" OrderType=\"\" BillToID=\"GATLING@test.com\" CustomerContactID=\"GATLING@test.com\" AllocationRuleID=\"" + Org + "_SCH\" AuthorizedClient=\"" + Org + "\">")
    orderXml.append(OrderLines)
    orderXml.append("<PersonInfoBillTo Title=\"\" FirstName=\"GATLING\" MidleName=\"\" LastName=\"PT\" PersonID=\"\" AddressLine1=\"" + address1 + "\" AddressLine2=\" \" AddressLine3=\"\" AddressID=\"\" City=\"" + city + "\" State=\"" + state + "\" Country=\"" + country + "\" ZipCode=\"" + zipCode + "\" EMailID=\"GATLING@test.com\" DayPhone=\"4791112711\"/>")
    orderXml.append("<PersonInfoShipTo Title=\"\" FirstName=\"GATLING\" MidleName=\"\" LastName=\"PT\" PersonID=\"\" AddressLine1=\"" + address1 + "\" AddressLine2=\" \" AddressLine3=\"\" AddressID=\"\" City=\"" + city + "\" State=\"" + state + "\" Country=\"" + country + "\" ZipCode=\"" + zipCode + "\" EMailID=\"GATLING@test.com\" DayPhone=\"4791112711\"/>")
    orderXml.append(PaymentMethods)
    orderXml.append("<HeaderCharges><HeaderCharge ChargePerLine=\"35.00\" ChargeCategory=\"YOUSAVED\" ChargeName=\"YOUSAVED\" Reference=\"\"/></HeaderCharges>")
    orderXml.append("<Promotions><Promotion PromotionId=\"SUMMER\"/><Promotion PromotionId=\"SHIP75\"/></Promotions>")
    orderXml.append("<Extn ExtnLoyaltyNo=\"6000086241N\" ExtnCommLang=\"en_US\" ExtnShopRnrAuthTok=\"\" ExtnCustomerId=\"12048877\" ExtnRegistryID=\"\" ExtnRegistrantName=\"\" ExtnIsFiftyOne=\"N\" ExtnFiftyOneOrderNo=\"\" ExtnCurrencyCode=\"\" ExtnCurrencyExchangeRate=\"\" ExtnReference1=\"PO.SCOM.NEW.ORDER\" ExtnAuthorizationDateLine=\"2015-05-26,23.45.43,789,9234,5988\"/>")
    orderXml.append("</Order>")
    return orderXml.toString
  }


  def getSalesTickleXml(Org: String, SupplyType: String, ShipNode: String, Quantity: String, item: String): String = {
    val salesTickleXml = new StringBuilder
    salesTickleXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
    salesTickleXml.append("<Items>")
    salesTickleXml.append("<Item OrganizationCode=\"" + Org + "\" UnitOfMeasure=\"EACH\" UnitCost=\"\" SupplyType=\"" + SupplyType + "\" ShipNode=\"" + ShipNode + "\" Quantity=\"" + Quantity + "\"  ItemID=\"" + item + "\" AdjustmentType=\"ADJUSTMENT\" AccountNo=\"\" Reference_1=\"15672436\">")
    salesTickleXml.append("</Item></Items>")

    return salesTickleXml.toString
  }


  def getPOcreateXml(Org: String, OrderNumber: String, ShipNode: String, Quantity: String, item: String, zipCode: String, address1: String, city: String, state: String, country: String): String = {
    val now = Calendar.getInstance().getTime()
    val minuteFormat = new SimpleDateFormat("yyyy-MM-dd")
    val currentDate = minuteFormat.format(now)

    val POcreateXml = new StringBuilder
    POcreateXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
    POcreateXml.append("<Order Action=\"CREATE\" BuyerOrganizationCode=\"" + Org + "\" DocumentType=\"0005\" EnterpriseCode=\"" + Org + "\" OrderNo=\"" + OrderNumber + "\" SellerOrganizationCode=\"" + Org + "\" OrderDate=\"" + currentDate + "\">")
    POcreateXml.append("<OrderLines>")
    POcreateXml.append("<OrderLine Action=\"CREATE\" OrderedQty=\"" + Quantity + "\" ReceivingNode=\"" + ShipNode + "\" ReqDeliveryDate=\"" + currentDate + "\" OriginalOrderedQty=\"" + Quantity + "\">")
    POcreateXml.append("<Item ItemID=\"" + item + "\" UnitOfMeasure=\"EACH\" />")
    POcreateXml.append("</OrderLine>")
    POcreateXml.append("</OrderLines>")
    POcreateXml.append("<PersonInfoShipTo AddressLine1=\"" + address1 + "\" AddressLine2=\"\" City=\"" + city + "\" Country=\"" + country + "\" DayPhone=\"9999999999\" EMailID=\"\" FirstName=\"\" LastName=\"\" MiddleName=\"\" State=\"" + state + "\" ZipCode=\"" + zipCode + "\" />")
    POcreateXml.append("<PersonInfoBillTo AddressLine1=\"" + address1 + "\" AddressLine2=\"\" City=\"" + city + "\" Country=\"" + country + "\" DayPhone=\"9999999999\" EMailID=\"\" FirstName=\"\" LastName=\"\" MiddleName=\"\" State=\"" + state + "\" ZipCode=\"" + zipCode + "\" />")
    POcreateXml.append("</Order>")


    return POcreateXml.toString
  }

  def getPOreceiveXml(Org: String, OrderNumber: String, ShipNode: String, Quantity: String, item: String, zipCode: String, address1: String, city: String, state: String, country: String): String = {
    val now = Calendar.getInstance().getTime()
    val tomm = Calendar.getInstance()

    val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
    val currentDate = dateFormat.format(now)
    tomm.setTime(dateFormat.parse(currentDate))
    tomm.add(Calendar.DATE, 1)
    val tommDate = dateFormat.format(tomm.getTime)

    val POcreateXml = new StringBuilder
    POcreateXml.append("<Order Action=\"MODIFY\" BuyerOrganizationCode=\"" + Org + "\" DocumentType=\"0005\" EnterpriseCode=\"" + Org + "\" OrderNo=\"" + OrderNumber + "\" SellerOrganizationCode=\"" + Org + "\" OrderDate=\"" + currentDate + "\">")
    POcreateXml.append("<OrderLines>")
    POcreateXml.append("<OrderLine Action=\"RECEIPT\" OrderedQty=\"" + Quantity + "\" ShipNode=\"\" ReceivingNode=\"" + ShipNode + "\" ReqDeliveryDate=\"" + tommDate + "\" OriginalOrderedQty=\"" + Quantity + "\" ReceivedQty=\"" + Quantity + "\" TotalReceivedQty=\"" + Quantity + "\" ReceiptDate=\"" + currentDate + "\">")
    POcreateXml.append("<Item ItemID=\"" + item + "\" UnitOfMeasure=\"EACH\" />")
    POcreateXml.append("</OrderLine>")
    POcreateXml.append("</OrderLines>")
    POcreateXml.append("<PersonInfoShipTo AddressLine1=\"" + address1 + "\" AddressLine2=\"\" City=\"" + city + "\" Country=\"" + country + "\" DayPhone=\"9999999999\" EMailID=\"\" FirstName=\"\" LastName=\"\" MiddleName=\"\" State=\"" + state + "\" ZipCode=\"" + zipCode + "\" />")
    POcreateXml.append("<PersonInfoBillTo AddressLine1=\"" + address1 + "\" AddressLine2=\"\" City=\"" + city + "\" Country=\"" + country + "\" DayPhone=\"9999999999\" EMailID=\"\" FirstName=\"\" LastName=\"\" MiddleName=\"\" State=\"" + state + "\" ZipCode=\"" + zipCode + "\" />")
    POcreateXml.append("</Order>")


    return POcreateXml.toString
  }


  def getReserveXml(Instance: String, arg0: String, arg1: String): String = {
    val buf = new StringBuilder
    buf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
    buf.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" ")
    buf.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ")
    buf.append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" ")
    buf.append("xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/03/addressing\">")
    buf.append("<soap:Header>")
    buf.append("<wsa:Action></wsa:Action>")
    buf.append("<wsa:MessageID>uuid:d68f2638-a6ba-4fa0-a02e-fcd353048db6</wsa:MessageID>")
    buf.append("<wsa:ReplyTo><wsa:Address>http://schemas.xmlsoap.org/ws/2004/03/addressing/role/anonymous</wsa:Address></wsa:ReplyTo>")
    buf.append("<wsa:To>" + Instance + "/smcfsejb/YIFWebService/YIFWebService</wsa:To>")
    buf.append("</soap:Header>")
    //  buf.append(xml.toString)
    buf.append("<soap:Body>")
    buf.append("<hbcReserveInventory xmlns=\"http://ejb.rpc.webservices.services.interop.yantra.com/\">")
    buf.append("<arg0 xmlns=\"\">")
    buf.append(arg0)
    buf.append("</arg0>")
    buf.append("<arg1 xmlns=\"\">")
    buf.append(arg1)
    buf.append("</arg1>")
    buf.append("</hbcReserveInventory>")
    buf.append("</soap:Body>")
    buf.append("</soap:Envelope>")
    return buf.toString
  }


  def getInvLookupArg1(Items: List[String], EntryType: String, Org: String, zipCode: String, state: String, FulfillmentType: String): String = {
    val arg1 = new StringBuilder
    var LineId = 1
    arg1.append("&lt;Promise EntryType=\"" + EntryType + "\" OrganizationCode=\"" + Org + "\" FulfillmentType=\"" + FulfillmentType + "\" EnterpriseCode=\"" + Org + "\" DistanceUOMToConsider=\"MILE\" DistanceToConsider=\"5000\"&gt;")
    arg1.append("&lt;PromiseLines&gt;")
    Items.foreach {
      item =>
        arg1.append("&lt;PromiseLine FulfillmentType=\"\" UnitOfMeasure=\"EACH\" RequiredQty=\"5\" LineId=\"" + LineId + "\" ItemID=\"" + item + "\"&gt;&lt;ShipToAddress ZipCode=\"" + zipCode + "\" State=\"" + state + "\" Country=\"US\"/&gt;&lt;/PromiseLine&gt;")
        LineId = LineId + 1
    }
    arg1.append("&lt;/PromiseLines&gt;&lt;/Promise&gt;")
    return arg1.toString
  }

  def getInvLookupXml(Instance: String, arg0: String, arg1: String): String = {
    val buf = new StringBuilder
    buf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
    buf.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" ")
    buf.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ")
    buf.append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" ")
    buf.append("xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/03/addressing\">")
    buf.append("<soap:Header>")
    buf.append("<wsa:Action></wsa:Action>")
    buf.append("<wsa:MessageID>uuid:d68f2638-a6ba-4fa0-a02e-fcd353048db6</wsa:MessageID>")
    buf.append("<wsa:ReplyTo><wsa:Address>http://schemas.xmlsoap.org/ws/2004/03/addressing/role/anonymous</wsa:Address></wsa:ReplyTo>")
    buf.append("<wsa:To>" + Instance + "/smcfsejb/YIFWebService/YIFWebService</wsa:To>")
    buf.append("</soap:Header>")
    //  buf.append(xml.toString)
    buf.append("<soap:Body>")
    buf.append("<hbcInventoryLookUp xmlns=\"http://ejb.rpc.webservices.services.interop.yantra.com/\">")
    buf.append("<arg0 xmlns=\"\">")
    buf.append(arg0)
    buf.append("</arg0>")
    buf.append("<arg1 xmlns=\"\">")
    buf.append(arg1)
    buf.append("</arg1>")
    buf.append("</hbcInventoryLookUp>")
    buf.append("</soap:Body>")
    buf.append("</soap:Envelope>")
    return buf.toString
  }

  def getATPArg1(Items: List[String], Org: String): String = {
    val arg1 = new StringBuilder

    arg1.append("&lt;MonitorItemAvailability DistributionRuleId=\"\" OrganizationCode=\"" + Org + "\"&gt;&lt;Items&gt;")
    Items.foreach {
      item =>
        arg1.append("&lt;Item ItemID=\"" + item + "\" ProductClass=\"\" UnitOfMeasure=\"EACH\"/&gt;")

    }
    arg1.append("&lt;/Items&gt;&lt;/MonitorItemAvailability&gt;")
    return arg1.toString
  }

  def getATPXml(Instance: String, arg0: String, arg1: String): String = {
    val buf = new StringBuilder
    buf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
    buf.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" ")
    buf.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ")
    buf.append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" ")
    buf.append("xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/03/addressing\">")
    buf.append("<soap:Header>")
    buf.append("<wsa:Action></wsa:Action>")
    buf.append("<wsa:MessageID>uuid:d68f2638-a6ba-4fa0-a02e-fcd353048db6</wsa:MessageID>")
    buf.append("<wsa:ReplyTo><wsa:Address>http://schemas.xmlsoap.org/ws/2004/03/addressing/role/anonymous</wsa:Address></wsa:ReplyTo>")
    buf.append("<wsa:To>" + Instance + "/smcfsejb/YIFWebService/YIFWebService</wsa:To>")
    buf.append("</soap:Header>")
    //  buf.append(xml.toString)
    buf.append("<soap:Body>")
    buf.append("<hbcMonitorItemAvailability xmlns=\"http://ejb.rpc.webservices.services.interop.yantra.com/\">")
    buf.append("<arg0 xmlns=\"\">")
    buf.append(arg0)
    buf.append("</arg0>")
    buf.append("<arg1 xmlns=\"\">")
    buf.append(arg1)
    buf.append("</arg1>")
    buf.append("</hbcMonitorItemAvailability>")
    buf.append("</soap:Body>")
    buf.append("</soap:Envelope>")
    return buf.toString
  }


  def getItems(numItems: Int, location: String): List[String] = {
    var items = new ListBuffer[String]()
    if (location.contains(",")) {
      val locations: Array[String] = location.split(",")
      var a = 0;
      // for loop execution with a range
      for (a <- 1 to numItems / locations.length) {
        var b = 0;
        for (b <- 0 until locations.length) {
          items += readRedis(locations.get(b))
        }
      }
    }
    else {
      var a = 0;
      // for loop execution with a range
      for (a <- 1 to numItems) {
        items += readRedis(location)
      }
    }
    return items.toList

  }

  def getItems(numItems: Int, location1: String, location2: String): List[String] = {
    var items = new ListBuffer[String]()

    var a = 0;
    // for loop execution with a range
    for (a <- 1 to numItems / 2) {
      items += readRedis(location1)
      items += readRedis(location2)
    }
    return items.toList

  }


  def show(x: Option[String]) = x match {
    case Some(s) => s
    case None => "?"
  }


  def inputRedis(ListName: String, ListValue: String) = {

    r.rpush(ListName, ListValue)


  }

  def inputUnique(ListName: String, ListValue: String) = {
try {
  r.sadd(ListName, ListValue)

}
catch {

  case e: Exception =>
    println(e)
 //  System.exit(0)
}
  }

  def renameRedis(ListName: String, ListValue: String) = {
    r.del(ListValue)
    r.rename(ListName, ListValue)


  }

  def lengthRedis(ListName: String): Int = {
    println("In : " + r.llen(ListName).toString)
    return r.llen(ListName).toString.dropRight(1).drop(5).toInt


  }


  def readRedisLPOP(ListName: String): String = {
    return show(r.lpop(ListName))

  }

  def readRedisSPOP(ListName: String): String = {

    return show(r.spop(ListName))

  }


  def readRedis(ListName: String): String = {

    val item = show(r.lpop(ListName))
      println(ListName + " : " + item)

    inputRedis(ListName, item)
    return item

  }


  /* def getPromiseLine(LineItems:Int, FulfillmentType:String) :String = {

       val promise = "&lt;PromiseLines&gt;" + "&lt;PromiseLine FulfillmentType=\"SHIP\" ItemID=\"${Item1}\" LineId=\"1\" RequiredQty=\"1\" UnitOfMeasure=\"EACH\"&gt;&lt;ShipToAddress Country=\"US\" State=\"NY\" ZipCode=\"91764\"/&gt;&lt;/PromiseLine&gt;&lt;PromiseLine FulfillmentType=\"SHIP\" ItemID=\"${Item1}\" LineId=\"2\" RequiredQty=\"1\" UnitOfMeasure=\"EACH\"&gt;&lt;ShipToAddress Country=\"US\" State=\"NY\" ZipCode=\"91764\"/&gt;&lt;/PromiseLine&gt;&lt;/PromiseLines&gt;"

   }
 */


  def generateOrderNumber(prefix: String): String = {


    val OrderNumber = show(r.lpop("LatestOrder"))

    // val OrderNumber = redisPool(redisPool, "LatestOrder", RedisFeeder.LPOP).toString

    val NewOrderNumber = OrderNumber.toInt + 1

    inputRedis("LatestOrder", NewOrderNumber.toString)

    return prefix + NewOrderNumber.toString

  }


  def insertGroupResponseTime(session: String, transaction: String) = {

    val pattern = "GroupBlock\\(List\\(\\S+\\),\\d+,(\\d+)".r


    val diff: String = (pattern findAllIn session).mkString(",").split(",").last

    var json: String = transaction + " value=" + diff

    val result = Http("http://hd1qjra03lx.digital.hbc.com:8086/write")
      .param("db", "gatling")
      .postData(json)
      //.header("Content-Type", "application/json")
      .header("Charset", "UTF-8").asString


  }


  val Instances = jsonFile("src/test/resources/OMSDataSetUp/S5APInstances.json").random
  val credentials = jsonFile("src/test/resources/OMSDataSetUp/credentials.json").circular
  val WebCOMcredentials = jsonFile("src/test/resources/OMSDataSetUp/WebCOMcredentials.json").circular
  val address = jsonFile("src/test/resources/OMSDataSetUp/address.json").random
  val addressInt = jsonFile("src/test/resources/OMSDataSetUp/addressInt.json").random

  def salesTickleDC() = {


    val item = readRedis("ItemsDC")
    // val item = "72109771"
    val messageText = getSalesTickleXml("SAKS", "ONHAND", "DC-LVG-689", "10", item)

    sendMessage(messageText, "MCC.INVADJ.DC.MW.OMS.INPUT.PRD")
  }

  def salesTickleStores() = {
    val item = readRedis("ItemsDC")
    // val item = "72109771"
    val messageText = getSalesTickleXml("SAKS", "ONHAND", "DC-LVG-689", "10", item)

    sendMessage(messageText, "MCC.INVADJ.DC.MW.OMS.INPUT.PRD")
  }

}
