package com.kobil.mcwmpgettingstarted.appconfig

import android.content.Context
import com.example.mcsdkdemoapp.AppConstants
import com.example.mcsdkdemoapp.SessionManager
import com.google.gson.Gson
import com.kobil.mcwmpgettingstarted.appconfig.model.AppConfigEntity
import com.kobil.mcwmpgettingstarted.extensions.*


class KsAppConfigManager() {
    private val gson: Gson by lazy { Gson() }
    private var appConfigJson: String? = null

    lateinit var appConfigEntity: AppConfigEntity

    constructor(jsonContent: String) : this() {
        this.appConfigJson = jsonContent
        init()
    }

    constructor(context: Context) : this() {
        val configName = SessionManager.getInstance(context = context).getConfigName()
        this.appConfigJson = readConfigFile(
            context = context,
            configName = "MS09",
            fileName = AppConstants.APP_CONFIG_FILE
        )

        init()
    }

    fun init() {
        if (appConfigJson.isNotNullOrEmpty()) {
            if (!::appConfigEntity.isInitialized) {
               appConfigEntity = gson.fromJson(appConfigJson, AppConfigEntity::class.java)
            } else {
            }
        } else {

            throw UnsupportedOperationException("App Config file contents were empty!")
        }
    }

    fun getAppJsonConfigContent(): String = appConfigJson.orEmpty()

    companion object {
        private var ksAppConfigManager: KsAppConfigManager? = null

        @JvmStatic
        fun getEntityInstance(): AppConfigEntity {
            return ksAppConfigManager?.appConfigEntity.orElse {
                throw   IllegalArgumentException("AppConfigManager must be call init on Application before using.");
            }
        }

        @JvmStatic
        fun getInstance(): KsAppConfigManager {
            return ksAppConfigManager?.orElse {
                throw   IllegalArgumentException("AppConfigManager must be call init on Application before using.");
            }!!
        }

        @JvmStatic
        fun init(context: Context) {
            ksAppConfigManager = KsAppConfigManager(context)
        }

        @JvmStatic
        fun init(jsonContent: String) {
            ksAppConfigManager = KsAppConfigManager(jsonContent)
        }
    }
}