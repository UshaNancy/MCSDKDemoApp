package com.example.mcsdkdemoapp.ui.activationuserid

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.mcsdkdemoapp.AppConstants
import com.example.mcsdkdemoapp.BaseFragment
import com.example.mcsdkdemoapp.R
import com.example.mcsdkdemoapp.UserModel
import com.example.mcsdkdemoapp.databinding.ActivationSetPinFragmentBinding
import com.example.mcsdkdemoapp.databinding.ActivationUserIdFragmentBinding

class ActivationSetPinFragment : BaseFragment() {

    companion object {
        fun newInstance() = ActivationSetPinFragment()
    }

    private lateinit var viewModel: ActivationSetPinViewModel
    private lateinit var binding: ActivationSetPinFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.activation_set_pin_fragment, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivationSetPinViewModel::class.java)
        binding.activateButton.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.activate_button -> {

                startActivation(binding.activationSetPin.text.toString(), false)
            }
        }
    }

    private fun startActivation(actPin: String, enablePin: Boolean) {

        if (AppConstants.UserLoggedIN) {
            mcHandler?.triggerProvideSetPinEventAddUser(actPin, enablePin)
        } else {
            mcHandler?.triggerProvideSetPinEvent(actPin, enablePin)
            AppConstants.Password =actPin


        }
    }

}