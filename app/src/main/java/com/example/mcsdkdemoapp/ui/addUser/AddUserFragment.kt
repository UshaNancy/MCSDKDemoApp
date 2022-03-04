package com.example.mcsdkdemoapp.ui.addUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mcsdkdemoapp.R

class AddUserFragment : Fragment() {

    private lateinit var addUserViewModel: AddUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addUserViewModel =
            ViewModelProvider(this).get(AddUserViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_user, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        addUserViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}