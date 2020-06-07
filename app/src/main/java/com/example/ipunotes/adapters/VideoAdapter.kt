package com.example.ipunotes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ipunotes.R
import com.example.ipunotes.models.Video
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_item_view.view.*

class VideoAdapter(
    private val videoAdapterListeners: VideoAdapterListeners,
    private val list: List<Video>
) : RecyclerView.Adapter<VideoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder =
        VideoViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.video_item_view, parent, false)
        )

    override fun getItemCount(): Int {
        videoAdapterListeners.isContentEmpty(list.isEmpty())
        return list.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(video: Video) {
        with(itemView) {
            tvTitle.text = video.title
            val channelNameText = "Channel Name: ${video.channelName}"
            tvChannelName.text = channelNameText
            tvUrl.text = video.url
            Picasso.get().load(video.thumbnailUrl).into(ivThumbnail)
        }
    }
}

interface VideoAdapterListeners {
    fun isContentEmpty(value: Boolean)
}