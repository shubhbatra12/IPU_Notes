package com.example.ipunotes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ipunotes.R
import com.example.ipunotes.models.Subject
import kotlinx.android.synthetic.main.subject_item_view.view.*

private const val TYPE_SELECTED_SUBJECT = 123
private const val TYPE_NOT_SELECTED_SUBJECT = 456

class SubjectSelectionAdapter(
    private val onSubjectSelectionChangedListener: OnSubjectSelectionChangedListener,
    private val selectedSubjectsList: List<Subject>,
    private val notSelectedSubjectsList: List<Subject>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SELECTED_SUBJECT -> {
                SelectedSubjectViewHolder(
                    onSubjectSelectionChangedListener,
                    selectedSubjectsList,
                    notSelectedSubjectsList.size,
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.selected_subject_item_view, parent, false)
                )
            }
            else -> {
                NotSelectedSubjectViewHolder(
                    onSubjectSelectionChangedListener,
                    notSelectedSubjectsList,
                    selectedSubjectsList.size,
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.not_selected_subject_item_view, parent, false)
                )
            }
        }
    }

    override fun getItemCount(): Int = selectedSubjectsList.size + notSelectedSubjectsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SelectedSubjectViewHolder) {
            (holder as SelectedSubjectViewHolder).bind(selectedSubjectsList[position])
        } else {
            (holder as NotSelectedSubjectViewHolder).bind(notSelectedSubjectsList[position - selectedSubjectsList.size])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < selectedSubjectsList.size) {
            TYPE_SELECTED_SUBJECT
        } else {
            TYPE_NOT_SELECTED_SUBJECT
        }
    }

}

class SelectedSubjectViewHolder(
    private val onSubjectSelectionChangedListener: OnSubjectSelectionChangedListener,
    private val selectedSubjectList: List<Subject>,
    private val notSelectedListSize: Int,
    itemView: View
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    fun bind(subject: Subject) {
        with(itemView) {
            tvSubjectName.text = subject.name
            setOnClickListener(this@SelectedSubjectViewHolder)
        }
    }

    override fun onClick(v: View?) {
        onSubjectSelectionChangedListener.onSubjectClick(
            selectedSubjectList[adapterPosition - notSelectedListSize],
            false
        )
    }
}

class NotSelectedSubjectViewHolder(
    private val onSubjectSelectionChangedListener: OnSubjectSelectionChangedListener,
    private val notSelectedSubjectsList: List<Subject>,
    private val selectedListSize: Int,
    itemView: View
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    fun bind(subject: Subject) {
        with(itemView) {
            tvSubjectName.text = subject.name
            setOnClickListener(this@NotSelectedSubjectViewHolder)
        }
    }

    override fun onClick(v: View?) {
        onSubjectSelectionChangedListener.onSubjectClick(
            notSelectedSubjectsList[adapterPosition - selectedListSize],
            true
        )
    }
}

interface OnSubjectSelectionChangedListener {
    fun onSubjectClick(subject: Subject, isSelected: Boolean)
}