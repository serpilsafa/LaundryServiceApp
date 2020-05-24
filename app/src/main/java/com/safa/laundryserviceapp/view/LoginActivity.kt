package com.safa.laundryserviceapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.URLUtil
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.safa.laundryserviceapp.R
import com.safa.laundryserviceapp.utility.CustomSharedPreferences
import com.safa.laundryserviceapp.utility.DIALOG_MESSAGE
import com.safa.laundryserviceapp.utility.DIALOG_TITLE
import com.safa.laundryserviceapp.utility.USER_NAME


class LoginActivity : AppCompatActivity() {
    val customSharedPreferences = CustomSharedPreferences()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_login)



    }

    fun onLogIn(view: View) {
        val editText = findViewById<EditText>(R.id.user_editText)
        val userName = editText.text.toString()

        if (userName.equals("")){
            val title = getString(R.string.login_dialog_title)
            val message = getString(R.string.login_dialog_message)

            val intent = Intent(this, CustomDialogActivity::class.java).apply {
                putExtra(DIALOG_TITLE, title)
                putExtra(DIALOG_MESSAGE, message)
            }
            startActivity(intent)
        }else{
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(USER_NAME, userName)
            }
            startActivity(intent)
            finish()
        }

    }

    private fun getURI(videoname:String): Uri{
        if (URLUtil.isValidUrl(videoname)) {
            //  an external URL
            return Uri.parse(videoname);
        } else { //  a raw resource
            return Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + videoname);
        }
    }
}
