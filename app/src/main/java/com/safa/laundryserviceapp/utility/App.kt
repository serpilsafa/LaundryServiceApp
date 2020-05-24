package com.safa.laundryserviceapp.utility

import android.app.Application
import android.preference.PreferenceManager
import com.safa.laundryserviceapp.view.BaseActivity
import java.util.*

class App: Application(){
    lateinit var customSharedPreferences: CustomSharedPreferences
    override fun onCreate() {
        super.onCreate()
        customSharedPreferences = CustomSharedPreferences(applicationContext)

        var change = ""
        val language = customSharedPreferences.returnLanguage()
        if (language == SPANISH) {
            change="es"
        } else if (language == ENGLISH ) {
            change = "en"
        }else {
            change =""
        }

        BaseActivity.dLocale = Locale(change)

    }
}