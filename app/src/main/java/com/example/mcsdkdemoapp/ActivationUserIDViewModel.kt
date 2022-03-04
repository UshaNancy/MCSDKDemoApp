package com.example.mcsdkdemoapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivationUserIDViewModel:ViewModel() {

    val userId: MutableLiveData<String> = MutableLiveData()
    val activationCode: MutableLiveData<String> = MutableLiveData()

    init {
        userId.value = ""
        activationCode.value = ""
    }

}