package com.shalinibusinesssolutions.ecommerce.ui.repository

import com.shalinibusinesssolutions.ecommerce.ui.api.ApiService

class AppRepository(private val apiService: ApiService) {

    suspend fun getUserData() = apiService.getAllUser()

    suspend fun addUser(username: String, password: String) =
        apiService.addUser(username, password)


    suspend fun loginUser(username: String, password: String) =
        apiService.loginUser(username, password)


    suspend fun getBanner() = apiService.getBanner()

    suspend fun getDashBoard() = apiService.getDehBoard()


    suspend fun getProductByCategoryId(ID: Int) = apiService.getProductByCategoryId(ID)


    fun addToCart(
        productid: Int, variation_id: Int, qty: Int,
        mrp: Float, unit: String, price: Float,
        discount: Int, userid: Int, variationQty: String, productPriceCal: Float
    ) =
        apiService.addToCart(
            productid,
            variation_id,
            qty,
            mrp,
            unit,
            price,
            discount,
            userid,
            variationQty,
            productPriceCal
        )


    fun updateToCart(
        productid: Int, variation_id: Int, qty: Int,
        mrp: Float, unit: String, price: Float,
        discount: Int, variationQty: String,userid: Int
    ) =
        apiService.updateToCart(
            productid,
            variation_id,
            qty,
            mrp,
            unit,
            price,
            discount,
            variationQty,
            userid
        )

}