package com.example.mcsdkdemoapp

import android.content.Context
import com.google.gson.Gson
import com.kobil.wrapper.events.UserIdentifier

class SessionManager private constructor(private val context: Context) {

    companion object {
        private var instance: SessionManager? = null
        private var pref: PreferenceUtil? = null

        fun getInstance(context: Context): SessionManager {
            synchronized(this) {
                if (instance == null) {
                    instance = SessionManager(context)
                    pref = PreferenceUtil(context)
                }
                return instance!!
            }
        }
    }

    fun setDeviceName(deviceName: String?) {
        pref?.setStringData(AppConstants.DEVICE_NAME, deviceName)
    }


    fun getDeviceName(): String? {
        return pref?.getStringData(AppConstants.DEVICE_NAME)
    }

    fun setUserIdentifier(userIdentifier: UserIdentifier) {
        val gson = Gson()
        val identifierString = gson.toJson(userIdentifier)
        pref?.setStringData(AppConstants.USER_IDENTIFIER, identifierString)
    }

    fun getUserIdentifier(): UserIdentifier {
        val gson = Gson()
        val identifierString = pref?.getStringData(AppConstants.USER_IDENTIFIER)
        return gson.fromJson(identifierString, UserIdentifier::class.java)
    }

    fun clear() {
        pref?.clear()
    }

    fun setStartTime(startTime: Long) {
        pref?.setLongData(AppConstants.START_TIME, startTime)
    }


    fun getStartTime(): Long {
        return pref?.getLongValue(AppConstants.START_TIME)!!
    }

    fun setEndTime(endTime: Long) {
        pref?.setLongData(AppConstants.END_TIME, endTime)
    }


    fun getEndTime(): Long {
        return pref?.getLongValue(AppConstants.END_TIME)!!
    }

    fun setConfig(configName: String) {
        pref?.setStringData(AppConstants.CONFIG_NAME, configName)
    }

    fun getConfigName(): String {
        return pref?.getStringData(AppConstants.CONFIG_NAME).toString()
    }
}

