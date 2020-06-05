package com.example.ipunotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_mobile_login.*
import java.util.concurrent.TimeUnit

class MobileLoginActivity : AppCompatActivity() {


    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

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

    }

    lateinit var storedVerificationId: String

    fun submit(view: View) {
        val number = etNumber.text.toString()
        if(number.length != 10){
            Toast.makeText(this, getString(R.string.invalidNumber), Toast.LENGTH_SHORT).show()
        }else {
            otpText.visibility = View.VISIBLE
            submitBtn.visibility = View.VISIBLE
            val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Log.d("TAG", "onVerificationCompleted:$credential")

                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Log.w("TAG", "onVerificationFailed", e)

                    if (e is FirebaseAuthInvalidCredentialsException) {
                        // Invalid request
                    } else if (e is FirebaseTooManyRequestsException) {
                        // The SMS quota for the project has been exceeded
                    }
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    Log.d("TAG", "onCodeSent:$verificationId")
                    storedVerificationId = verificationId
                    var resendToken = token

                }
            }
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91${etNumber.text}",
                60,
                TimeUnit.SECONDS,
                this,
                callbacks)
        }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithCredential:success")
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()
                    val user = task.result?.user
                } else {
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    fun VerifyOtp(view: View) {
        val credential = PhoneAuthProvider.getCredential(storedVerificationId, pass.text.toString())
        signInWithPhoneAuthCredential(credential)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,LoginActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        finish()
    }
}