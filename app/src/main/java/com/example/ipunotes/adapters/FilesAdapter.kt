package com.example.ipunotes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ipunotes.Extras
import com.example.ipunotes.R
import com.example.ipunotes.models.File
import kotlinx.android.synthetic.main.file_item_view.view.*

class NotesAdapter(private val list: List<File>) : RecyclerView.Adapter<NotesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder =
        NotesViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.file_item_view, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
            holder.bind(list[position])
    }

}

class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(note: File){
        with(itemView){
            tvFileName.text = note.name
            val authorText = "Author: ${note.author}"
            tvAuthor.text = authorText
            val uploadDate = "Upload Date: ${note.upload_date}"
            tvUploadDate.text = uploadDate
            tvLabel.text = Extras.getLabel(note.name)
        }
    }
}