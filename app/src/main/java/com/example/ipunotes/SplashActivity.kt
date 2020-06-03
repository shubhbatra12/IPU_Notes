package com.example.ipunotes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                auth.addAuthStateListener {
                    if(it.currentUser != null){
                        startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_top)
                        finish()
                    }
                    else {
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_top)
                        finish()
                    }
                }

            }
        }
    }

}
