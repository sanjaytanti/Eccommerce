package com.shalinibusinesssolutions.ecommerce.ui.utility

import androidx.multidex.MultiDexApplication

class AppController : MultiDexApplication() {
    var Address_id = "0"
    var coupon_id = "0"
    var coupon_code = ""
    var coupon_desc = ""
    var product_variation_id = ""
    var productID = ""
    var product_qty = ""
    var minPrice = "0"
    var maxPrice = "0"
    var sortName = ""
    var category = ""
    var paymentSource = ""
    var paymentStatus = ""
    var AdvancePaidAmount = ""
    var AdvancePage = ""
    var AdvanceOrderID = ""
    var cartPrice = ""
    var PromoCode = "0"
    var couponPageIndicator = ""
    var BuyNowPrice = ""
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        val TAG = AppController::class.java
            .simpleName

        @get:Synchronized
        var instance: AppController? = null
            private set
    }
}
