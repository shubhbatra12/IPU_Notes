package com.example.ipunot

import com.example.ipunotes.R

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        if (auth.currentUser?.phoneNumber.isNullOrEmpty()) {

            Picasso.get().load(auth.currentUser?.photoUrl).into(imgViewProfile)
            profName.setText(auth.currentUser?.displayName)
            profEmail.setText(auth.currentUser?.email)
        } else {
            profNumber.setText(auth.currentUser?.phoneNumber)
        }

        saveBtnProfile.setOnClickListener {
            if (profNumber.text?.length==10){
                //save()
            } else {
                Toast.makeText(this, "Enter Valid Credentials", Toast.LENGTH_SHORT).show()
            }
        }
        //Toast.makeText(this,auth.currentUser?.displayName,Toast.LENGTH_SHORT).show()
    }
}