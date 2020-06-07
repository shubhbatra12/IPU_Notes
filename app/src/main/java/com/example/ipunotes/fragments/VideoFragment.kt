package com.example.ipunotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ipunotes.AppViewModel
import com.example.ipunotes.Extras
import com.example.ipunotes.R
import com.example.ipunotes.adapters.VideoAdapter
import com.example.ipunotes.adapters.VideoAdapterListeners
import com.example.ipunotes.models.Video
import kotlinx.android.synthetic.main.fragment_video.*


class VideoFragment : Fragment(), VideoAdapterListeners {

    private val viewModel by lazy {
        ViewModelProvider(Extras.myApp).get(AppViewModel::class.java)
    }
    private val videosList = ArrayList<Video>()
    private val adapter = VideoAdapter(this, videosList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvVideos.adapter = adapter
    }

    fun refreshList() {
        videosList.clear()
        videosList.addAll(viewModel.getVideosList())
        adapter.notifyDataSetChanged()
    }

    override fun isContentEmpty(value: Boolean) {
        if (value) {
            rvVideos.visibility = View.GONE
            messageView.visibility = View.VISIBLE
        } else {
            rvVideos.visibility = View.VISIBLE
            messageView.visibility = View.GONE
        }
    }


}
