package com.example.ipunotes.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FirebaseClient {
    private val gson = GsonBuilder().create()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://ipunotesauthentication.firebaseio.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val api = retrofit.create(FirebaseService::class.java)
}