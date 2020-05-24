package com.safa.laundryserviceapp.utility

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreferences {

    companion object {

        private var sharedPreferences: SharedPreferences? = null

        @Volatile private var instance: CustomSharedPreferences? = null
        private val lock = Any()

        operator fun invoke(context: Context) : CustomSharedPreferences = instance ?: synchronized(lock) {
            instance ?: makeCustomSharedPreferences(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPreferences(context: Context) : CustomSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }

    }

    fun enterTime(time: Long){
        sharedPreferences?.edit(commit = true){
            putLong(TIME,time)
        }
    }

    fun returnTime()= sharedPreferences?.getLong(TIME, 0)


    fun enterLanguage(language: Int){
        sharedPreferences?.edit(commit = true){
            putInt(LANGUAGE,language)
        }
    }

    fun returnLanguage()= sharedPreferences?.getInt(LANGUAGE, ENGLISH)
}