package com.example.ipunotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
            otpText.visibility = View.VISIBLE
            submitBtn.visibility = View.VISIBLE
        }
    }
    override fun onResume() {
        super.onResume()
        if (sharedPreferences.getBoolean(THEME_CHANGED, false)) {
            sharedPreferencesEditor.putBoolean(THEME_CHANGED, false).commit()
            recreate()
        } else if (sharedPreferences.getBoolean(LANGUAGE_CHANGED, false)) {
            sharedPreferencesEditor.putBoolean(LANGUAGE_CHANGED, false).commit()
            recreate()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
    }
}