package com.example.mcsdkdemoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import com.example.mcsdkdemoapp.databinding.ActivityActivationUserIDBinding

class ActivationUserID : BaseActivity() {
    private lateinit var binding: ActivityActivationUserIDBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_activation_user_i_d)

        binding.button1.setOnClickListener (this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.button1 ->

                passActCodeandUserID(binding.userIdActivation.text.toString(),binding.activCodeActivation.text.toString(),"kobil")
        }

    }

    private fun passActCodeandUserID(userId: String, actCode: String, tenantId: String) {

        if(AppConstants.UserLoggedIN){

            mcHandler?.triggerStartAddUserEvent()
        }else {
            mcHandler?.triggerProvideActivationCodeandUserID(userId, actCode, tenantId)
        }
    }


}