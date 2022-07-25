package com.shalinibusinesssolutions.ecommerce.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.BannerResponse
import com.shalinibusinesssolutions.ecommerce.ui.repository.AppRepository
import com.shalinibusinesssolutions.ecommerce.ui.utility.Event
import com.shalinibusinesssolutions.ecommerce.ui.utility.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class GetBannerViewModel(private val appRepository: AppRepository, private val context: Context) :
    ViewModel() {


    val _response = MutableLiveData<Event<Resource<BannerResponse>>>()
    val response: LiveData<Event<Resource<BannerResponse>>> = _response

    fun getBanner() = viewModelScope.launch {
        susGetBanner()

    }

    private suspend fun susGetBanner() {
        _response.postValue(Event(Resource.Loading()))

        try {
            val res = appRepository.getBanner()
            _response.postValue(handleResponse(res))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _response.postValue(Event(Resource.Error("internet error accor")))
                }
                else -> {

                    _response.postValue(Event(Resource.Error("something   went wrong ")))
                }
            }
        }
    }

    private fun handleResponse(res: Response<BannerResponse>): Event<Resource<BannerResponse>>? {
        if (res.isSuccessful) {
            res.body()?.let {
                return Event(Resource.Success(it))
            }
        }
        return Event(Resource.Error(res.message()))
    }


}