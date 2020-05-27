package com.example.ipunotes.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ipunotes.R
import com.example.ipunotes.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_subject.*


class SubjectFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subject, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPagerAdapter = getFragmentManager()?.let { ViewPagerAdapter(it) }
        viewPagerAdapter?.apply {
            add(NotesFragment())
            add(FileFragment())
            add(VideoFragment())
        }
        SubViewPagerF.adapter = viewPagerAdapter

        notesBtnF.setOnClickListener {
            SubViewPagerF.setCurrentItem(0)
        }
        fileBtnF.setOnClickListener {
            SubViewPagerF.setCurrentItem(1)
        }
        videoBtnF.setOnClickListener {
            SubViewPagerF.setCurrentItem(2)
        }
    }
}
