package com.safa.laundryserviceapp.service

import com.safa.laundryserviceapp.model.APICategoryModel
import io.reactivex.Single
import retrofit2.http.GET


interface LaundryAPI {
    @GET("cat_list")
    fun getCountryList():Single<APICategoryModel>
}