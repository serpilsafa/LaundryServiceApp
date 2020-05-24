package com.safa.laundryserviceapp.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.safa.laundryserviceapp.R
import com.safa.laundryserviceapp.utility.DIALOG_MESSAGE
import com.safa.laundryserviceapp.utility.DIALOG_TITLE
import kotlinx.android.synthetic.main.activity_custom_dialog.*

class CustomDialogActivity : Activity() {
    lateinit var title:String
    lateinit var message:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_dialog)

        if (intent != null){
            title = intent.getStringExtra(DIALOG_TITLE)
            message = intent.getStringExtra(DIALOG_MESSAGE)

            dialog_title_textView.setText(title)
            dialog_content_TextView.setText(message)
        }

    }

    fun onCloseDialog(view: View) {
        finish()
    }
}
