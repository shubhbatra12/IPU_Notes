package com.example.ipunotes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ipunotes.Extras
import com.example.ipunotes.R
import com.example.ipunotes.models.Subject
import kotlinx.android.synthetic.main.subject_item_view.view.*

private const val TYPE_SELECTED_SUBJECT = 123
private const val TYPE_NOT_SELECTED_SUBJECT = 456

class SubjectSelectionAdapter(
    private val selectedSubjectList: List<Subject>,
    private val notSelectedSubjectsList: List<Subject>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SELECTED_SUBJECT -> {
                SelectedSubjectViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.selected_subject_item_view, parent, false)
                )
            }
            else -> {
                NotSelectedSubjectViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.not_selected_subject_item_view, parent, false)
                )
            }
        }
    }

    override fun getItemCount(): Int = selectedSubjectList.size + notSelectedSubjectsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SelectedSubjectViewHolder) {
            (holder as SelectedSubjectViewHolder).bind(selectedSubjectList[position])
        } else {
            (holder as NotSelectedSubjectViewHolder).bind(notSelectedSubjectsList[position - selectedSubjectList.size])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < selectedSubjectList.size) {
            TYPE_SELECTED_SUBJECT
        } else {
            TYPE_NOT_SELECTED_SUBJECT
        }
    }
}

class SelectedSubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(subject: Subject) {
        with(itemView){
            tvSubjectName.text = subject.name
        }
    }
}

class NotSelectedSubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(subject: Subject) {
        with(itemView){
            tvSubjectName.text = subject.name
        }
    }
}