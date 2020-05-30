package com.example.ipunotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.ipunotes.R
import com.example.ipunotes.adapters.ViewPagerAdapter
import com.example.ipunotes.models.Subject
import kotlinx.android.synthetic.main.fragment_subject.*


class SubjectFragment(private val subject: Subject = Subject("")) : Fragment() {


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

        val viewPagerAdapter = getFragmentManager()?.let {
            ViewPagerAdapter(
                it
            )
        }
        viewPagerAdapter?.apply {
            add(NotesFragment())
            add(FileFragment())
            add(VideoFragment())
        }
        SubViewPagerF.adapter = viewPagerAdapter

        bottomNavBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.notesBtnF -> {
                    SubViewPagerF.setCurrentItem(0)
                    true
                }
                R.id.fileBtnF -> {
                    SubViewPagerF.setCurrentItem(1)
                    true
                }
                R.id.videoBtnF -> {
                    SubViewPagerF.setCurrentItem(2)
                    true
                }
                else -> false
            }
        }

        SubViewPagerF.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            var prevMenuItem: MenuItem? = null
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if(prevMenuItem!=null){
                    prevMenuItem!!.isChecked = false
                }else{
                    bottomNavBar.menu.getItem(0).isChecked = false
                }
                bottomNavBar.menu.getItem(position).isChecked= true
                prevMenuItem = bottomNavBar.menu.getItem(position)
            }

        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = subject.name

    }
}
