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
import com.example.mcsdkdemoapp.databinding.ActivationUserIdFragmentBinding
import com.example.mcsdkdemoapp.databinding.ActivityActivationUserIDBinding

class ActivationUserIdFragment : BaseFragment() {

    companion object {
        fun newInstance() = ActivationUserIdFragment()
    }

    private lateinit var viewModel: ActivationUserIdViewModel

    private lateinit var binding: ActivationUserIdFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.activation_user_id_fragment, container, false
        )
        return  binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivationUserIdViewModel::class.java)
        binding.button1.setOnClickListener (this)

    }
    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            binding.button1.id ->
                passActCodeandUserID(binding.userIdActivation.text.toString(),binding.activCodeActivation.text.toString(),"kobil")
        }

    }

    private fun passActCodeandUserID(userId: String, actCode: String, tenantId: String) {

        if(AppConstants.UserLoggedIN){

            mcHandler?.triggerProvideActivationCodeandUserIDAddUser(userId,actCode,tenantId)
        }else {
            mcHandler?.triggerProvideActivationCodeandUserID(userId, actCode, tenantId)
            AppConstants.User = userId

        }

    }

}