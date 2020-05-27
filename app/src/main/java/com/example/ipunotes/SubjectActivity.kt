package com.example.ipunotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import com.example.ipunotes.Fragments.FileFragment
import com.example.ipunotes.Fragments.NotesFragment
import com.example.ipunotes.Fragments.VideoFragment
import kotlinx.android.synthetic.main.activity_subject.*

class SubjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.apply {
            add(NotesFragment())
            add(FileFragment())
            add(VideoFragment())
        }
        SubViewPager.adapter = viewPagerAdapter

        notesBtn.setOnClickListener {
            SubViewPager.setCurrentItem(0)
        }
        fileBtn.setOnClickListener {
            SubViewPager.setCurrentItem(1)
        }
        videoBtn.setOnClickListener {
            SubViewPager.setCurrentItem(2)
        }
    }
}
