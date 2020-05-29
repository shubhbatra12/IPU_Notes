package com.example.ipunotes.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.example.ipunotes.R
import com.example.ipunotes.adapters.OnSubjectClickListener
import com.example.ipunotes.adapters.SubjectsAdapter
import com.example.ipunotes.models.Subject
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), OnSubjectClickListener {

    private val subjectsList = ArrayList<Subject>()
    private val subjectsAdapter = SubjectsAdapter(this, subjectsList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvSubjects.adapter = subjectsAdapter
        rvSubjects.addItemDecoration(DividerItemDecoration(context, VERTICAL))

        subjectsList.clear()

        subjectsList.add(Subject("Applied Maths"))
        subjectsList.add(Subject("Communication System"))
        subjectsAdapter.notifyDataSetChanged()

    }

    override fun onSubjectClick(position: Int) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.container, SubjectFragment(subjectsList[position]))
                .addToBackStack(null)
                .commit()
        }else{
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.container2, SubjectFragment(subjectsList[position]))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(resources.configuration.orientation==Configuration.ORIENTATION_PORTRAIT){
            (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.app_name)
        }
    }
}
