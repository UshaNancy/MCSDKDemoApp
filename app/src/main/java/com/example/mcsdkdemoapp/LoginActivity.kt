package com.example.mcsdkdemoapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.mcsdkdemoapp.databinding.ActivityLoginBinding
import com.kobil.wrapper.events.UserIdentifier

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var userIds: java.util.ArrayList<UserIdentifier>? = ArrayList()
    private var userList : String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        val bundle = intent.extras

        if (bundle != null) {
            userList= bundle.getString("userList")!!
        }
        binding.userIdLogin.setText(userList)
        binding.loginButton.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.login_button ->
                startLogin(binding.userIdLogin.text.toString(),binding.pinLogin.text.toString(),"kobil")
        }

    }

    private fun startLogin(userId: String, pin: String, tenantId: String) {

        mcHandler?.triggerLoginEvent(userId,pin,tenantId)

    }

}