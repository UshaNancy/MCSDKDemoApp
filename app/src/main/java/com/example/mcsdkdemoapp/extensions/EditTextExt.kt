package com.kobil.mcwmpgettingstarted.extensions

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView

/**
 * Sets TextView.OnEditorActionListener and calls specified function [block]
 * if EditorInfo.IME_ACTION_DONE was specified for this edit text
 *
 * @see EditText.setOnEditorActionListener
 * @see TextView.OnEditorActionListener
 */
inline fun EditText.onImeActionDone(crossinline block: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_DONE) {
            block.invoke()
            true
        } else {
            false
        }
    }
}