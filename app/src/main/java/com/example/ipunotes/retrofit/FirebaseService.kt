package com.example.ipunotes.retrofit

import com.example.ipunotes.*
import com.example.ipunotes.models.File
import com.example.ipunotes.models.Subject
import com.example.ipunotes.models.Video
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FirebaseService {

    @GET("$ALL_SUBJECTS_KEY.json")
    fun getAllSubjects(): Call<HashMap<String, Subject>>

    @GET("{subjectId}/$NOTES_KEY.json")
    fun getNotes(@Path("subjectId") subjectId: String): Call<HashMap<String, File>>

    @GET("{subjectId}/$PRACTICAL_FILES_KEY.json")
    fun getPracticalFiles(@Path("subjectId") subjectId: String): Call<HashMap<String, File>>

    @GET("{subjectId}/$EXAMS_KEY.json")
    fun getExams(@Path("subjectId") subjectId: String): Call<HashMap<String, File>>

    @GET("{subjectId}/$VIDEOS_KEY.json")
    fun getVideos(@Path("subjectId") subjectId: String): Call<HashMap<String, Video>>
}