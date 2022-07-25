package com.shalinibusinesssolutions.ecommerce.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.DashBoardResponse
import com.shalinibusinesssolutions.ecommerce.ui.repository.AppRepository
import com.shalinibusinesssolutions.ecommerce.ui.utility.Event
import com.shalinibusinesssolutions.ecommerce.ui.utility.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class GetDasBoardViewModel(private val appRepository: AppRepository, private val context: Context) :
    ViewModel() {

    val _response = MutableLiveData<Event<Resource<DashBoardResponse>>>()
    val response: LiveData<Event<Resource<DashBoardResponse>>> = _response

    fun getDasBoard() = viewModelScope.launch {
        susGetDasBoard()

    }

    private suspend fun susGetDasBoard() {
        _response.postValue(Event(Resource.Loading()))

        try {
            val res = appRepository.getDashBoard()
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

    private fun handleResponse(res: Response<DashBoardResponse>): Event<Resource<DashBoardResponse>>? {
        if (res.isSuccessful) {
            res.body()?.let {
                return Event(Resource.Success(it))
            }
        }
        return Event(Resource.Error(res.message()))
    }


}