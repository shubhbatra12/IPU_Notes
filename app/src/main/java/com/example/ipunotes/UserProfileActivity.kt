package com.example.ipunotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Extras.changeTheme(this)
        setContentView(R.layout.activity_user_profile)
        loadContent()

        if (auth.currentUser?.phoneNumber.isNullOrEmpty()){
            profEmail.isClickable = false
        } else {
            profNumber.isClickable = false
        }



        saveBtnProfile.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        //Toast.makeText(this,auth.currentUser?.displayName,Toast.LENGTH_SHORT).show()
    }

    private fun loadContent() {
        if (auth.currentUser?.phoneNumber.isNullOrEmpty()) {

            Picasso.get().load(auth.currentUser?.photoUrl).into(imgViewProfile)
            profName.setText(auth.currentUser?.displayName)
            profEmail.setText(auth.currentUser?.email)
        } else {
            profNumber.setText(auth.currentUser?.phoneNumber)
        }
    }

}