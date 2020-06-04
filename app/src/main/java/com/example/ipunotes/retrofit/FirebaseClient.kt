package com.example.ipunotes.retrofit

import retrofit2.Retrofit

object FirebaseClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("")
        .build()

    val api = retrofit.create(FirebaseService::class.java)
}