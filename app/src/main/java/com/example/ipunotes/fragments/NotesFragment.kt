package com.example.ipunotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ipunotes.AppViewModel
import com.example.ipunotes.Extras
import com.example.ipunotes.R
import com.example.ipunotes.adapters.NotesAdapter
import com.example.ipunotes.models.File
import kotlinx.android.synthetic.main.fragment_notes.*


class NotesFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(Extras.myApp).get(AppViewModel::class.java)
    }
    private val notesList = ArrayList<File>()
    private val notesAdapter = NotesAdapter(notesList)

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
        viewModel.subjectContentsUpdating.observe(viewLifecycleOwner, Observer {
            if(it){
                startLoadingEffect()
            }else{
                stopLoadingEffect()
            }
        })
    }

    fun refreshList() {
        notesList.clear()
        notesList.addAll(viewModel.getNotesList())
        notesAdapter.notifyDataSetChanged()
    }

    fun startLoadingEffect() {
        shimmerLayout?.visibility = View.VISIBLE
        shimmerLayout?.startShimmer()
    }

    fun stopLoadingEffect() {
        shimmerLayout?.stopShimmer()
        shimmerLayout?.visibility = View.GONE
    }
}
