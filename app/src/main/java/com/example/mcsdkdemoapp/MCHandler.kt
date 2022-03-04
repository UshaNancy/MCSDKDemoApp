package com.example.mcsdkdemoapp

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.kobil.mcwmpgettingstarted.appconfig.KsAppConfigManager
import com.kobil.mcwmpgettingstarted.extensions.orElse
import com.kobil.mcwmpgettingstarted.util.launchIO
import com.kobil.wrapper.EventResultHandler
import com.kobil.wrapper.Future
import com.kobil.wrapper.SynchronousEventHandler
import com.kobil.wrapper.events.*
import java.io.File
import java.io.IOException

class MCHandler private constructor(
    private val context: Context
) : MCWrapper.MCWrapperListener {

    /**
     * Holds MCEventObserver to notify about Master Controller events
     */
    private var observers: MutableList<MCEventObserver> = mutableListOf()

    companion object {
        private var instance: MCHandler? = null
        private var mcWrapper: MCWrapperInterface? = null

        fun getInstance(context: Context): MCHandler {
            synchronized(this) {
                if (instance == null) {
                    instance = MCHandler(context)
                }
                if(mcWrapper ==null){
                    mcWrapper=MCWrapper(context, instance!!)
                }
                return instance!!
            }

        }
        fun setMCWrapperInstance(mcWrapper: MCWrapperInterface) {
            this.mcWrapper = mcWrapper
        }

    }

    @Synchronized
    override fun executeEvent(event: EventFrameworkEvent) {

        if (observers.isNotEmpty()) {
            observers.forEach { observer ->
                event?.let {
                    observer.onEventReceived(it)
                    Log.e("event",it.toString())

                }
            }
        }
    }

    @Synchronized
    fun addMCEventObserver(o: MCEventObserver) {
        observers.add(o)
    }
    @Synchronized
    fun removeMCEventObserver(o: MCEventObserver){
        observers.remove(o)
    }

    fun triggerStartEvent(){
        Log.e("trigger start event"," is invoked")
        val sdkConfig = getSDKConfig()
        sdkConfig?.let {
            KsScpConfigManager .init(context)
            KsAppConfigManager.init(context)

            val scpConfig = KsScpConfigManager.getEntityInstance()

            val startEvent = StartEvent(
                AppConstants.LOCALE,
                AppVersion(
                    AppConstants.VERSION_MAJOR,
                    AppConstants.VERSION_MINOR,
                    AppConstants.VERSION_BUILD
                ),
                AppConstants.APP_NAME,
                scpConfig.ecoTokenIssuerServiceUrl,
                scpConfig.ecoAddressBookServiceUrl,
                scpConfig.ecoMessageRouterUrl,
                scpConfig.ecoPresenceServiceUrl,
                scpConfig.ecoId,
                scpConfig.ecoTrustedSSLServerCerts,
                it
            )
            forwardEvent(startEvent)

           /* launchIO {
                SessionManager.getInstance(context).setStartTime(System.currentTimeMillis())
            }*/
        }
        
    }

    private fun getSDKConfig(): String? {
        var sdkConfig: String? = null
        try {

            val configName = SessionManager.getInstance(context).getConfigName()
            val inputStream =
                context.assets.open("${AppConstants.SDK_CONFIG_FILE}")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            sdkConfig = String(buffer)
        } catch (e: IOException) {
Log.e("exception",e.toString())
        }
        return sdkConfig

    }
    fun forwardEvent(event: EventFrameworkEvent) {
        mcWrapper?.forwardEvent(event)
    }

    fun postEvent(event: EventFrameworkEvent): Future {
       return mcWrapper?.postEvent(event)!!
    }

    fun triggerProvideActivationCodeandUserID(userId: String,
                                              activationCode: String,
                                              tenantId: String){

        val provideActivationCodeAndUserId =
            ProvideActivationCodeAndUserIdEvent(
                activationCode,
                UserIdentifier(tenantId, userId)
            )

            forwardEvent(provideActivationCodeAndUserId)

    }

    fun triggerProvideSetPinEvent(
        pin: String,
        isEnableLogin: Boolean = false
    ){
         val provideActivationSetPinEvent = ProvideSetPinEvent(pin,isEnableLogin)

        forwardEvent(provideActivationSetPinEvent)
    }

    fun triggerLoginEvent(userId: String, pin: String, tenantId: String){

        val provideLoginEvent = LoginEvent(UserIdentifier(tenantId, userId),pin)

        forwardEvent(provideLoginEvent)
    }


    fun triggerSetDeviceNameEvent(deviceName:String){
        val setDeviceNameEvent = SetDeviceNameEvent(deviceName)
        postEvent(setDeviceNameEvent)?.then {
            executeEvent(it)
        }
    }

    fun triggerStartAddUserEvent() {
        launchIO {
            forwardEvent(StartAddUserEvent())
        }
    }

    fun triggerRestartEvent(eventResultHandler: EventResultHandler?) {

        val restartEvent = RestartEvent(
            AppConstants.LOCALE,
            AppVersion(
                AppConstants.VERSION_MAJOR,
                AppConstants.VERSION_MINOR,
                AppConstants.VERSION_BUILD
            ),
            AppConstants.APP_NAME
        )
        launchIO {
            eventResultHandler?.let { postEvent(restartEvent).then(eventResultHandler) }
                .orElse { postEvent(restartEvent) }
        }

    }
    fun triggerProvideActivationCodeandUserIDAddUser(userId: String,
                                              activationCode: String,
                                              tenantId: String){

        val provideActivationCodeAndUserIdAddUser =
            ProvideActivationCodeAndUserIdForAddUserEvent(
                activationCode,
                UserIdentifier(tenantId, userId)
            )

        forwardEvent(provideActivationCodeAndUserIdAddUser)

    }

    fun triggerProvideSetPinEventAddUser(actPin: String, enablePin: Boolean) {

        val provideActivationSetPinEventAddUSer = ProvideSetPinForAddUserEvent(actPin,enablePin)

        forwardEvent(provideActivationSetPinEventAddUSer)
    }

    fun triggerCancelEvent() {
forwardEvent(CancelEvent())
    }

    fun triggerStartDeleteUserEvent() {

        val startDeleteUserEvent = StartDeleteUserEvent()
        launchIO {
            forwardEvent(startDeleteUserEvent)
        }

    }
    fun triggerProvideSetUserIdToDeleteEvent(userId: String, tenantId: String) {

        val provideSetUserIdToDeleteEvent =
            ProvideSetUserIdToDeleteEvent(UserIdentifier(tenantId, userId))
        launchIO {
            forwardEvent(provideSetUserIdToDeleteEvent)
        }
    }

    fun triggerUpdateEvent(){
        forwardEvent(UpdateEvent())
    }

    fun triggerSetUserIdtoDeleteEvent(currentUser: String?) {

        forwardEvent(ProvideSetUserIdToDeleteEvent(UserIdentifier(AppConstants.TENANT_ID,currentUser)))

    }

    fun triggerGetDeviceInfo(currentUser: String?){

        val getDeviceInformationEvent = GetDeviceInformationEvent(
            UserIdentifier("kobil", currentUser)
        )
        launchIO {
            val deviceInfoResult =
                postEvent(getDeviceInformationEvent).get() as GetDeviceInformationResultEvent

            executeEvent(deviceInfoResult)
    }}

    fun triggerSetPushToken(pushToken: String) {

        postEvent(SetPushTokenEvent(pushToken)).then {

            executeEvent(it)
        }
    }
    fun triggerStartChangePinEvent() {

        launchIO {
            forwardEvent(StartChangePinEvent())
        }
    }
    fun triggerProvideSetNewPinEvent(currentPin: String, newPin: String) {


        val setNewPinEvent = ProvideSetNewPinEvent(currentPin, newPin)
        launchIO {
            forwardEvent(setNewPinEvent)
        }
    }

    fun triggerStartTransactionEvent() {

        launchIO {
            forwardEvent(StartTransactionEvent())
        }
    }

    fun triggerStartDisplayMessageEvent() {


    }

    fun triggerDisplayConfirmationEvent(confirmationType: ConfirmationType,displayData:String){
        launchIO {
            forwardEvent(DisplayConfirmationEvent(confirmationType,displayData))
        }
    }

    fun triggerTransPinEvent(confirmationType: ConfirmationType,pin: String){

        launchIO {
            forwardEvent(ProvidePinEvent(confirmationType,pin))
        }
    }
}

