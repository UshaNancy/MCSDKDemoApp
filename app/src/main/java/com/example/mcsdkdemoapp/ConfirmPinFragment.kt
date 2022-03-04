package com.example.mcsdkdemoapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ConfirmPinFragment : Fragment() {

    companion object {
        fun newInstance() = ConfirmPinFragment()
    }

    private lateinit var viewModel: ConfirmPinViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.confirm_pin_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConfirmPinViewModel::class.java)
        // TODO: Use the ViewModel
    }

}