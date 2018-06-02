package testSetUp

import BaseTest.BaseTest
import PlaceOrderChains.PlaceOrderChains._
import SaksChains.SaksCheckoutChains._
import SaksChains.SaksFacetSearchChains.SaksFacetSearch
import SaksChains.SaksPDPChains._
import SaksChains.SaksPagnation._
import SaksChains.SaksSearchStringChains.SaksSearchString
import SaksO5Chains.SaksO5CheckoutChains._
import io.gatling.core.Predef._

import scala.language.postfixOps

/**
  * Created by aindana on 3/14/2016.
  */
class PlaceOrders extends BaseTest {



  val DC1 = scenario("DC Only")
   .exitBlockOnFail {
        exec(clearSession.clearSession)
          .exec(HomePage.SaksHome)
          .feed(DCProducts)
          .exec(SearchPDPDC.SaksSearch)
          .doIf(session => session("SoldOut").as[Int] == 0) {
            doIf(session => session("sku_idCount").as[Int] > 0) {
              doIf(session => session("availableItems").as[Int] == 1) {
                exec(AddToBag.AddToBag)
                  .exec(ShoppingBag.SaksshoppingBag)
                  .exec(Checkout.SaksCheckout)
                  .exec(ShippingAddress.Shipping)
                  .exec(BillingAddress.Billing)
                 //  .exec(SubmitOrder.SubmitOrder)
              }
            }
          }

      }


  val DC1Multi = scenario("DC Only Multi Qty")
    .exitBlockOnFail {
      exec(clearSession.clearSession)
        .exec(HomePage.SaksHome)
        .feed(DCProducts)
        .exec(SearchPDPDC.SaksSearch)
        .doIf(session => session("SoldOut").as[Int] == 0) {
          doIf(session => session("sku_idCount").as[Int] > 0) {
            doIf(session => session("availableItems").as[Int] == 1) {
              exec(AddToBag.AddToBag)
                .exec(ShoppingBag.SaksshoppingBag)
                .exec(ChangeQty.ChangeQty)
                .exec(Checkout.SaksCheckout)
                .exec(ShippingAddress.Shipping)
                .exec(BillingAddress.Billing)
              //  .exec(SubmitOrder.SubmitOrder)
            }
          }
        }

    }

  val Store1 = scenario("Store Only")
    .exitBlockOnFail {
      exec(clearSession.clearSession)
        .exec(HomePage.SaksHome)
        .exec(SearchPDPStore.SaksSearch)
        .doIf(session => session("SoldOut").as[Int] == 0) {
          doIf(session => session("sku_idCount").as[Int] > 0) {
            doIf(session => session("availableItems").as[Int] == 1) {
              exec(AddToBag.AddToBag)
                .exec(ShoppingBag.SaksshoppingBag)
                .exec(Checkout.SaksCheckout)
                .exec(ShippingAddress.Shipping)
                .exec(BillingAddress.Billing)
              //  .exec(SubmitOrder.SubmitOrder)
            }
          }
        }

    }

  val Store1Multi = scenario("Store Only Multi Qty")
    .exitBlockOnFail {
      exec(clearSession.clearSession)
        .exec(HomePage.SaksHome)
        .exec(SearchPDPStore.SaksSearch)
        .doIf(session => session("SoldOut").as[Int] == 0) {
          doIf(session => session("sku_idCount").as[Int] > 0) {
            doIf(session => session("availableItems").as[Int] == 1) {
              exec(AddToBag.AddToBag)
                .exec(ShoppingBag.SaksshoppingBag)
                .exec(ChangeQty.ChangeQty)
                .exec(Checkout.SaksCheckout)
                .exec(ShippingAddress.Shipping)
                .exec(BillingAddress.Billing)
              //  .exec(SubmitOrder.SubmitOrder)
            }
          }
        }

    }

  val DC2 = scenario("DC Only Multiple Items")

    .exitBlockOnFail {
      exec(clearSession.clearSession)
        .exec(HomePage.SaksHome)
          .repeat(2) {
           exec(SearchPDPDC.SaksSearch)
              .doIf(session => session("SoldOut").as[Int] == 0) {
                doIf(session => session("sku_idCount").as[Int] > 0) {
                  doIf(session => session("availableItems").as[Int] == 1) {
                    exec(AddToBag.AddToBag)
                  }
                }
              }
          }
        .doIf(session => session("SoldOut").as[Int] == 0) {
          doIf(session => session("sku_idCount").as[Int] > 0) {
            doIf(session => session("availableItems").as[Int] == 1) {
                exec(ShoppingBag.SaksshoppingBag)
               // .exec(ChangeQty.ChangeQty)
                .exec(Checkout.SaksCheckout)
                .exec(ShippingAddress.Shipping)
                .exec(BillingAddress.Billing)
              //    .exec(SaksO5SubmitOrder.SubmitOrder)
            }
          }
        }

    }


  val DC2MultiQty = scenario("DC Only Multiple Items Multiple Qty")

    .exitBlockOnFail {
      exec(clearSession.clearSession)
        .exec(HomePage.SaksHome)
        .repeat(2) {
          exec(SearchPDPDC.SaksSearch)
            .doIf(session => session("SoldOut").as[Int] == 0) {
              doIf(session => session("sku_idCount").as[Int] > 0) {
                doIf(session => session("availableItems").as[Int] == 1) {
                  exec(AddToBag.AddToBag)
                    .doIf(session => session("SoldOut").as[Int] == 0) {
                      doIf(session => session("sku_idCount").as[Int] > 0) {
                        doIf(session => session("availableItems").as[Int] == 1) {
                          exec(ShoppingBag.SaksshoppingBag)
                            .exec(ChangeQty.ChangeQty)

                        }
                      }
                    }
                }
              }
            }
        }
        .exec(Checkout.SaksCheckout)
        .exec(ShippingAddress.Shipping)
        .exec(BillingAddress.Billing)
      //    .exec(SaksO5SubmitOrder.SubmitOrder)

    }


  val Store2 = scenario("Store Only Multiple Items")

    .exitBlockOnFail {
      exec(clearSession.clearSession)
        .exec(HomePage.SaksHome)
        .repeat(2) {
          exec(SearchPDPStore.SaksSearch)
            .doIf(session => session("SoldOut").as[Int] == 0) {
              doIf(session => session("sku_idCount").as[Int] > 0) {
                doIf(session => session("availableItems").as[Int] == 1) {
                  exec(AddToBag.AddToBag)
                }
              }
            }
        }
        .doIf(session => session("SoldOut").as[Int] == 0) {
          doIf(session => session("sku_idCount").as[Int] > 0) {
            doIf(session => session("availableItems").as[Int] == 1) {
              exec(ShoppingBag.SaksshoppingBag)
                .exec(Checkout.SaksCheckout)
                .exec(ShippingAddress.Shipping)
                .exec(BillingAddress.Billing)
              //    .exec(SaksO5SubmitOrder.SubmitOrder)
            }
          }
        }

    }

  val Store2MultiQty = scenario("Store Only Multiple Items Multiple Qty")

    .exitBlockOnFail {
      exec(clearSession.clearSession)
        .exec(HomePage.SaksHome)
        .repeat(2) {
          exec(SearchPDPStore.SaksSearch)
            .doIf(session => session("SoldOut").as[Int] == 0) {
              doIf(session => session("sku_idCount").as[Int] > 0) {
                doIf(session => session("availableItems").as[Int] == 1) {
                  exec(AddToBag.AddToBag)
                    .doIf(session => session("SoldOut").as[Int] == 0) {
                      doIf(session => session("sku_idCount").as[Int] > 0) {
                        doIf(session => session("availableItems").as[Int] == 1) {
                          exec(ShoppingBag.SaksshoppingBag)
                            .exec(ChangeQty.ChangeQty)

                        }
                      }
                    }
                }
              }
            }
        }
        .exec(Checkout.SaksCheckout)
        .exec(ShippingAddress.Shipping)
        .exec(BillingAddress.Billing)
      //    .exec(SaksO5SubmitOrder.SubmitOrder)

    }


  val DCStore = scenario("DC and Store")

    .exitBlockOnFail {
      exec(clearSession.clearSession)
        .exec(HomePage.SaksHome)
        .repeat(2) {
          exec(SearchPDPDCStore.SaksSearch)
            .doIf(session => session("SoldOut").as[Int] == 0) {
              doIf(session => session("sku_idCount").as[Int] > 0) {
                doIf(session => session("availableItems").as[Int] == 1) {
                  exec(AddToBag.AddToBag)
                }
              }
            }
        }
        .doIf(session => session("SoldOut").as[Int] == 0) {
          doIf(session => session("sku_idCount").as[Int] > 0) {
            doIf(session => session("availableItems").as[Int] == 1) {
              exec(ShoppingBag.SaksshoppingBag)
                .exec(Checkout.SaksCheckout)
                .exec(ShippingAddress.Shipping)
                .exec(BillingAddress.Billing)
              //    .exec(SaksO5SubmitOrder.SubmitOrder)
            }
          }
        }

    }

  val DCStoreMultiQty = scenario("DC Store Multiple Qty")

    .exitBlockOnFail {
      exec(clearSession.clearSession)
        .exec(HomePage.SaksHome)
        .repeat(2) {
          exec(SearchPDPDCStore.SaksSearch)
            .doIf(session => session("SoldOut").as[Int] == 0) {
              doIf(session => session("sku_idCount").as[Int] > 0) {
                doIf(session => session("availableItems").as[Int] == 1) {
                  exec(AddToBag.AddToBag)
                    .doIf(session => session("SoldOut").as[Int] == 0) {
                      doIf(session => session("sku_idCount").as[Int] > 0) {
                        doIf(session => session("availableItems").as[Int] == 1) {
                          exec(ShoppingBag.SaksshoppingBag)
                            .exec(ChangeQty.ChangeQty)

                        }
                      }
                    }
                }
              }
            }
        }
        .exec(Checkout.SaksCheckout)
        .exec(ShippingAddress.Shipping)
        .exec(BillingAddress.Billing)
      //    .exec(SaksO5SubmitOrder.SubmitOrder)

    }

//  val allScenarios = PDP.exec(FacetSearch).exec(Pagnation).exec(SS).exec(SearchCheckout)

  val allScenarios = DC1.exec(DC1Multi).exec(DC2).exec(DC2MultiQty).exec(Store1).exec(Store1Multi).exec(Store2).exec(Store2MultiQty).exec(DCStore).exec(DCStoreMultiQty)

  setUp(

    allScenarios.inject(
      atOnceUsers(1)
    ).protocols(httpConf)
    )


}

