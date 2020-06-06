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
import kotlinx.android.synthetic.main.fragment_file.*

class FileFragment : Fragment(), FilesAdapterListeners {

    private val viewModel by lazy {
        ViewModelProvider(Extras.myApp).get(AppViewModel::class.java)
    }
    private val practicalFilesList = ArrayList<File>()
    private val adapter = FilesAdapter(this, practicalFilesList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPracFiles.adapter = adapter
    }

    fun refreshList() {
        practicalFilesList.clear()
        practicalFilesList.addAll(viewModel.getPracticalFilesList())
        adapter.notifyDataSetChanged()
    }

    override fun onFileClick(position: Int) {

    }

    override fun isContentEmpty(value: Boolean) {
        if (value) {
            rvPracFiles.visibility = View.GONE
            messageView.visibility = View.VISIBLE
        } else {
            rvPracFiles.visibility = View.VISIBLE
            messageView.visibility = View.GONE
        }
    }


}
