package com.example.mcsdkdemoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.mcsdkdemoapp.databinding.ActivityActivationSetPinBinding
import com.example.mcsdkdemoapp.databinding.ActivityActivationUserIDBinding

class ActivationSetPinActivity : BaseActivity() {
    private lateinit var binding: ActivityActivationSetPinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_activation_set_pin)

        binding.activateButton.setOnClickListener (this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.activate_button->{

                startActivation(binding.activationSetPin.text.toString(),false)
            }
        }
    }

    private fun startActivation(actPin: String, enablePin: Boolean) {

        mcHandler?.triggerProvideSetPinEvent(actPin,enablePin)
    }
}