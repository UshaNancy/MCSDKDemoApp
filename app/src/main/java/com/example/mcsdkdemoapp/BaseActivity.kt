package com.example.mcsdkdemoapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mcsdkdemoapp.ui.activationuserid.ActivationSetPinFragment
import com.example.mcsdkdemoapp.ui.activationuserid.ActivationUserIdFragment
import com.example.mcsdkdemoapp.ui.activationuserid.LoginFragment
import com.example.mcsdkdemoapp.ui.home.HomeFragment
import com.google.firebase.messaging.FirebaseMessaging
import com.kobil.wrapper.events.*


open class BaseActivity : AppCompatActivity(), View.OnClickListener, MCEventObserver {

    protected var mcHandler: MCHandler? = null
    private var deviceNameSet: Boolean = false
    var userId: ArrayList<UserIdentifier>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mcHandler = MCHandler.getInstance(this)
    }


    override fun onStart() {
        super.onStart()
        mcHandler?.addMCEventObserver(this)
    }

    override fun onStop() {
        super.onStop()
        // mcHandler?.removeMCEventObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mcHandler?.removeMCEventObserver(this)
    }

    override fun onClick(v: View?) {

    }

    @SuppressLint("LongLogTag")
    override fun onEventReceived(eventFrameworkEvent: EventFrameworkEvent) {
        Log.e("onEventReceived", eventFrameworkEvent.toString())
        when (eventFrameworkEvent) {
            is RestartEvent -> {
                restartApp()
            }


            is UpdateInformationEvent -> {

                AppConstants.UPDATE_URL = eventFrameworkEvent.updateUrl
                Log.e("update--", eventFrameworkEvent.updateUrl);

                /*val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(eventFrameworkEvent.updateUrl))
                startActivity(browserIntent)*/
            }
            is StartActivationUserIdAndCodeOnlyEvent -> {
                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        "StartActivationUserIdAndCodeOnlyEvent is called",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                runOnUiThread {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ActivationUserIdFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                }


            }
            is StartActivationSetPinEvent -> {

                runOnUiThread {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ActivationSetPinFragment.newInstance())
                        .commit()
                }

            }

            is ActivationResultEvent -> {
                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        eventFrameworkEvent.status.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                val title: String = "Activation"
                when (eventFrameworkEvent.status) {
                    StatusType.INVALID_PARAMETER -> {
                        mcHandler?.triggerCancelEvent()
                    }
                    StatusType.OK -> {
                        // mcHandler?.triggerRestartEvent(null)
                        AppConstants.firstActivation = true
                        mcHandler?.triggerLoginEvent(
                            AppConstants.User,
                            AppConstants.Password,
                            AppConstants.TENANT_ID
                        )

                    }
                    StatusType.ACTIVATION_CODE_EXPIRED -> {
                        mcHandler?.triggerCancelEvent()
                    }
                    StatusType.WRONG_CREDENTIALS -> {
                        mcHandler?.triggerCancelEvent()
                    }
                    StatusType.INVALID_ACTIVATION_CODE -> {
                        mcHandler?.triggerCancelEvent()

                    }
                    StatusType.INVALID_PIN -> {
                        mcHandler?.triggerCancelEvent()

                    }
                    StatusType.UNKNOWN_CERTIFICATE -> {
                        mcHandler?.triggerCancelEvent()
                    }
                    StatusType.UPDATE_NECESSARY -> {

                        mcHandler?.triggerUpdateEvent()

                    }
                    StatusType.UPDATE_AVAILABLE -> {

//showLoading()
                    }

                }
                runOnUiThread {
                    showAlertDialog(this,
                        title,
                        eventFrameworkEvent.status.toString(),
                        DialogInterface.OnClickListener { dialog, which ->

                            if (which == DialogInterface.BUTTON_POSITIVE) {

                                dialog.dismiss()
                                dialog.cancel()

                                when(eventFrameworkEvent.status){
                                    StatusType.OK->{
                                        AppConstants.firstActivation = true
                                        mcHandler?.triggerLoginEvent(
                                            AppConstants.User,
                                            AppConstants.Password,
                                            AppConstants.TENANT_ID
                                        )
                                    }
                                    StatusType.UPDATE_AVAILABLE->{
                                        AppConstants.firstActivation = true

                                        AppConstants.UPDATE_STATUS = "UPDATE_AVAILABLE"
                                        mcHandler?.triggerLoginEvent(
                                            AppConstants.User,
                                            AppConstants.Password,
                                            AppConstants.TENANT_ID
                                        )
                                    }
                                }


                            }

                        })

                }
            }
            is StartLoginEvent -> {
                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        "StartLoginEvent is called",
                        Toast.LENGTH_SHORT
                    ).show()
                }



                userId = eventFrameworkEvent.userIdentifierList as ArrayList<UserIdentifier>?
if(!AppConstants.firstActivation){
                runOnUiThread {
                    val fragment = LoginFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("userLists", userId)
                    fragment.setArguments(bundle)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit()
                }}
                //showLoading()

            }

            is LoginResultEvent -> {
                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        eventFrameworkEvent.status.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                AppConstants.CurrentUser =
                    eventFrameworkEvent.userIdentifier.userId
                val deviceName =
                    StringBuilder(Build.BRAND + " " + Build.MODEL)
                Log.e("{update available} ", "called")

          //      mcHandler?.triggerGetDeviceInfo(AppConstants.CurrentUser)
                if (!AppConstants.firstActivation) {
                    runOnUiThread {
                        showAlertDialog(this,
                            "Login Status",
                            eventFrameworkEvent.status.toString(),
                            DialogInterface.OnClickListener { dialog, which ->

                                if (which == DialogInterface.BUTTON_POSITIVE) {
                                    dialog.dismiss()
                                    dialog.cancel()
                                    when (eventFrameworkEvent.status) {


                                        StatusType.OK->{
                                            val intent = Intent(this, WaitingScreenActivity::class.java)
                                            startActivity(intent)
                                        }
                                        StatusType.INVALID_PIN -> {

                                        }
                                        StatusType.WRONG_CREDENTIALS -> {

                                        }
                                        StatusType.TEMPORARY_LOCKED -> {

                                        }
                                        StatusType.UNKNOWN_CERTIFICATE -> {

                                        }

                                        StatusType.UPDATE_NECESSARY -> {
                                            mcHandler?.triggerUpdateEvent()

                                        }
                                    }

                                }


                            }
                        )
                    }
                }
                when (eventFrameworkEvent.status) {
                    StatusType.UPDATE_AVAILABLE -> {
                        //   mcHandler?.triggerUpdateEvent()
                        val deviceName =
                            StringBuilder(Build.BRAND + " " + Build.MODEL)
                        Log.e("{update available} ", "called")

                        //  mcHandler?.triggerGetDeviceInfo(AppConstants.CurrentUser)

                        if (!AppConstants.firstActivation) {
                            Log.e("triggerGetDeviceInfo ", "called")

                            mcHandler?.triggerGetDeviceInfo(AppConstants.CurrentUser)
                        } else {
                            Log.e("triggerSetDeviceNameEvent ", "called")

                            //Get Firebase FCM token

                            //Get Firebase FCM token
                            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                                if (it.isComplete) {
                                    var fbToken = it.result.toString()
                                    // DO your thing with your firebase token
                                    mcHandler?.triggerSetPushToken("GCM:"+fbToken)

                                }
                            }
                            mcHandler?.triggerSetDeviceNameEvent(deviceName.toString())

                            AppConstants.firstActivation = false
                        }


                        //  mcHandler?.triggerSetDeviceNameEvent(deviceName.toString())


                    }
                    StatusType.OK -> {
                        val deviceName =
                            StringBuilder(Build.BRAND + " " + Build.MODEL)
                        Log.e("{update available} ", "called")

                        //  mcHandler?.triggerGetDeviceInfo(AppConstants.CurrentUser)

                        if (!AppConstants.firstActivation) {
                            Log.e("triggerGetDeviceInfo ", "called")

                            mcHandler?.triggerGetDeviceInfo(AppConstants.CurrentUser)
                        } else {
                            Log.e("triggerSetDeviceNameEvent ", "called")

                            //Get Firebase FCM token

                            //Get Firebase FCM token
                            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                                if (it.isComplete) {
                                    var fbToken = it.result.toString()
                                    // DO your thing with your firebase token
                                    mcHandler?.triggerSetPushToken("GCM:"+fbToken)

                                }
                            }
                            mcHandler?.triggerSetDeviceNameEvent(deviceName.toString())

                            AppConstants.firstActivation = false
                        }


                        /* if (!deviceNameSet) {
                             mcHandler?.triggerSetDeviceNameEvent(deviceName.toString())
                         }
                         AppConstants.CurrentUser =
                             eventFrameworkEvent.userIdentifier.userId
                         if (!AppConstants.firstActivation) {
                             val intent = Intent(this, WaitingScreenActivity::class.java)
                             startActivity(intent)
                         }*/

                    }
                    else -> {

                    }
                }


            }

            is SetPushTokenResultEvent -> {
                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        eventFrameworkEvent.status.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            is SetDeviceNameResultEvent -> {

                when (eventFrameworkEvent.status) {
                    StatusType.OK -> {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "SetDeviceNameResultEvent is called",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        deviceNameSet = true
                        mcHandler?.triggerCancelEvent()
                    }
                    StatusType.NOT_UNIQUE -> {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "SetDeviceNameResultEvent is called",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        deviceNameSet = true
                        mcHandler?.triggerCancelEvent()
                    }

                }
            }
            is GetDeviceInformationResultEvent -> {
                val deviceName =
                    StringBuilder(Build.BRAND + " " + Build.MODEL)
                // mcHandler?.triggerCancelEvent()

                Log.e("GetDeviceInformationResultEvent ", eventFrameworkEvent.deviceName)
                if (eventFrameworkEvent.deviceName == "") {
                    mcHandler?.triggerSetDeviceNameEvent(deviceName.toString())
                }
            }
            is StartAddUserUserIdAndCodeOnlyEvent -> {

                AppConstants.UserLoggedIN = true
                runOnUiThread {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ActivationUserIdFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                }

            }

            is StartAddUserSetPinEvent -> {

                AppConstants.UserLoggedIN = true
                runOnUiThread {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ActivationSetPinFragment.newInstance())
                        .commit()
                }

            }
            is AddUserResultEvent -> {
                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        eventFrameworkEvent.status.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                when (eventFrameworkEvent.status) {
                    StatusType.OK -> {
                        // mcHandler?.triggerRestartEvent(null)
                    }
                    StatusType.WRONG_CREDENTIALS -> {
                        mcHandler?.triggerCancelEvent()
                    }
                    StatusType.INVALID_ACTIVATION_CODE -> {
                        mcHandler?.triggerCancelEvent()

                    }
                    StatusType.ACTIVATION_CODE_EXPIRED -> {
                        mcHandler?.triggerCancelEvent()
                    }

                }
            }
            is StartSetUserIdToDeleteEvent -> {


                mcHandler?.triggerSetUserIdtoDeleteEvent(AppConstants.selectedUser)

            }
            is DeleteUserResultEvent -> {
                when (eventFrameworkEvent.status) {

                    StatusType.OK -> {

                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "deleted successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }


            }
            is UpdateResultEvent -> {
                when (eventFrameworkEvent.updateStatus) {
                    UpdateStatus.INVALID_PARAMETER -> {
                        mcHandler?.triggerCancelEvent()
                    }
                }
            }
            is TriggerBannerEvent->{

                when (eventFrameworkEvent.bannerType) {
                    BannerType.TRANSACTION -> {
                        mcHandler?.triggerStartTransactionEvent()

                    }
                    BannerType.DISPLAY_MESSAGE -> {
                        mcHandler?.triggerStartDisplayMessageEvent()
                    }
                }

            }


        }

    }

    override fun onBackPressed() {

        mcHandler?.triggerCancelEvent()
        super.onBackPressed()
    }

    private fun restartApp() {
        mcHandler?.triggerRestartEvent(null)
    }

    open fun showAlertDialog(
        context: Context?,
        title: String?,
        msg: String?,
        listener: DialogInterface.OnClickListener?
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton("OK", listener)
        builder.setNegativeButton("Dismiss", listener)
        builder.setCancelable(false)

        //builder.setNegativeButton("Cancel", listener)
        builder.create()
        builder.show()

    }
    /*  open fun showLoading(){
          val progressDialog = ProgressDialog(this@BaseActivity)

          progressDialog.setMessage("Please wait")
          progressDialog.show()
      }*/
}