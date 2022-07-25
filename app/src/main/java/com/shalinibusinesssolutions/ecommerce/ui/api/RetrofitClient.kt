package com.shalinibusinesssolutions.ecommerce.ui.api

import android.os.Build
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object RetrofitClient {

    var BASE_URL = "https://app.shalinibusiness.com/Ecommerce/"

    private val gson: Gson by lazy { GsonBuilder().setLenient().create() }

    private val logger: HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }

    private val headerintercepter = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request: Request = chain.request()
            request = request.newBuilder()
                .addHeader("x-device-type", Build.DEVICE)
                .addHeader("Accept-Language", Locale.getDefault().language)
                .build()
            val response: Response = chain.proceed(request)
            return response

        }

    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(headerintercepter)
            .addInterceptor(logger)
            .build()

    }
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    }
    val apiservice: ApiService by lazy { retrofit.create(ApiService::class.java) }

}