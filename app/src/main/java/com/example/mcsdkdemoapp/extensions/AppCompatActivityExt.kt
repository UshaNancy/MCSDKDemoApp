package com.kobil.mcwmpgettingstarted.extensions

import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import com.example.mcsdkdemoapp.BaseActivity
import com.github.florent37.runtimepermission.PermissionResult
import com.github.florent37.runtimepermission.RuntimePermission.askPermission
import com.github.florent37.runtimepermission.kotlin.KotlinRuntimePermission


/**
 * Extension method to request permissions from activity/fragment.
 */
//<editor-fold desc="Permission Request">
fun Activity.askForPermission(
    permissions: Array<String>,
    acceptedblock: (PermissionResult) -> Unit
): KotlinRuntimePermission {
    return KotlinRuntimePermission(
        askPermission(this as AppCompatActivity)
            .request(permissions.toList())
            .onAccepted(acceptedblock)
    )
        .onDeclined { status ->
            if (status.hasForeverDenied()) {
                PermissionUtils.showNeverAskWarning(
                    this as BaseActivity,
                    DialogInterface.OnClickListener { _, _ -> status.goToSettings() })
            }
        }
}

inline val Activity.providerName: String
    get() = "$packageName.ksfileprovider"