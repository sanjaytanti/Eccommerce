package com.shalinibusinesssolutions.ecommerce.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetProductListViewModel : ViewModel() {
    val ProductResponse = MutableLiveData<ProductResponse>()
    val errorResponse = MutableLiveData<String>()

    //  fun getProductData(productid : Int)
    //{
    //  val retroInstance = RetrofitClient.apiservice.getProductByCategoryId(productid)

    /*
    val response =RetrofitClient.apiservice.getProductListByCategoryId(productid)

    response.enqueue(object : Callback<ProductResponse> {
        override fun onResponse(
            call: Call<ProductResponse>,
            response: Response<ProductResponse>
        ) {
            if(response.isSuccessful)
            {
            //    Toast.makeText(context,response.body().toString(), Toast.LENGTH_LONG).show()
                ProductResponse.postValue(response.body())

            }
            else
            {
                errorResponse.postValue(response.body()?.message)
            }

        }

        override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
          //  Toast.makeText(context,t.message.toString(), Toast.LENGTH_LONG).show()
            errorResponse.postValue(t.message)
        }
    })
}
*/
    lateinit var ProductData: MutableLiveData<ProductResponse>

    init {
        ProductData = MutableLiveData()
    }

    fun productList(): MutableLiveData<ProductResponse> {
        return ProductData
    }

    fun makeApiCall(productid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val retroInstance = RetrofitClient.apiservice.getProductByCategoryId(productid)

            ProductData.postValue(retroInstance)
        }
    }


}