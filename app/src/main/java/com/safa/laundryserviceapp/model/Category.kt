package com.safa.laundryserviceapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Category(

    @ColumnInfo(name = "id")
    @SerializedName("id")
    val categoryId: String?,

    @ColumnInfo(name = "order")
    @SerializedName("order_no")
    val category_order_no: String?,

    @ColumnInfo(name = "title")
    @SerializedName("cat_title")
    val category_title: String?

){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}