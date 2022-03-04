package com.example.mcsdkdemoapp.ui.activationuserid

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.mcsdkdemoapp.AppConstants
import com.example.mcsdkdemoapp.BaseFragment
import com.example.mcsdkdemoapp.R
import com.example.mcsdkdemoapp.databinding.ActivityLoginBinding
import com.example.mcsdkdemoapp.databinding.LoginFragmentBinding
import com.kobil.wrapper.events.UserIdentifier
import java.lang.Appendable

class LoginFragment : BaseFragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding
    private var userIds: java.util.ArrayList<UserIdentifier>? = null
    private var userId:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.login_fragment, container, false
        )
        return binding.root    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userIds = arguments?.let {
            it.getSerializable("userLists") as ArrayList<UserIdentifier>
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.login.setOnClickListener(this)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.login.setOnClickListener(this)
        setUpUserDropDown()


    }
    private fun setUpUserDropDown() {
        val userList: MutableList<String> = mutableListOf()
        userIds?.let {
            it.forEach { value ->
                userList.add(value.userId)
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter(
                baseActivity,
                R.layout.support_simple_spinner_dropdown_item,
                userList
            )
            binding.userIdLoginSpinner.setAdapter(adapter)

        }

        binding.userIdLoginSpinner.onItemSelectedListener =object :


            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                userId =  userList[position]
                AppConstants.selectedUser=userId
            }

        }
    }

     override fun onClick(v: View?) {
        super.onClick(v)

        when(v?.id){
            R.id.login ->

                startLogin(userId,binding.pinLogin.text.toString(),"kobil")
        }

    }


    private fun startLogin(userId: String, pin: String, tenantId: String) {

        mcHandler?.triggerLoginEvent(userId,pin,tenantId)

    }
}