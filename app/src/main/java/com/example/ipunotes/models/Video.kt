package com.example.ipunotes.models

data class Video (
    val url:String,
    val thumbnailUrl: String? = null,
    val description: String? = null,
    val title: String? = null,
    val channelName: String? = null
)