package com.kobil.mcwmpgettingstarted.extensions

import android.view.View
import android.widget.TextView

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun TextView.text(): String {
    return this.text.toString().trim()
}

fun TextView.clearText() {
    this.text = ""
}