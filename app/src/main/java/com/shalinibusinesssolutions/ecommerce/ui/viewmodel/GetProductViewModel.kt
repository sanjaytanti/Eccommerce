package com.shalinibusinesssolutions.ecommerce.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.shalinibusinesssolutions.ecommerce.ui.repository.AppRepository

class GetProductViewModel(private val appRepository: AppRepository, private val context: Context) :
    ViewModel() {
//    val _response = MutableLiveData<Event<Resource<ProductResponse>>>()
//    val response: LiveData<Event<Resource<ProductResponse>>> = _response
//
//    fun getProduct(id: Int) = viewModelScope.launch {
//        //Toast.makeText(context, "id :" + id.toString(), Toast.LENGTH_SHORT).show()
//        susGetProduct(id)
//
//    }
//    private suspend fun susGetProduct(id: Int) {
//        _response.postValue(Event(Resource.Loading()))
//
//        try {
//            val res = appRepository.getProductByCategoryId(id)
//            _response.postValue(handleResponse(res))
//        } catch (t: Throwable) {
//            when (t) {
//                is IOException -> {
//                    _response.postValue(Event(Resource.Error("internet error accor")))
//                }
//                else -> {
//
//                    _response.postValue(Event(Resource.Error("something   went wrong ")))
//                }
//            }
//        }
//    }
//
//    private fun handleResponse(res: Response<ProductResponse>): Event<Resource<ProductResponse>>? {
//        if(res.isSuccessful)
//        {
//            res.body()?.let {
//                return Event(Resource.Success(it))
//            }
//        }
//        return Event(Resource.Error(res.message()))
//    }

}