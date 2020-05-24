package com.safa.laundryserviceapp.model

import com.google.gson.annotations.SerializedName

data class APICategoryModel (
    @SerializedName("status")
    val status:Int?,
    @SerializedName("msg")
    val message:String?,
    @SerializedName("data")
    val data:List<Category>?
)