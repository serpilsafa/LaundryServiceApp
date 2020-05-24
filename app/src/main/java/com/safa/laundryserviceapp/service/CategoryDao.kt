package com.safa.laundryserviceapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.safa.laundryserviceapp.model.Category

@Dao
interface CategoryDao {
    @Insert
    suspend fun insertCategoryAll(vararg categories: Category) : List<Long>

    @Query("SELECT * FROM category")
    suspend fun getAllCategory() : List<Category>

    @Query("DELETE FROM category")
    suspend fun deleteAllCatogries()

}