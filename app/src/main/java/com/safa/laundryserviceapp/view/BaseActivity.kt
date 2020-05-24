package com.safa.laundryserviceapp.view

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import java.util.*

class BaseActivity : AppCompatActivity() {
    companion object{
        public var dLocale: Locale? = null
    }

    init {
        updateConfig(this)
    }

    fun updateConfig(wrapper: ContextThemeWrapper){
        if (dLocale == Locale(""))
            return

        Locale.setDefault(dLocale)
        val  configuration = Configuration()
        configuration.setLocale(dLocale)
        wrapper.applyOverrideConfiguration(configuration)
    }


}
