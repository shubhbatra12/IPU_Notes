package com.example.ipunotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.ipunotes.R
import com.example.ipunotes.adapters.NotesAdapter
import com.example.ipunotes.adapters.SubjectsAdapter
import com.example.ipunotes.models.File
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.coroutines.*


class NotesFragment : Fragment() {

    private val notesList = ArrayList<File>()
    private val notesAdapter = NotesAdapter(notesList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvNotes.adapter = notesAdapter

        shimmerLayout.startShimmer()
        GlobalScope.launch {
            delay(5000)
            withContext(Dispatchers.Main){
                notesList.add(File("Unit 1 notes", "Dhruv", "01/01/2020"))
                notesList.add(File("Unit 1 notes", "Dhruv", "01/01/2020"))
                notesList.add(File("Unit 1 notes", "Dhruv", "01/01/2020"))
                notesList.add(File("Unit 1 notes", "Dhruv", "01/01/2020"))
                notesAdapter.notifyDataSetChanged()
                shimmerLayout.stopShimmer()
                shimmerLayout.visibility = View.GONE
            }
        }
    }
}
