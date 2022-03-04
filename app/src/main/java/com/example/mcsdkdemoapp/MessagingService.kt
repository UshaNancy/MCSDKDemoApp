package com.example.mcsdkdemoapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {
    val TAG = "TrustedMessagingService"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        try {
            if (remoteMessage != null && remoteMessage.data != null && remoteMessage.data
                    .containsKey("message")
            ) {
                if (remoteMessage.data != null) {
                    val message = remoteMessage.data["message"]
                    val contents =
                        message!!.split("\\|".toRegex()).toTypedArray()

                    sendNotification(message)
                }
            }
        } catch (e: Exception) {
            Log.e("onMessageReceived", e.message!!)
        }
    }
    // [END receive_message]

    // [END receive_message]
    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param message FCM message body received.
     */
    open fun sendNotification(message: String?) {
        try {
            if (message != null) {
                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                if (Build.VERSION.SDK_INT >= 26) {
                    val mChannel = NotificationChannel(
                        "0",
                        AppConstants.APP_NAME,
                        NotificationManager.IMPORTANCE_HIGH
                    )
                    mChannel.description = message
                    mChannel.enableLights(true)
                    // Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
                    mChannel.lightColor = Color.WHITE
                    mChannel.enableVibration(true)
                    mChannel.vibrationPattern = longArrayOf(
                        100,
                        200,
                        300,
                        400,
                        500,
                        400,
                        300,
                        200,
                        400
                    )
                    notificationManager.createNotificationChannel(mChannel)
                }
                val notificationBuilder =
                    NotificationCompat.Builder(this, "0")
                        .setContentTitle(AppConstants.APP_NAME)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setVibrate(longArrayOf(100, 200, 100, 500))
                notificationBuilder.setSmallIcon(R.drawable.ic_menu_gallery)
                showHeadsUpNotification(message, notificationBuilder, notificationManager)

//
//                if (HandlerFactory.getInstance().isShouldShowNotification()) {
//                    //App in background or killed
//                    showHeadsUpNotification(message, notificationBuilder, notificationManager);
//                } else {
//                    //App in foreground
//                    if (HandlerFactory.getInstance().getSelectedTenantModel() != null) {
//                        //Some user logged in
//                        //Ignore Notification
//                    } else {
//                        //Not logged in, waiting in home screen\
//                        //App in background or killed
//                    }
//                }
            }
        } catch (err: Exception) {
        }
    }

    open fun showHeadsUpNotification(
        message: String,
        notificationBuilder: NotificationCompat.Builder,
        notificationManager: NotificationManager
    ) {
        val contents = message.split("\\|".toRegex()).toTypedArray()
        if (contents.size == 2) {
            notificationBuilder.setContentText(contents[0].trim { it <= ' ' })
        } else {
            notificationBuilder.setContentText(message)
        }
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.putExtra("isFromNotification", true)
        if (contents.size == 2) {
            notificationIntent.putExtra(
                "userIdFromNotification",
                contents[1].trim { it <= ' ' }
            )
        }

        // set intent so it does not start a new activity
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val pIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            notificationBuilder.setContentIntent(pIntent)

            notificationBuilder.setAutoCancel(true)
            notificationBuilder.setContentIntent(PendingIntent.getActivity(this, 0, Intent(), 0))

        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }
}