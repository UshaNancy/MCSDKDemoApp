package com.kobil.mcwmpgettingstarted.extensions

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable


fun Bundle?.contents(): String {
    if (this != null && !this.isEmpty && keySet() != null && keySet().isNotEmpty()) {
        val stringBuilder = StringBuilder()
        stringBuilder.append("\n\n")
        stringBuilder.append("+===================== Bundle Contents =====================+\n\r")
        for (key in keySet()) {
            stringBuilder.append("| ").append(key).append(" = ").append(get(key)).append("\n\r")
        }
        stringBuilder.append("+===========================================================+")
        stringBuilder.append("\n\n")
        return stringBuilder.toString()

    }
    return "bundle was empty"

}

fun Intent?.contents(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.appendln("+===================== Intent Contents =====================+\n\r")
    if (this != null) {
        if (extras != null && !extras!!.isEmpty && extras!!.keySet() != null && extras!!.keySet()
                .isNotEmpty()
        ) {
            for (key in extras!!.keySet()) {
                stringBuilder.append("+=> ").append(key).append(" = ").append(extras!![key])
                    .appendln()
            }
        }
        stringBuilder.append("+=> ").append("flags = ").append(flags).appendln()
        stringBuilder.append("+=> ").append("type = ").append(type).appendln()
        stringBuilder.append("+=> ").append("action = ").append(action).appendln()
        stringBuilder.append("+=> ").append("scheme = ").append(scheme.orEmpty()).appendln()
        stringBuilder.append("+=> ").append("identifier = ").append(`package`.orEmpty()).appendln()
        stringBuilder.append("+=> ").append("dataString = ").append(dataString.orEmpty()).appendln()
        stringBuilder.append("+=> ").append("data = ").append(data?.toString().orEmpty()).appendln()
    }
    stringBuilder.appendln("+===========================================================+")
    return stringBuilder.toString()
}

fun <T : Parcelable> Bundle.putParcelableMutableList(name: String?, value: MutableList<T>) {
    putParcelableArrayList(name, value as ArrayList<out T>)
}