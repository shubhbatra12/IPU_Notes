package com.example.ipunotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
    private val sharedPreferencesEditor by lazy {
        sharedPreferences.edit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Extras.changeTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mobileLoginBtn.setOnClickListener {
            startActivity(Intent(this,MobileLoginActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }

        skipBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
            finish()
        }


    }

}