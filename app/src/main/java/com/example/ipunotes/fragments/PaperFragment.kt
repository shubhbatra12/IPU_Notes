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
import com.example.ipunotes.adapters.FilesAdapter
import com.example.ipunotes.adapters.FilesAdapterListeners
import com.example.ipunotes.models.File
import kotlinx.android.synthetic.main.fragment_paper.*

class PaperFragment : Fragment(), FilesAdapterListeners {

    private val viewModel by lazy {
        ViewModelProvider(Extras.myApp).get(AppViewModel::class.java)
    }
    private val examsList = ArrayList<File>()
    private val adapter = FilesAdapter(this, examsList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_paper, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPaper.adapter = adapter
    }

    fun refreshList() {
        examsList.clear()
        examsList.addAll(viewModel.getExamsList())
        adapter.notifyDataSetChanged()
    }


    override fun onFileClick(position: Int) {

    }

    override fun isContentEmpty(value: Boolean) {
        if (value) {
            rvPaper.visibility = View.GONE
            messageView.visibility = View.VISIBLE
        } else {
            rvPaper.visibility = View.VISIBLE
            messageView.visibility = View.GONE
        }
    }
}
