package LTChains.LTPageNavigation

import BaseTest.BaseTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by aindana on 3/14/2016.
  */
object LTPageNavigation extends BaseTest{
val url = baseUrlHttp + "/webapp/wcs/stores/servlet/AjaxCatalogSearchResultView"
  val LTPageNavigation = group("LTPageNavigation_Transaction") {
      exec(http("LTPageNavigation")
        .post(url)
        .queryParam("searchTermScope","")
         .queryParam("searchType","1000")
        .queryParam("filterTerm","")
        .queryParam("maxPrice","")
        .queryParam("top_category","${top_category}")
        .queryParam("showResultsPage","true")
        .queryParam("langId","-1")
        .queryParam("beginIndex","100")
        .queryParam("sType","SimpleSearch")
        .queryParam("metaData","")
        .queryParam("manufacturer","")
        .queryParam("filterSelected","")
        .queryParam("resultCatEntryType","")
        .queryParam("catalogId","${catalogID}")
        .queryParam("searchTerm","")
        .queryParam("facet","")
        .queryParam("minPrice","")
        .queryParam("categoryId","${categoryId}")
        .queryParam("storeId","${StoreID}")
        .queryParam("facetLabel","")
        .formParam("searchResultsPageNum","100")
        .formParam("searchResultsView","").formParam("searchResultsURL","//www.thebay.com/webapp/wcs/stores/servlet/AjaxCatalogSearchResultView?searchTermScope=&searchType=1000&filterTerm=&maxPrice=&top_category=${top_category}&showResultsPage=true&langId=-24&beginIndex=100&sType=SimpleSearch&metaData=&manufacturer=&filterSelected=&resultCatEntryType=&catalogId=${catalogID}&pageView=image&searchTerm=&facet=&minPrice=&categoryId=${categoryId}&storeId=${StoreID}&facetLabel=")
        .formParam("objectId","")
        .formParam("requesttype","ajax")
        .check(status.is(200))
      )


    }

  }
