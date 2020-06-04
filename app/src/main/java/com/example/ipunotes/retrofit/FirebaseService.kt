package com.example.ipunotes.retrofit

import com.example.ipunotes.*
import com.example.ipunotes.models.File
import com.example.ipunotes.models.Subject
import com.example.ipunotes.models.Video
import retrofit2.Response
import retrofit2.http.GET

interface FirebaseService {

    @GET("$ALL_SUBJECTS_KEY.json")
    suspend fun getAllSubjects(): Response<List<Subject>>

    @GET("{subjectId}/$NOTES_KEY.json")
    suspend fun getNotes(subjectId: String): Response<List<File>>

    @GET("{subjectId}/$PRACTICAL_FILES_KEY.json")
    suspend fun getPracticalFiles(subjectId: String): Response<List<File>>

    @GET("{subjectId}/$EXAMS_KEY.json")
    suspend fun getExams(subjectId: String): Response<List<File>>

    @GET("{subjectId}/$VIDEOS_KEY.json")
    suspend fun getVideos(subjectId: String): Response<List<Video>>
}