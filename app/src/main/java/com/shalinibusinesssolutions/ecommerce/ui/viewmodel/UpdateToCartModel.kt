package com.shalinibusinesssolutions.ecommerce.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.AddToCartResponse
import com.shalinibusinesssolutions.ecommerce.ui.repository.AppRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateToCartModel(private val appRepository: AppRepository) : ViewModel() {
    val sucessresponse = MutableLiveData<AddToCartResponse>()
    val errResponse = MutableLiveData<String>()

    fun updateToCart(
        productid: Int, variation_id: Int, qty: Int,
        mrp: Float, unit: String, price: Float,
        discout: Int, variationQty: String,userid: Int
    ) {
        var updateToCartresponse =
            appRepository.updateToCart(
                productid,
                variation_id,
                qty,
                mrp,
                unit,
                price,
                discout,
                variationQty,
                userid
            )


        updateToCartresponse.enqueue(object : Callback<AddToCartResponse> {
            override fun onResponse(
                call: Call<AddToCartResponse>,
                response: Response<AddToCartResponse>
            ) {
                if (response.isSuccessful) {
                    sucessresponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {
                errResponse.postValue(t.message)
            }

        })
    }

}