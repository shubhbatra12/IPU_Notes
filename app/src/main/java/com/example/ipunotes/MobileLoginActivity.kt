package com.example.ipunotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_mobile_login.*

class MobileLoginActivity : AppCompatActivity() {

    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
    private val sharedPreferencesEditor by lazy {
        sharedPreferences.edit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Extras.changeTheme(this)
        setContentView(R.layout.activity_mobile_login)

        sendOtpBtn.setOnClickListener {
            val number = etNumber.text.toString()
            if(number.length != 10 || !number.isDigitsOnly()){
                Toast.makeText(this, getString(R.string.invalidNumber), Toast.LENGTH_SHORT).show()
            }else {
                otpText.visibility = View.VISIBLE
                submitBtn.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
    }
}