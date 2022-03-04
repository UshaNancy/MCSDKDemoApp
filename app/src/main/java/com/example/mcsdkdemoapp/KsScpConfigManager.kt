package com.example.mcsdkdemoapp

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.kobil.mcwmpgettingstarted.extensions.isNotNullOrEmpty
import com.kobil.mcwmpgettingstarted.extensions.orElse
import com.kobil.mcwmpgettingstarted.extensions.readConfigFile

class KsScpConfigManager() {
    private val gson: Gson by lazy { Gson() }
    private var scpConfigJson: String? = null

    lateinit var scpConfigEntity: ScpConfigEntity

    constructor(jsonContent: String) : this() {
        init()
        this.scpConfigJson = jsonContent


    }

    constructor(context: Context) : this() {
        val configName = SessionManager.getInstance(context = context).getConfigName()
        this.scpConfigJson =
            readConfigFile(
                context,
                configName = configName,
                fileName = AppConstants.SCP_CONFIG_FILE
            )

        init()
    }

    fun init() {
        Log.e("init ","is called")
        if (scpConfigJson.isNotNullOrEmpty()) {
            if (!::scpConfigEntity.isInitialized) {

                scpConfigEntity = gson.fromJson(scpConfigJson, ScpConfigEntity::class.java)
            } else {
               // logDebug("scpConfigEntity was already initialized", "init", "ScpConfigManager")
            }
        } else {

            throw UnsupportedOperationException("Scp Config file contents were empty!")
        }
    }

    fun getScpConfigContent(): String = scpConfigJson.orEmpty()

    companion object {
        private var ksScpConfigManager: KsScpConfigManager? = null

        @JvmStatic
        fun getEntityInstance(): ScpConfigEntity {
            return ksScpConfigManager?.scpConfigEntity.orElse {
                throw   IllegalArgumentException("KsScpConfigManager must be call init on Application before using.");
            }
        }

        @JvmStatic
        fun getInstance(): KsScpConfigManager {
            return ksScpConfigManager?.orElse {
                throw   IllegalArgumentException("KsScpConfigManager must be call init on Application before using.");
            }!!
        }

        @JvmStatic
        fun init(context: Context) {
        //    logDebug("Called with ${context::class.java.simpleName}", "init", "KsScpConfigManager")
            ksScpConfigManager = KsScpConfigManager(context)
        }

        @JvmStatic
        fun init(jsonContent: String) {
           // logDebug("Called with scp_config.json content", "init", "KsScpConfigManager")
            ksScpConfigManager = KsScpConfigManager(jsonContent)
        }
    }

}
