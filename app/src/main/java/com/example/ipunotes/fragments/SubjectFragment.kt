package com.example.ipunotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.ipunotes.AppViewModel
import com.example.ipunotes.Extras
import com.example.ipunotes.R
import com.example.ipunotes.adapters.ViewPagerAdapter
import com.example.ipunotes.models.Subject
import kotlinx.android.synthetic.main.fragment_subject.*


class SubjectFragment(private val subject: Subject = Subject("", "")) : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            Extras.myApp,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(AppViewModel::class.java)
    }
    private val notesFragment = NotesFragment()
    private val fileFragment = FileFragment()
    private val paperFragment = PaperFragment()
    private val videoFragment = VideoFragment()

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
            add(notesFragment)
            add(fileFragment)
            add(paperFragment)
            add(videoFragment)
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
                R.id.paperBtnF -> {
                    SubViewPagerF.setCurrentItem(2)
                    true
                }
                R.id.videoBtnF -> {
                    SubViewPagerF.setCurrentItem(3)
                    true
                }
                else -> false
            }
        }

        SubViewPagerF.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
                if (prevMenuItem != null) {
                    prevMenuItem!!.isChecked = false
                } else {
                    bottomNavBar.menu.getItem(0).isChecked = false
                }
                bottomNavBar.menu.getItem(position).isChecked = true
                prevMenuItem = bottomNavBar.menu.getItem(position)
            }

        })
        viewModel.loadSubjectContents(subject.id)
        viewModel.subjectContentsUpdating.observe(viewLifecycleOwner, Observer {
            if (!it) {
                bottomNavBar.visibility = View.VISIBLE
                bottomNavBar.startAnimation(
                    AnimationUtils.loadAnimation(
                        activity,
                        R.anim.slide_in_bottom
                    )
                )
                notesFragment.refreshList()
                paperFragment.refreshList()
                fileFragment.refreshList()
                videoFragment.refreshList()
//                viewModel.subjectContentsUpdating.removeObservers(viewLifecycleOwner)
            }else{
                bottomNavBar.visibility = View.GONE
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = subject.name

    }
}
