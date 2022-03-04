package com.example.mcsdkdemoapp

object AppConstants {

    var CurrentUser: String? = ""
    const val TENANT_ID = "kobil"
    const val LOCALE = "en"
    const val APP_NAME = "MCSDKDemoApp"
    var User  = ""
    var selectedUser  = ""
    var Password  = ""
    var firstActivation = false
    var deviceNameSet = false


    /*APP VERSION*/
    const val VERSION_MAJOR = 1
    const val VERSION_MINOR = 0
    const val VERSION_BUILD = 0

    const val SHARED_PREF_NAME = AppConstants.APP_NAME
    const val DEVICE_NAME = "device_name"
    const val USER_IDENTIFIER = "user_identifier"

    const val START_TIME = "start_time"
    const val END_TIME = "end_time"
    const val CONFIG_NAME = "config_name"

    // config file names
    const val SDK_CONFIG_FILE = "MS09/sdk_config.xml"
    const val APP_CONFIG_FILE = "app_config.json"
    const val SCP_CONFIG_FILE = "scp_config.json"
    const val LICENSE_INFO_FILE = "LICENSE_KOBIL_en.txt"

    var UPDATE_URL = ""
    var UPDATE_STATUS= " "

    var UserLoggedIN = false
}
