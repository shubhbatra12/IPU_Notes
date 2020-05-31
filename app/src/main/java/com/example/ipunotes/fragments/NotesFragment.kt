package com.example.ipunotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.ipunotes.R
import com.example.ipunotes.adapters.SubjectsAdapter
import com.example.ipunotes.models.File


class NotesFragment : Fragment() {

    private val notesList = ArrayList<File>()
    //private val notesAdapter = PdfAdapter(notesList)

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

}