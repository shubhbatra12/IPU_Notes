package com.example.ipunotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ipunotes.models.File
import com.example.ipunotes.models.Subject
import com.example.ipunotes.models.Video
import com.example.ipunotes.retrofit.FirebaseClient
import com.example.ipunotes.sql.DatabaseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO) { FirebaseClient.api.getAllSubjects() }
            if (response.isSuccessful) {
                response.body()?.let {
                    allSubjectsList.clear()
                    allSubjectsList.addAll(it)
                    allSubjectsListUpdated.postValue(true)
                }
            }
        }
    }

    fun loadSubjectContents(subjectId: String) {
        GlobalScope.launch(Dispatchers.Main) {
            subjectContentsUpdating.postValue(true)
            val notesResponse =
                withContext(Dispatchers.IO) { FirebaseClient.api.getNotes(subjectId) }
            val practicalFilesResponse =
                withContext(Dispatchers.IO) { FirebaseClient.api.getPracticalFiles(subjectId) }
            val examsResponse =
                withContext(Dispatchers.IO) { FirebaseClient.api.getExams(subjectId) }
            val videosResponse =
                withContext(Dispatchers.IO) { FirebaseClient.api.getVideos(subjectId) }

            if (notesResponse.isSuccessful) {
                notesResponse.body()?.let {
                    notesList.clear()
                    notesList.addAll(it)
                }
            } else {
                notesList.clear()
            }
            if (practicalFilesResponse.isSuccessful) {
                practicalFilesResponse.body()?.let {
                    practicalFilesList.clear()
                    practicalFilesList.addAll(it)
                }
            } else {
                practicalFilesList.clear()
            }
            if (examsResponse.isSuccessful) {
                examsResponse.body()?.let {
                    examsList.clear()
                    examsList.addAll(it)
                }
            } else {
                examsList.clear()
            }
            if (videosResponse.isSuccessful) {
                videosResponse.body()?.let {
                    videosList.clear()
                    videosList.addAll(it)
                }
            } else {
                videosList.clear()
            }
            subjectContentsUpdating.postValue(false)
        }
    }

    fun loadMySubjects(){
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