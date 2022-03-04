package com.kobil.mcwmpgettingstarted.util

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.DialogInterface
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.text.HtmlCompat
import com.example.mcsdkdemoapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.atomic.AtomicBoolean


object Utils {

    private var isRunningTest: AtomicBoolean? = null

    fun showSnackBar(anyView: View, msg: String?): Snackbar? {
        val snackBar = Snackbar.make(anyView, msg!!, Snackbar.LENGTH_LONG)
        snackBar.setActionTextColor(Color.WHITE)
        if (anyView is CoordinatorLayout) {
            val params =
                snackBar.view.layoutParams as CoordinatorLayout.LayoutParams
            params.setMargins(0, 0, 0, 130)
            snackBar.view.layoutParams = params
        }
        val view: View = snackBar.view
        val tv: TextView = view.findViewById(R.id.snackbar_text)
        tv.setTextColor(Color.WHITE)
        tv.maxLines = 5
        snackBar.show()
        return snackBar
    }

    fun hideKeyboard(activity: Activity) {
        try {
            val inputManager: InputMethodManager = activity
                .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
    }

    /**
     * Method to show alert dialog with single button
     *
     * @param mContext Context of the calling class
     * @param text     Text to show in toast
     */
    fun showAlertDialog(
        context: Context,
        text: String,
        title: String,
        buttonText: String = "OK",
        clickListener: DialogInterface.OnClickListener?,
        isCancelable: Boolean = false,
        isShowDialog: Boolean = true
    ): AlertDialog? {
        val alertDialog = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(
                HtmlCompat.fromHtml(
                    text,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            )
            .setIcon(R.mipmap.ic_launcher_round)
            .setCancelable(isCancelable)
            .setPositiveButton(buttonText
            ) { dialog, which ->
                dialog?.dismiss()
                clickListener?.onClick(dialog, which)
            }.create()
        if (isShowDialog)
            alertDialog.show()
        return alertDialog;

    }


    fun showAlertDialog(
        context: Context,
        text: String,
        title: String,
        positiveButtonText: String,
        positiveButtonClickListener: DialogInterface.OnClickListener?,
        negativeButtonText: String,
        negativeButtonClickListener: DialogInterface.OnClickListener?,
        isCancelable: Boolean = false,
        isShowDialog: Boolean = true
    ): AlertDialog {

        val alertDialog = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(text)
            .setIcon(R.mipmap.ic_launcher_round)
            .setCancelable(isCancelable)
            .setPositiveButton(
                positiveButtonText
            ) { dialog, which ->
                dialog?.dismiss()
                positiveButtonClickListener?.onClick(dialog, which)
            }
            .setNegativeButton(
                negativeButtonText
            ) { dialog, which ->
                dialog?.dismiss()
                negativeButtonClickListener?.onClick(dialog, which)
            }.create()
        if (isShowDialog)
            alertDialog.show()
        return alertDialog
    }

    fun showToast(context: Context, msg: String) {
        launchMain {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    @Synchronized
    fun isRunningTest(): Boolean {
        if (null == isRunningTest) {
            val istest: Boolean
            istest = try {
                Class.forName("androidx.test.espresso.Espresso")
                true
            } catch (e: ClassNotFoundException) {
                false
            }
            isRunningTest = AtomicBoolean(istest)
        }
        return isRunningTest!!.get()
    }

}