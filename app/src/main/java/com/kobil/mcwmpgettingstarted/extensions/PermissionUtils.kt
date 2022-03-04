package com.kobil.mcwmpgettingstarted.extensions

import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.mcsdkdemoapp.BaseActivity
import com.example.mcsdkdemoapp.R
import com.kobil.mcwmpgettingstarted.util.Utils

object PermissionUtils {

    // When the user has clicked "Never Ask" on permission request this dialog will be shown on every permission request
    @JvmStatic
    fun showNeverAskWarning(
        activity: BaseActivity,
        onProvideManually: DialogInterface.OnClickListener,
        onCancelClick: DialogInterface.OnClickListener = DialogInterface.OnClickListener { _, _ -> }
    ) {
       /* Utils.showAlertDialog(
            context = activity,
            text = activity.getString(R.string.ks_permission_required_dialog_text),
            positiveButtonText = activity.getString(R.string.ks_permission_required_dialog_btn_pos),
            negativeButtonText = activity.getString(R.string.ks_close),
            positiveButtonClickListener = onProvideManually,
            negativeButtonClickListener = onCancelClick,
            title = activity.getString(R.string.ks_permission_required_dialog_title)
        )*/
    }

    // This will check if permissions received by parameter are granted
    @JvmStatic
    fun isPermissionGranted(c: Context, permissions: Array<String>): Boolean {
        for (p in permissions) {
            if (ActivityCompat.checkSelfPermission(c, p) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }}
