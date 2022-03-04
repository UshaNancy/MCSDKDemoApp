package com.kobil.mcwmpgettingstarted.util

import android.content.Context
import android.content.SharedPreferences
import com.example.mcsdkdemoapp.AppConstants

class PreferenceUtil(private val context: Context) {
    private val mSpref: SharedPreferences = context.getSharedPreferences(
        AppConstants.SHARED_PREF_NAME,
        Context.MODE_PRIVATE
    )
    private val TAG = PreferenceUtil::class.java.simpleName

    fun getIntData(key: String?): Int {
        return mSpref.getInt(key, 0)
    }

    fun setFloatData(
        context: Context?,
        key: String?,
        value: Float
    ) {
        val appInstallInfoEditor = mSpref.edit()
        appInstallInfoEditor.putFloat(key, value)
        appInstallInfoEditor.apply()
    }

    fun setIntData(key: String?, value: Int) {
        val appInstallInfoEditor = mSpref.edit()
        appInstallInfoEditor.putInt(key, value)
        appInstallInfoEditor.apply()
    }

    fun setStringData(key: String?, value: String?) {
        val appInstallInfoEditor = mSpref.edit()
        appInstallInfoEditor.putString(key, value)
        appInstallInfoEditor.apply()
    }

    fun getBoolean(key: String?): Boolean {
        return mSpref.getBoolean(key, false)
    }

    fun getStringData(key: String?): String? {
        return mSpref.getString(key, "")
    }

    fun getStringDataFilterCount(key: String?): String? {
        return mSpref.getString(key, "0")
    }

    fun setBooleanData(key: String?, value: Boolean) {
        val appInstallInfoEditor = mSpref.edit()
        appInstallInfoEditor.putBoolean(key, value)
        appInstallInfoEditor.apply()
    }

    fun getLongValue(key: String?): Long {
        if (mSpref.contains(key)) return mSpref.getLong(key, 0L) else {

        }
        return 0L
    }

    fun setLongData(key: String?, value: Long) {
        val editor = mSpref.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun clear() {
        mSpref.edit().clear().apply()
    }

}