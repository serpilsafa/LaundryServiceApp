package com.safa.laundryserviceapp.service

import com.safa.laundryserviceapp.model.APICategoryModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class LaundryAPIService {
    private val BASE_URL = "https://droidtest.xnw.cloud/api/"
    private val api = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LaundryAPI::class.java)

    fun getCategoryListFromApi():Single<APICategoryModel>{
        return api.getCountryList()
    }
}