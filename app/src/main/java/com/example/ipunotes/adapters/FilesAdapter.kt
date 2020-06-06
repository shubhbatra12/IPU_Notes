package com.example.ipunotes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ipunotes.Extras
import com.example.ipunotes.R
import com.example.ipunotes.models.File
import kotlinx.android.synthetic.main.file_item_view.view.*

class FilesAdapter(
    private val filesAdapterListeners: FilesAdapterListeners,
    private val list: List<File>
) : RecyclerView.Adapter<FileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder =
        FileViewHolder(
            filesAdapterListeners,
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.file_item_view, parent, false)
        )

    override fun getItemCount(): Int {
        filesAdapterListeners.isContentEmpty(list.isEmpty())
        return list.size
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(list[position])
    }

}

class FileViewHolder(private val filesAdapterListeners: FilesAdapterListeners, itemView: View) :
    RecyclerView.ViewHolder(itemView),View.OnClickListener {
    fun bind(note: File) {
        with(itemView) {
            tvFileName.text = note.name
            val authorText = "Author: ${note.author}"
            tvAuthor.text = authorText
            val uploadDate = "Upload Date: ${note.upload_date}"
            tvUploadDate.text = uploadDate
            tvLabel.text = Extras.getLabel(note.name)

            setOnClickListener(this@FileViewHolder)
        }
    }

    override fun onClick(v: View?) {
        filesAdapterListeners.onFileClick(adapterPosition)
    }
}

interface FilesAdapterListeners {
    fun onFileClick(position: Int)
    fun isContentEmpty(value: Boolean)
}