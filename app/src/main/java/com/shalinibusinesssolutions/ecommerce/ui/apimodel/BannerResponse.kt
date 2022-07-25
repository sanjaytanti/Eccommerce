package com.shalinibusinesssolutions.ecommerce.ui.apimodel

import java.sql.Time
import java.util.*
import kotlin.collections.ArrayList

data class BannerResponse(var data: ArrayList<BannerList>, var status: String, var message: String)

data class DashBoardResponse(
    var status: String,
    var message: String,
    var bannerdata: ArrayList<BannerList>,
    val categorydata: ArrayList<CategoryList>
)

data class BannerList(var ID: Int, var image: String, var title: String, var discount: Float)

data class CategoryList(
    var ID: Int,
    var name: String,
    var image: String,
    var description: String,
    var status: String
)


data class ProductResponse(
    var status: String,
    var message: String,
    var data: ArrayList<ProductList>
)

data class ProductList(
    var ID: Int,
    var name: String,
    var description: String,
    var cat_id: Int,
    var Image: String,
    var qty: Int,
    var mrp: Float,
    var unit: String,
    var price: Float,
    var status: Int,
    var stock: Float,
    var variation: Int
)


data class ProductDetailsResponse(
    var status: String,
    var message: String,
    var data: ArrayList<ProductDetailList>
)

data class ProductDetailList(
    var ID: Int,
    var productid: String,
    var name: String,
    var description: String,
    var Image: String,
    var qty: Int,
    var mrp: Float,
    var unit: String,
    var price: Float,
    var DiscountAmt: Float,
    var DiscountPer: Int
)


data class ProductDetailsListResponse(
    var status: String,
    var message: String,
    var data: ProductData,
    var productdata: ArrayList<ProductDetailsList>
)

data class ProductData(var ID: String, var name: String, var description: String, var Image: String)

data class ProductDetailsList(
    var ID: Int, var productid: Int, var name: String, var qty: Int,
    var mrp: Float, var unit: String, var price: Float, var DiscountAmt: Float, var DiscountPer: Int,
    var stock:Int,var closingstock:Int
)


data class GetAddToCardResponse(var status: String, var message: String, var data: Userdata)

data class Userdata(var userdata: String)

data class ViewDataResponse(var status: String, var message: String, var data: ProductSumData)
data class ProductSumData(var qty: Int, var price: Float)

data class AddToCartDetailResponse(
    var status: String,
    var message: String,
    var data: ArrayList<CartDetailList>
)

data class CartDetailList(
    var ID: Int, var productid: String, var variation_id: String, var name: String,
    var Image: String, var qty: Int,
    var mrp: Float, var unit: String, var price: Float, var discount: Float, var userid: Int,
    var varqty: String, var Pricecal: Float
)

data class AddressResponse(
    var status: String,
    var message: String,
    var data: ArrayList<AddressList>
)

data class AddressList(var ID : Int,var userid : Int,var Name: String, var MobNO: String, var Pincode: String, var state: String,
                       var Flat: String, var Area: String,var Landmark: String)



data class GetWishlistResponse(
    var status: String,
    var message: String,
    var data: ArrayList<WishListDetailList>
)

data class WishListDetailList(
    var ID: Int, var productid: Int, var variationid: Int, var userid: Int,
    var Image: String,var name:String, var qty: Int,
    var unit: String, var DiscountAmt: Float, var mrp: Float, var price: Float
)

data class OrderResponse(var status: String, var message: String, var data: orderData)
data class orderData(var orderid: Int)

data class getCouponResponse(var status: String, var message: String, var data: Coupandata)

data class Coupandata(var ID: Int,var couponcode: String,var Amount: Float,
                      var DiscountAmount :Float,var terms : String)



data class GetCouponListResponse(
    var status: String,
    var message: String,
    var data: ArrayList<CouponDetailList>
)
    data class CouponDetailList(var ID: Int,var couponcode: String,var Amount : Float,var DiscountAmount :Float, var Discountpercentage : Int,var terms: String)


data class getVariationResponse(var status: String,var message : String,var data : variationData )

data class variationData(var ID:Int,var productid : Int,var qty:Int,var mrp : Float,
var unit: String,var DiscountAmt: Int,var DiscountPer:Int,var price: Float,var stock  : Int,var instock: Int,
var outstock:Int,var closingstock : Int)

data class getadvancepaymentorderResponse(var status: String,var message : String,var data : ArrayList<advanceOrderList>)

data class advanceOrderList(var orderid : Int,var transactionno : String,
                            var Ordervalue: Float,var advancepayment: Float,
                            var Date: String,var Time :String,
                            var addressid : Int,var productid : Int,var variationid : Int,
                            var mrp: Float,var price: Float,var pricecal  : Float,
                            var qty : Float,var varqty : String,
                            var unit : String,var discount : Float)


data class getorderResponse(var status: String,var message : String,var data : ArrayList<orderlist>)

data class orderlist(var orderid : Int, var addressid : Int,
                     var productid : Int,var variationid : Int,
                     var transactionno : String, var paymentmode: String,
                     var mrp: Float,var price: Float,var pricecal  : Float,
                     var qty : Float,var varqty : String,
                     var unit : String,var discount : Float,
                     var Date: String,var Time :String,
                     var Ordervalue: Float,var payment: Float)




data class getOrderDetailsResponse(var status: String,var message : String,var data : ArrayList<orderdetilslist>)

data class orderdetilslist(var ID : Int, var productid : Int,
                     var name : String,var variationid : Int,
                     var varqty : String, var qty: Int,
                     var mrp: Int,var unit: String,var price  : Float,
                     var pricecal : Int,var discount : Float,
                     var userid : Int,var orderid : Int
                     )
