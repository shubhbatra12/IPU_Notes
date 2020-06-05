package com.example.ipunotes

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ipunotes.models.File
import com.example.ipunotes.models.Subject
import com.example.ipunotes.models.Video
import com.example.ipunotes.retrofit.FirebaseClient
import com.example.ipunotes.sql.DatabaseHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "AppViewModel"

class AppViewModel(val app: Application) : AndroidViewModel(app) {

    private val databaseHandler = DatabaseHandler(app.applicationContext)

    private val notesList = ArrayList<File>()
    private val practicalFilesList = ArrayList<File>()
    private val examsList = ArrayList<File>()
    private val videosList = ArrayList<Video>()

    private val allSubjectsList = ArrayList<Subject>()
    private val mySubjectsList = ArrayList<Subject>()

    val allSubjectsListUpdated = MutableLiveData(false)
    val subjectContentsUpdating = MutableLiveData(false)

    fun loadAllSubjects() {
        FirebaseClient.api.getAllSubjects().enqueue(object : Callback<HashMap<String, Subject>> {
            override fun onFailure(call: Call<HashMap<String, Subject>>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<HashMap<String, Subject>>,
                response: Response<HashMap<String, Subject>>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: success")
                    allSubjectsList.clear()
                    response.body()?.let { map ->
                        map.keys.forEach {
                            map[it]?.let { it1 -> allSubjectsList.add(it1) }
                        }
                    }
                } else {
                    Log.d(TAG, "onResponse: failed")
                }
                allSubjectsListUpdated.postValue(true)
            }
        })

    }

    fun loadSubjectContents(subjectId: String) {
        subjectContentsUpdating.postValue(true)
        Log.d(TAG, "loadSubjectContents: ")
        var notesLoaded = false
        var filesLoaded = false
        var videosLoaded = false
        var examsLoaded = false
        FirebaseClient.api.getNotes(subjectId).enqueue(object : Callback<HashMap<String, File>> {
            override fun onFailure(call: Call<HashMap<String, File>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<HashMap<String, File>>,
                response: Response<HashMap<String, File>>
            ) {
                notesList.clear()
                response.body()?.let { map ->
                    map.keys.forEach {
                        map[it]?.let { it1 -> notesList.add(it1) }
                    }
                }
                if (filesLoaded and videosLoaded and examsLoaded) {
                    Log.d(TAG, "onResponse: All loaded")
                    subjectContentsUpdating.postValue(false)
                } else {
                    Log.d(TAG, "onResponse: notes loaded")
                    notesLoaded = true
                }
            }
        })
        FirebaseClient.api.getPracticalFiles(subjectId)
            .enqueue(object : Callback<HashMap<String, File>> {
                override fun onFailure(call: Call<HashMap<String, File>>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<HashMap<String, File>>,
                    response: Response<HashMap<String, File>>
                ) {
                    practicalFilesList.clear()
                    response.body()?.let { map ->
                        map.keys.forEach {
                            map[it]?.let { it1 -> practicalFilesList.add(it1) }
                        }
                    }
                    if (notesLoaded and videosLoaded and examsLoaded) {
                        Log.d(TAG, "onResponse: All loaded")
                        subjectContentsUpdating.postValue(false)
                    } else {
                        Log.d(TAG, "onResponse: files loaded")
                        filesLoaded = true
                    }
                }
            })
        FirebaseClient.api.getExams(subjectId).enqueue(object : Callback<HashMap<String, File>> {
            override fun onFailure(call: Call<HashMap<String, File>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<HashMap<String, File>>,
                response: Response<HashMap<String, File>>
            ) {
                examsList.clear()
                response.body()?.let { map ->
                    map.keys.forEach {
                        map[it]?.let { it1 -> examsList.add(it1) }
                    }
                }
                if (filesLoaded and videosLoaded and notesLoaded) {
                    Log.d(TAG, "onResponse: All loaded")
                    subjectContentsUpdating.postValue(false)
                } else {
                    Log.d(TAG, "onResponse: exams loaded")
                    examsLoaded = true
                }
            }
        })
        FirebaseClient.api.getVideos(subjectId).enqueue(object : Callback<HashMap<String, Video>> {
            override fun onFailure(call: Call<HashMap<String, Video>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<HashMap<String, Video>>,
                response: Response<HashMap<String, Video>>
            ) {
                videosList.clear()
                response.body()?.let { map ->
                    map.keys.forEach {
                        map[it]?.let { it1 -> videosList.add(it1) }
                    }
                }
                if (filesLoaded and notesLoaded and examsLoaded) {
                    Log.d(TAG, "onResponse: All loaded")
                    subjectContentsUpdating.postValue(false)
                } else {
                    Log.d(TAG, "onResponse: videos loaded")
                    videosLoaded = true
                }
            }
        })
    }

    fun loadMySubjects() {
        mySubjectsList.addAll(databaseHandler.getMySubjects())
    }

    //Getters
    fun getNotesList(): ArrayList<File> = notesList
    fun getPracticalFilesList(): ArrayList<File> = practicalFilesList
    fun getExamsList(): ArrayList<File> = examsList
    fun getVideosList(): ArrayList<Video> = videosList
    fun getAllSubjectsList(): ArrayList<Subject> = allSubjectsList
    fun getMySubjectsList(): ArrayList<Subject> = mySubjectsList

}