package com.example.ipunotes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "SplashActivity"
class SplashActivity : AppCompatActivity() {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }
    private val viewModel by lazy {
        ViewModelProvider(Extras.myApp, ViewModelProvider.AndroidViewModelFactory(application)).get(AppViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (auth.currentUser != null) {
            Log.d(TAG, "onCreate: current user not null")
            startMainActivity()
        } else {
            Log.d(TAG, "onCreate: current user null")
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
            finish()
        }

    }

    private fun startMainActivity() {

        viewModel.loadAllSubjects()
        viewModel.loadMySubjects()
        viewModel.allSubjectsListUpdated.observe(this, Observer {
            Log.d(TAG, "startMainActivity: allSubjectLstUpdated changed: ${viewModel.allSubjectsListUpdated.value}")
            if (it) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
                finish()
                viewModel.allSubjectsListUpdated.removeObservers(this@SplashActivity)
            }
        })
    }

}
