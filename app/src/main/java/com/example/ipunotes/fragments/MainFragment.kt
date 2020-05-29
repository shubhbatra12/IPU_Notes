package com.example.ipunotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.example.ipunotes.R
import com.example.ipunotes.adapters.SubjectsAdapter
import com.example.ipunotes.models.Subject
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private val subjectsList = ArrayList<Subject>()
    private val subjectsAdapter = SubjectsAdapter(subjectsList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvSubjects.adapter = subjectsAdapter
        rvSubjects.addItemDecoration(DividerItemDecoration(context, VERTICAL))

        subjectsList.add(Subject("Applied Maths"))
        subjectsList.add(Subject("Communication System"))
        subjectsAdapter.notifyDataSetChanged()
    }
}
