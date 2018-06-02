

import java.io.{FileReader, BufferedReader, PrintWriter, File}
import org.json4s.JsonAST.{JString, JField, JInt, JObject}
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._

import com.redis._

object Main extends App {


  val r = new RedisClient("hd1qjra03lx.digital.hbc.com",6379)

 // val br = new BufferedReader(new FileReader("src/test/resources/OMSDataSetUp/Items.txt"))


  var line:String = ""

  /*for (b <- 0 until 34) {
    r.del(readRedis("Stores"))
  }*/

/*
  val jsonString: String = "{\"ProductDetails\":{\"main_products\":[{\"add_to_bag_submit_btn\":{\"enabled\":true,\"label\":\"ADD TO BAG\",\"action\":{\"endpoint\":\"add_saks_suggests_item_service_product_array\",\"input_value\":\"checkout\"},\"failure_message\":\"Please select a color and/or size.\"},\"edit_bag_submit_btn\":{\"enabled\":true,\"label\":\"APPLY\",\"action\":{\"endpoint\":\"edit_cart_item_service\",\"input_value\":\"\"}},\"intl_shipping_restriction\":{\"enabled\":false,\"label\":\"Please note\",\"link\":{\"label\":\"This item cannot be shipped to United States\",\"url\":\"http://qa.saksdirect.com/html/contextual_popups/StateShippingRestriction.jsp\"},\"message\":\"Sorry this item cannot be shipped internationally.\"},\"brand_name\":{\"enabled\":true,\"label\":\"Valentino by Mario Valentino\",\"url\":\"http://qa-so5.digital.hbc.com/search/EndecaSearch.jsp?bmText=SearchString&N_Dim=0&Ntk=Entire+Site&Ntt=Valentino+by+Mario+Valentino\"},\"call_to_order_message\":{\"enabled\":false,\"label\":\"\",\"message\":\"\"},\"order_from_full_site_message\":{\"enabled\":false,\"message\":\"To order this item, please go to our main site or call 1.866.601.5105.\"},\"colors\":{\"enabled\":true,\"show_swatches\":false,\"colors\":[{\"id\":0,\"label\":\"Cognac\",\"value\":\"\",\"is_value_an_image\":false,\"colorize_image_url\":\"0400090746736\",\"is_soldout\":false,\"is_waitlistable\":false,\"counter\":1}]},\"price\":{\"enabled\":true,\"alternate_copy\":\"\",\"list_price_label\":\"\",\"list_price\":{\"local_currency_code\":\"USD\",\"local_currency_symbol\":\"&#36;\",\"local_currency_value\":\"1095.00\",\"usd_currency_value\":\"1095.00\"},\"sale_price_label\":\"\",\"sale_price\":{\"local_currency_code\":\"USD\",\"local_currency_symbol\":\"&#36;\",\"local_currency_value\":\"399.99\",\"usd_currency_value\":\"399.99\"},\"on_sale\":true,\"percent_off\":\"63%\",\"show_msrp_legal_copy\":true},\"description\":\"<ul><li>Chic shoulder bag boasts of supple leather construction</li><li>Adjustable removable shoulder strap</li><li>Flap magnetic snap closure</li><li>Rose goldtone hardware</li><li>Authenticity card and dust bag included</li><li>Fully lined</li><li>One zip pocket</li><li>One open interior pocket</li><li>Leather</li><li>Imported</li></ul>\",\"fit_model_message\":{\"enabled\":false,\"message\":\"\"},\"fit_predictor\":{\"enabled\":true,\"ssp_key\":\"saksoff5th\",\"ssp_version\":\"v1\"},\"high_demand_message\":{\"enabled\":true,\"message\":\"DUE TO HIGH DEMAND, A CUSTOMER MAY ORDER NO MORE THAN 99 UNITS OF THIS ITEM EVERY THIRTY DAYS.\",\"purchase_limit_threshold\":99},\"holiday_cutoff_message\":{\"enabled\":false,\"holiday_name\":\"\",\"holiday_message\":\"\"},\"product_detail_tabs\":{\"enabled\":true,\"details_tab\":{\"enabled\":true,\"label\":\"Details\"},\"shipping_messages_tab\":{\"enabled\":true,\"label\":\"Shipping & Returns\"}},\"media\":{\"images_server_url\":\"//image.s5a.com/\",\"images_path\":\"is/image/saksoff5th/\",\"asset_prefix\":\"/saksoff5th\",\"images\":[\"0400090746736\",\"0400090746736_A1\",\"0400090746736_A2\",\"0400090746736_A3\"],\"video_player\":{\"enabled\":true,\"video\":{\"small_src\":\"\",\"medium_src\":\"\",\"large_src\":\"//image.s5a.com/is/content/saksoff5th/0400090746736_V486x648\"},\"auto_play\":true,\"has_video\":\"0\"},\"zoom_player\":{\"enabled\":true,\"flash_links\":{\"server_url\":\"//image.s5a.com/is/image&asset=saksoff5th/0400090746736_IS&showVideo=0&hideThumbs=0&iscommand=op_usm%3D2.2%2C1%2C6%2C0\",\"player_url\":\"//image.s5a.com/is/content/saksoff5th/productViewer\"},\"html_links\":{\"server_url\":\"//s7d9.scene7.com/is/image\",\"player_url\":\"//s7d9.scene7.com/s7sdk/2.2/js/s7sdk/utils/Utils.js\"},\"image_set\":\"saksoff5th/0400090746736_IS\",\"main_image\":\"saksoff5th/0400090746736\"}},\"product_code\":\"0400090746736\",\"product_detail_page_link\":{\"enabled\":false,\"label\":\"View Product Detail\",\"url\":\"http://prev-so5.digital.hbc.com/main/ProductDetail.jsp?PRODUCT<>prd_id=845524442112450\"},\"social_media\":{\"enabled\":true,\"fb_like_button\":{\"enabled\":true,\"title\":\"Facebook\",\"link\":\"http://qa-so5.digital.hbc.com/main/ProductDetail.jsp?PRODUCT%3C%3Eprd_id=845524442112450\"},\"pinterest_button\":{\"enabled\":true,\"title\":\"Pinterest\",\"link\":\"http://pinterest.com/pin/create/button/?url=http%3A%2F%2Fqa-so5.digital.hbc.com%2Fmain%2FProductDetail%2Ejsp%3FPRODUCT%253C%253Eprd_id%3D845524442112450&media=http%3A%2F%2Fimage.s5a.com%2Fis%2Fimage%2Fsaksoff5th%2F0400090746736_300x400.jpg&description=Valentino+by+Mario+Valentino%20Leather+Shoulder+Bag\"},\"twitter_button\":{\"enabled\":true,\"title\":\"Twitter\",\"link\":\"https://twitter.com/share?url=http%3A%2F%2Fqa-so5.digital.hbc.com%2Fmain%2FProductDetail%2Ejsp%3FPRODUCT%253C%253Eprd_id%3D845524442112450&text=Valentino+by+Mario+Valentino%20%2D%20Leather+Shoulder+Bag%20%7C%20Saks%20Fifth%20Avenue\"},\"google_plus_button\":{\"enabled\":true,\"title\":\"G+\",\"link\":\"https://plus.google.com/share?url=http%3A%2F%2Fqa-so5.digital.hbc.com%2Fmain%2FProductDetail%2Ejsp%3FPRODUCT%253C%253Eprd_id%3D845524442112450&text=Valentino+by+Mario+Valentino%20%2D%20Leather+Shoulder+Bag%20%7C%20Saks%20Fifth%20Avenue\"},\"tell_a_friend_button\":{\"enabled\":true,\"title\":\"Tell A Friend\",\"link\":\"/main/ComposeTAF.jsp?PRODUCT%3C%3Eprd_id=845524442112450\"}},\"fit_model_product_copy\":{\"enabled\":false,\"message\":\"\",\"link\":{\"label\":\"Details\",\"url\":\"http://qa-so5.digital.hbc.com?PRODUCT%3C%3Eprd_id=845524442112450\"}},\"personalization\":{\"enabled\":false,\"pers_add_to_bag_submit_btn\":{\"label\":\"PERSONALIZE AND ADD TO BAG\",\"action\":{\"endpoint\":\"\",\"input_value\":\"checkoutPers\"}},\"label\":\"Personalize this item\"},\"is_specialized\":false,\"simple_shipping_statement\":{\"message\":\"Standard shipping is free for purchases over $99 with code SHIP99. SaksFirst customers receive Free Standard Shipping when purchasing with a SaksFirst MasterCard® or SaksFirst Store Card. During the sales event, items that cannot be returned are marked as Final Sale.\",\"link\":{\"label\":\"Details\",\"url\":\"http://qa-so5.digital.hbc.com/Shipping-Returns-Taxes\"}},\"additional_information\":{\"enabled\":false,\"label\":\"Additional Information\",\"cannot_be_returned_message\":{\"enabled\":false,\"message\":\"Cannot be returned\"},\"gift_wrap_not_allowed\":{\"enabled\":false,\"message\":\"Please Note: This item cannot be gift wrapped.\"},\"drop_ship_time_line\":{\"enabled\":false,\"label\":\"Shipping timeline for this item\",\"url\":\"http://qa-so5.digital.hbc.com?PRODUCT%3C%3Eprd_id=845524442112450\"},\"usps_ship_ok_message\":{\"enabled\":false,\"message\":\"Cannot be shipped to a P.O. Box, APO/FPO or U.S. Territory\"},\"restricted_ship_type\":{\"enabled\":false,\"message\":\"Cannot be shipped via  delivery\"},\"restricted_ship_to_state\":{\"enabled\":false,\"message\":\"Cannot be shipped to:\",\"state_name\":\"\",\"url\":\"http://qa-so5.digital.hbc.com/html/contextual_popups/StateShippingRestriction.jsp\"}},\"clarity_event_tags\":{\"product_name_event\":{\"event_id\":\"131\",\"value\":\"Leather Shoulder Bag\"},\"product_id_event\":{\"event_id\":\"1012\",\"value\":\"845524442112450\"},\"group_id_event\":{\"event_id\":\"1112\",\"value\":\"49\"}},\"gift_with_purchase_message\":{\"enabled\":false,\"message\":\"Complimentary\"},\"is_electronic_gift_card\":false,\"is_virtual\":false,\"gift_card\":{\"is_gift_card\":false,\"is_virtual_gift_card\":false,\"action\":{\"endpoint\":\"saks_add_to_cart\",\"input_value\":\"enter_recipient\"},\"label\":\"Denominations:\"},\"quantity_field\":{\"enabled\":true,\"label\":\"Quantity\",\"message\":\"\"},\"product_id\":\"845524442112450\",\"promo\":[],\"reviews\":{\"enabled\":false,\"product_code\":\"0400090746736\",\"formatted_rating_average\":0,\"rating_average\":0,\"total_reviews\":0,\"read_reviews\":{\"enabled\":false,\"label\":\"Read 0 Reviews\"},\"write_reviews\":{\"enabled\":false,\"label\":\"Write a Review\",\"url\":\"http://ny.saksoff5th.com/main/ProductDetail.jsp?productcode=0400090746736\"},\"site_key\":\"uruIN1GkkRwurAvsite\",\"tab_label\":\"Reviews\",\"anchor\":\"reviews\",\"panel_contents_id\":\"TurnToReviewsContent\",\"title\":\"Reviews\"},\"short_description\":\"Leather Shoulder Bag\",\"sizes\":{\"enabled\":false,\"show_sizes_as_boxes\":false,\"size_label\":\"Choose a Size\",\"sizes\":[]},\"denominations\":{\"enabled\":false,\"show_denominations_as_boxes\":false,\"denominations\":[]},\"all_preorder\":{\"enabled\":false,\"label\":\"\",\"message\":\"\"},\"skus\":{\"enabled\":true,\"skus\":[{\"upc\":\"841915104120\",\"color_id\":0,\"size_id\":-1,\"denomination_id\":-1,\"sku_id\":\"1689949373757980\",\"price\":{\"alternate_copy\":\"\",\"list_price_label\":\"\",\"list_price\":{\"local_currency_code\":\"USD\",\"local_currency_symbol\":\"&#36;\",\"local_currency_value\":\"1095.00\",\"usd_currency_value\":\"1095.00\"},\"sale_price_label\":\"\",\"sale_price\":{\"local_currency_code\":\"USD\",\"local_currency_symbol\":\"&#36;\",\"local_currency_value\":\"399.99\",\"usd_currency_value\":\"399.99\"},\"on_sale\":true,\"on_clearance\":false,\"percent_off\":\"63%\",\"show_msrp_legal_copy\":true},\"status_alias\":\"available\",\"status_label\":\"\",\"status_message\":\"\",\"no_returns\":{\"enabled\":false,\"message\":\"\"},\"skn\":\"90746735\",\"limited_inventory\":false}]},\"waitlist_submit_btn\":{\"enabled\":false,\"enabled_on_page\":false,\"label\":\"Join the Wait List!\",\"inline_waitlist_label\":\"Is your size/color sold out?\",\"thank_you_message\":\"This item has been added to your Wait List.\",\"email_placeholder\":\"Email Address\",\"description\":\"This item is sold out. Add to Wait List to be notified when it is back in stock.\",\"leading_message\":\"This combination is not available.\",\"email_validation_error_message\":\"Enter a valid email address.\",\"overlay_endpoint\":\"/v1/waitlist-overlay-service/product/{product_code}\",\"add_to_waitlist_endpoint\":\"/v1/waitlist-service/waitlist/store-and-retrieve-entry\",\"add_to_waitlist_endpoint_bmform\":\"waitlist_add_to_service\",\"failure_message\":\"Please select a color and/or size.\",\"email\":{\"label\":\"Email Address\",\"placeholder\":\"\",\"validation_message\":\"Please enter a valid email address.\"},\"phone\":{\"label\":\"Mobile Phone Number (optional)\",\"placeholder\":\"\",\"validation_message\":\"Please enter a valid phone number.\"},\"sign_up\":{\"label\":\"Sign up for emails.\",\"validation_message\":\"\"}},\"question_and_answer\":{\"ask_question\":{\"enabled\":false,\"label\":\"Ask a Question\"},\"read_question_answer\":{\"enabled\":false,\"label\":\"See All Q(0) & A(0)\"},\"enabled\":false,\"questions_count\":0,\"answers_count\":0,\"product_code\":\"0400090746736\",\"site_key\":\"BBhFQqvKrFEA7M4site\",\"prompt\":\"Q & A:\",\"tab_label\":\"Q & A\",\"anchor\":\"Questions and Answers\",\"panel_contents_id\":\"TurnToContent\",\"title\":\"Questions and Answers\"},\"sold_out_message\":{\"enabled\":false,\"message\":\"Sold Out\"},\"no_returns\":{\"enabled\":false,\"message\":\"\"},\"alternate_descriptions\":{\"enabled\":true,\"description_2\":\"\",\"description_3\":\"\",\"description_4\":\"\",\"description_5\":\"\"},\"size_guide_link\":{\"enabled\":false,\"label\":\"Size Guide\",\"url\":\"\"},\"hide_from_catalog\":{\"enabled\":true,\"is_hide_from_catalog\":false},\"is_main_product\":true,\"is_csr_only\":false,\"shop_runner\":{\"enabled\":false,\"url\":\"/v1/shoprunner/\",\"retailer_id\":\"SAKSOFF5TH\"},\"restricted_warning_message\":{\"enabled\":false,\"message\":\"Warning for shipment to California\",\"url\":\"/html/contextual_popups/LeadWarning.jsp\"},\"find_in_store\":{\"enabled\":true,\"url\":\"/v2/find-in-store-service/findStoresBySkn/{sknCode}/100/{zipCode}/N/Y\"}}],\"meta_objects\":[{\"name\":\"meta_description\",\"content\":\"Content goes here\"}],\"shop_the_look_products\":{\"label\":\"Complete the Look\",\"products\":[]},\"title\":\"\",\"messages\":{\"recently_viewed_title\":\"Recently Viewed\",\"find_in_store_title\":\"Find In Store\",\"find_in_store_zip_code\":\"Zip Code\",\"find_in_store_check_stores\":\"Check Stores\",\"find_in_store_service_not_available\":\"We cannot complete your request, please try again later\",\"html_waitlist_disclaimer\":\"By opting in to Saks Fifth Avenue Wait List Alerts, you agree to receive autodialed marketing text messages to the phone number provided at opt-in. Message frequency may vary based on customer preferences. Text <b>HELP</b> for info. Text <b>STOP</b> to cancel. Consent is not a condition of purchase. Message and data rates may apply. For more details, review our <a href=\\\"/Policies\\\" target=\\\"_blank\\\">Privacy Policy</a>.\",\"service_down_msg\":\"Apologies... Please try again shortly.\",\"zip_code_invalid_msg\":\"Enter a valid zip code\",\"limited_inventory_label\":\"Limited Inventory\",\"clearance_label\":\"Clearance\",\"percent_off_label\":\"Off\",\"msrp_legal_copy\":\"The strikethrough price reflects the regular price at which we've normally sold that item or, if we have not previosuly sold an item, the price at which that item (or a comparable item) is normally sold in the market\"},\"cookies\":[],\"response\":{\"statusCode\":200},\"__meta\":{\"env\":\"qa\",\"signal_site_key\":\"xxxDgJn\"}}}"

  val json = parse(jsonString)
  val list: List[String] = for {
    JObject(child) <- json
    JField("brand_name", JObject(name)) <- child
    JField("label", JString(label))  <- name
  } yield label

  val name = list(0)*/

//  println(r.sadd("Test", "Test1"))

/*  while (lengthRedis("ItemsPONew")>0) {

    r.rpush("ItemsPO",r.lpop("ItemsPONew"))

  }
 def inputUnique(ListName: String, ListValue: String) = {

    r.sadd(ListName, ListValue)


  }

  def lengthRedis(ListName: String): Int = {
    println("In : " + r.llen(ListName).toString)
    return r.llen(ListName).toString.dropRight(1).drop(5).toInt


  }*/
 // println(r.llen("ItemsDC").toString.dropRight(1).drop(5).toLong)

/*  while ({ line = br.readLine() ; line != null } ) {

      r.rpush("8621",line)

  }*/

 /* while ({ line = br.readLine() ; line != null } ) {
    if(line.contains(",")) {
      val Array(location1, location2, _*) = line.split(",")
      r.rpush(location1, location2)
    }

  }

*/

/*
  val Item1 = readRedis("ItemsDC")
  val Item2 = readRedis("ItemsDC")
*/

 //val test = r.lpop("LatestOrder")
 // println(show(test))
  def show(x: Option[String]) = x match {
    case Some(s) => s
    case None => "?"
  }


  def readRedis(ListName:String) :String = {

    val item = show(r.lpop(ListName))
    println(item)
    inputRedis(ListName,item)
    return item

  }


  def inputRedis(ListName:String, ListValue:String) = {

    r.rpush(ListName,ListValue)

  }





}