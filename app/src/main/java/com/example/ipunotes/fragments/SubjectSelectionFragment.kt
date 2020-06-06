package com.example.ipunotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.ipunotes.AppViewModel
import com.example.ipunotes.Extras
import com.example.ipunotes.R
import com.example.ipunotes.adapters.OnSubjectSelectionChangedListener
import com.example.ipunotes.adapters.SubjectSelectionAdapter
import com.example.ipunotes.adapters.SubjectsAdapter
import com.example.ipunotes.models.Subject
import kotlinx.android.synthetic.main.fragment_subject_selection.*


class SubjectSelectionFragment : DialogFragment(),OnSubjectSelectionChangedListener {

    private val selectedSubjectsList = ArrayList<Subject>()
    private val allSubjectsList = ArrayList<Subject>()
    private val notSelectedSubjectsList = ArrayList<Subject>()

    private val viewModel by lazy {
        ViewModelProvider(Extras.myApp).get(AppViewModel::class.java)
    }
    private val adapter = SubjectSelectionAdapter(this, selectedSubjectsList, notSelectedSubjectsList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_subject_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAllSubjects.adapter = adapter
        populateLists()
        adapter.notifyDataSetChanged()

        tvDone.setOnClickListener {
            dialog?.cancel()
        }

    }

    private fun populateLists() {
        selectedSubjectsList.clear()
        allSubjectsList.clear()
        notSelectedSubjectsList.clear()

        selectedSubjectsList.addAll(viewModel.getMySubjectsList())
        allSubjectsList.addAll(viewModel.getAllSubjectsList())
        notSelectedSubjectsList.addAll(allSubjectsList)
        for (subject in selectedSubjectsList) {
            notSelectedSubjectsList.remove(subject)
        }
    }

    override fun onSubjectClick(subject: Subject, isSelected: Boolean) {
        if(isSelected){
            selectedSubjectsList.add(subject)
            notSelectedSubjectsList.remove(subject)
            viewModel.addMySubject(subject)
        }else{
            notSelectedSubjectsList.add(subject)
            selectedSubjectsList.remove(subject)
            viewModel.removeMySubject(subject)
        }
        adapter.notifyDataSetChanged()
    }
}