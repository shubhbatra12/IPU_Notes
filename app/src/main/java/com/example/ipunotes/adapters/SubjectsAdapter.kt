package com.example.ipunotes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ipunotes.R
import com.example.ipunotes.fragments.MainFragment
import com.example.ipunotes.models.Subject
import kotlinx.android.synthetic.main.subject_item_view.view.*

class SubjectsAdapter(
    private val onSubjectClickListener: MainFragment,
    private val list: List<Subject>
) : RecyclerView.Adapter<SubjectsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectsViewHolder =
        SubjectsViewHolder(
            onSubjectClickListener,
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.subject_item_view, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SubjectsViewHolder, position: Int) {
        holder.bind(list[position])
    }



}

class SubjectsViewHolder(
    private val onSubjectClickListener: OnSubjectClickListener,
    itemView: View
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    fun bind(subject: Subject) {
        val subjectName = subject.name
        var label = subjectName.substring(0, 1)
        val words = subjectName.split(" ")
        if (words.size > 1) {
            label += words[1].substring(0, 1)
        }
        with(itemView) {
            tvSubjectName.text = subjectName
            tvSubjectLabel.text = label
            setOnClickListener(this@SubjectsViewHolder)
        }
    }

    override fun onClick(v: View?) {
        onSubjectClickListener.onSubjectClick(adapterPosition)
    }

}

interface OnSubjectClickListener {
    fun onSubjectClick(position: Int)
}