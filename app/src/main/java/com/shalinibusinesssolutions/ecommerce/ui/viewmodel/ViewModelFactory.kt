package com.shalinibusinesssolutions.ecommerce.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shalinibusinesssolutions.ecommerce.ui.repository.AppRepository

class ViewModelFactory(private val appRepository: AppRepository, private val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddUserViewModel::class.java) -> {
                AddUserViewModel(appRepository, context) as T
            }

            modelClass.isAssignableFrom(LoginUserViewModel::class.java) -> {
                LoginUserViewModel(appRepository, context) as T
            }

            modelClass.isAssignableFrom(GetBannerViewModel::class.java) -> {
                GetBannerViewModel(appRepository, context) as T
            }

            modelClass.isAssignableFrom(GetDasBoardViewModel::class.java) -> {
                GetDasBoardViewModel(appRepository, context) as T
            }

            modelClass.isAssignableFrom(GetProductViewModel::class.java) -> {
                GetProductViewModel(appRepository, context) as T
            }

            modelClass.isAssignableFrom(GetProductListViewModel::class.java) -> {
                GetProductListViewModel() as T
            }
            modelClass.isAssignableFrom(AddToCartViewModel::class.java) -> {
                AddToCartViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(UpdateToCartModel::class.java) -> {
                UpdateToCartModel(appRepository) as T
            }
            else -> throw  IllegalArgumentException("view Model not found")
        }
    }
}