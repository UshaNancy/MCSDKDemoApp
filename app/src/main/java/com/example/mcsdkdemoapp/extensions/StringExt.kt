package com.kobil.mcwmpgettingstarted.extensions

import android.view.View
import com.kobil.mcwmpgettingstarted.util.Utils


/**
 * This extension checks whether input [String] is not null, not blank, not equals with "[]" or not equals with "null"
 */
fun String?.isNotNullOrEmpty() = !isNullOrEmpty()

fun String?.isNullOrEmpty(): Boolean {
    return if (this == null) true
    else {
        this.trim() == "null" || this.trim() == "[]" || isBlank()
    }
}

fun String?.validateEmptyAndShowError(view: View, errorMsg: String): Boolean {
    if (this.isNullOrEmpty()) {
        if (errorMsg.isNotEmpty())
            Utils.showSnackBar(view, errorMsg)
        return false
    }
    return true

}

fun String?.compareAndShowError(strToCompare: String, view: View, errorMsg: String): Boolean {
    if (this == strToCompare) {
        return true
    }
    if (errorMsg.isNotEmpty())
        Utils.showSnackBar(view, errorMsg)
    return false

}

fun String?.compare(strToCompare: String): Boolean {
    if (this == strToCompare) {
        return true
    }
    return false

}

