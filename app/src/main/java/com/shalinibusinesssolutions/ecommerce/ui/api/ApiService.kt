package com.shalinibusinesssolutions.ecommerce.ui.api

import android.view.ViewDebug
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {


    @GET("FetchUser.php")
    suspend fun getAllUser(): Response<userLoginResponse>


    @FormUrlEncoded
    @POST("UserRegistration.php")
    suspend fun addUser(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Response<userRegistrationResponse>

    @FormUrlEncoded
    @POST("UserLogin.php")
    suspend fun loginUser(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Response<userLoginResponse>

    @FormUrlEncoded
    @POST("getProductByCategoryId.php")
    suspend fun getProductByCategoryId(
        @Field("ID") ID: Int?
    ): ProductResponse
    //Response<ProductResponse>

    @FormUrlEncoded
    @POST("getProductByCategoryId.php")
    fun getProductListByCategoryId(
        @Field("ID") ID: Int?
    ): Call<ProductResponse>

    @FormUrlEncoded
    @POST("getProductWithVariation.php")
    fun getProductByProductId(
        @Field("ID") ID: Int?
    ): Call<ProductDetailsResponse>


    @FormUrlEncoded
    @POST("getproductdetailsbyproductid.php")
    fun getProductdetailsByProductId(
        @Field("ID") ID: Int?
    ): Call<ProductDetailsListResponse>


    @GET("GetBanner.php")
    suspend fun getBanner(): Response<BannerResponse>


    @GET("getDehBoard.php")
    suspend fun getDehBoard(): Response<DashBoardResponse>

    @GET("getDehBoard.php")
    fun getProductDehBoard(): Call<DashBoardResponse>


    @FormUrlEncoded
    @POST("getAddToCardResponse.php")
    fun getAddToCardResponse(
        @Field("userid") userid: Int?,
        @Field("productid") productid: Int?,
        @Field("variation_id") variation_id: Int?

    ): Call<GetAddToCardResponse>


    @FormUrlEncoded
    @POST("addToCart.php")
    fun addToCart(
        @Field("productid") productid: Int?,
        @Field("variation_id") variation_id: Int?,
        @Field("qty") qty: Int?,
        @Field("mrp") mrp: Float?,
        @Field("unit") unit: String?,
        @Field("price") price: Float?,
        @Field("discount") discount: Int?,
        @Field("userid") userid: Int?,
        @Field("varqty") varqty: String,
        @Field("productPriceCal") productPriceCal: Float

    ): Call<AddToCartResponse>

    @FormUrlEncoded
    @POST("UpdateCartMaster.php")
    fun updateToCart(
        @Field("productid") productid: Int?,
        @Field("variation_id") variation_id: Int?,
        @Field("qty") qty: Int?,
        @Field("mrp") mrp: Float?,
        @Field("unit") unit: String?,
        @Field("price") price: Float?,
        @Field("discount") discount: Int?,
        @Field("varqty") varqty: String?,
         @Field("userid") userid: Int?
    ): Call<AddToCartResponse>


    @FormUrlEncoded
    @POST("Deletecart.php")
    fun deleteToCart(
        @Field("productid") productid: Int?,
        @Field("variation_id") variation_id: Int?,
        @Field("userid") userid: Int?

    ): Call<AddToCartResponse>

    @FormUrlEncoded
    @POST("viewcart.php")
    fun viewCart(
        @Field("userid") userid: Int?

    ): Call<ViewDataResponse>



    @FormUrlEncoded
    @POST("getAddtoCardDetailsByuserid.php")
    fun getAddtoCardDetailsByuserid(
        @Field("userid") userid: Int?,
        @Field("productid") productid: Int?,
        @Field("variation_id") variation_id: Int?

    ): Call<AddToCartDetailResponse>



    @FormUrlEncoded
    @POST("getAddToCardDetails.php")
    fun getAddToCardDetails(
        @Field("userid") userid: Int?

    ): Call<AddToCartDetailResponse>

    @FormUrlEncoded
    @POST("GetAddressResponse.php")
    fun GetAddressResponse(
        @Field("userid") userid: Int?
    ): Call<GetAddToCardResponse>

    @FormUrlEncoded
    @POST("AddUserAddress.php")
    fun addUserAddress(
        @Field("userid") userid: Int?,
        @Field("Name") Name: String?,
        @Field("MobNO") MobNO: String?,
        @Field("Pincode") Pincode:String?,
        @Field("state") state: String?,
        @Field("Flat") Flat: String?,
        @Field("Area") Area: String?,
        @Field("Landmark") Landmark: String?

    ): Call<AddToCartResponse>


    @FormUrlEncoded
    @POST("getUserAddress.php")
    fun getUserAddress(
        @Field("userid") userid: Int?

    ): Call<AddressResponse>

    @FormUrlEncoded
    @POST("DeleteUserAddress.php")
    fun deleteUserAddress(
        @Field("ID") ID: Int?,
        @Field("userid") userid: Int?

    ): Call<AddToCartResponse>

    @FormUrlEncoded
    @POST("addwishlist.php")
    fun addWishlist(
        @Field("productid") productid: Int?,
        @Field("variationid") variationid: Int?,
        @Field("userid") userid: Int?

    ): Call<AddToCartResponse>


    @FormUrlEncoded
    @POST("getWishcard.php")
    fun getWishcard(
        @Field("userid") userid: Int?

    ): Call<GetWishlistResponse>

    @FormUrlEncoded
    @POST("getwishlistresponse.php")
    fun getwishlistresponse(
        @Field("userid") userid: Int?,
        @Field("productid") productid: Int?,
        @Field("variationid") variationid: Int?

    ): Call<GetAddToCardResponse>

    @FormUrlEncoded
    @POST("deleteWishList.php")
    fun deleteWishList(
        @Field("ID") ID: Int?,
        @Field("userid") userid: Int?,

    ): Call<AddToCartResponse>



    @FormUrlEncoded
    @POST("addorderbycartcashtransaction.php")
    fun addorderbycart(
        @Field("userid") userid: Int?,
        @Field("addressid") addressid: Int?,
        @Field("transactionno") transactionno: String?,
        @Field("paymentmode") paymentmode: String?,
        @Field("Date") Date: String?,
        @Field("Time") Time: String?,
        @Field("Ordervalue") Ordervalue: Float?,
        @Field("payment") payment: Float?



    ): Call<OrderResponse>


    @FormUrlEncoded
    @POST("addorbycartcarttransaction.php")
    fun addorbycartcarttransaction(
        @Field("userid") userid: Int?,
        @Field("addressid") addressid: Int?,
        @Field("transactionno") transactionno: String?,
        @Field("paymentmode") paymentmode: String?,
        @Field("Date") Date: String?,
       @Field("Time") Time: String?,
        @Field("Ordervalue") Ordervalue: Float?,
        @Field("payment") payment: Float?


    ): Call<OrderResponse>


    @FormUrlEncoded
    @POST("transferDataAddtocarttoProductorder.php")
    fun transferDataAddtocarttoProductorder(
        @Field("userid") userid: Int?,
        @Field("orderid") orderid: Int?

    ): Call<AddToCartResponse>


    @FormUrlEncoded
    @POST("DeleteAddtoCartbyuserid.php")
    fun deleteAddtoCartbyuserid(
        @Field("userid") userid: Int?

    ): Call<AddToCartResponse>


    @FormUrlEncoded
    @POST("addtoorderbyBuyNowbycash.php")
    fun addtoorderbyBuyNow(
        @Field("userid") userid: Int?,
        @Field("addressid") addressid: Int?,
        @Field("productid") productid: Int?,
        @Field("variationid") variationid: Int?,
        @Field("transactionno") transactionno: String?,
        @Field("paymentmode") paymentmode: String?,
        @Field("mrp") mrp: Float?,
        @Field("price") price: Float?,
        @Field("pricecal") pricecal:Float?,
        @Field("qty") qty: Int,
        @Field("varqty") varqty: String,
        @Field("unit") unit: String?,
        @Field("discount") discount: Int?,
        @Field("Date") Date: String?,
        @Field("Time") Time: String?,
        @Field("Ordervalue") Ordervalue: Float?,
        @Field("payment") payment: Float?

        ): Call<AddToCartResponse>


    @FormUrlEncoded
    @POST("addtoorderbynowbycart.php")
    fun addtoorderbycardBuyNow(
        @Field("userid") userid: Int?,
        @Field("addressid") addressid: Int?,
        @Field("productid") productid: Int?,
        @Field("variationid") variationid: Int?,
        @Field("transactionno") transactionno: String?,
        @Field("paymentmode") paymentmode: String?,
        @Field("mrp") mrp: Float?,
        @Field("price") price: Float?,
        @Field("pricecal") pricecal:Float?,
        @Field("qty") qty: Int,
        @Field("varqty") varqty: String,
        @Field("unit") unit: String?,
        @Field("discount") discount: Int?,
        @Field("Date") Date: String?,
        @Field("Time") Time: String?,
        @Field("Ordervalue") Ordervalue: Float?,
        @Field("payment") payment: Float?

        ): Call<AddToCartResponse>



    @FormUrlEncoded
    @POST("AdvanceBooking.php")
    fun advanceBooking(
        @Field("userid") userid: Int?,
        @Field("addressid") addressid: Int?,
        @Field("productid") productid: Int?,
        @Field("variationid") variationid: Int?,
        @Field("transactionno") transactionno: String?,
        @Field("paymentmode") paymentmode: String?,
        @Field("mrp") mrp: Float?,
        @Field("price") price: Float?,
        @Field("pricecal") pricecal:Float?,
        @Field("qty") qty: Int,
        @Field("varqty") varqty: String,
        @Field("unit") unit: String?,
        @Field("discount") discount: Int?,
        @Field("Date") Date: String?,
        @Field("Time") Time: String?,
        @Field("Ordervalue") Ordervalue: Float?,
        @Field("advancepayment") advancepayment: Float?
    ): Call<AddToCartResponse>

    @FormUrlEncoded
    @POST("advancesettelment.php")
    fun advanceSettlement(
        @Field("userid") userid: Int?,
        @Field("addressid") addressid: Int?,
        @Field("productid") productid: Int?,
        @Field("variationid") variationid: Int?,
        @Field("transactionno") transactionno: String?,
        @Field("paymentmode") paymentmode: String?,
        @Field("mrp") mrp: Float?,
        @Field("price") price: Float?,
        @Field("pricecal") pricecal:Float?,
        @Field("qty") qty: Int,
        @Field("varqty") varqty: String,
        @Field("unit") unit: String?,
        @Field("discount") discount: Int?,
        @Field("Date") Date: String?,
        @Field("Time") Time: String?,
        @Field("Ordervalue") Ordervalue: Float?,
        @Field("advancepayment") advancepayment: Float?,
        @Field("advancebookingorderid") advancebookingorderid:Int ?


    ): Call<AddToCartResponse>



    @FormUrlEncoded
    @POST("existingcustomer.php")
    fun getExistingCustomerResponse(
        @Field("userid") userid: Int?
    ): Call<GetAddToCardResponse>


    @FormUrlEncoded
    @POST("Getcoupan.php")
    fun getCouponResponse(
        @Field("couponcode") couponcode: String?
    ): Call<getCouponResponse>

    @GET("getcouponlist.php")
    fun getCouponList(): Call<GetCouponListResponse>

    @FormUrlEncoded
    @POST("getvariotion.php")
    fun getVariationDetails(
        @Field("ID") ID: Int?
    ): Call<getVariationResponse>

    @FormUrlEncoded
    @POST("getadvancepayment.php")
    fun getAdvancePaymentRecord(
        @Field("userid") userid: Int?
    ): Call<getadvancepaymentorderResponse>

    @FormUrlEncoded
    @POST("getorderlist.php")
    fun getorderlist(
        @Field("userid") userid: Int?
    ): Call<getorderResponse>

    @FormUrlEncoded
    @POST("getorderproductdetails.php")
    fun getorderproductdetails(
        @Field("userid") userid: Int?,
        @Field("orderid") orderid: Int?
    ): Call<getOrderDetailsResponse>


    @FormUrlEncoded
    @POST("getuserAddressbyaddressid.php")
    fun getUserAddressbyaddressid(
        @Field("userid") userid: Int?,
        @Field("addressid") addressid: Int?
    ): Call<AddressResponse>

}