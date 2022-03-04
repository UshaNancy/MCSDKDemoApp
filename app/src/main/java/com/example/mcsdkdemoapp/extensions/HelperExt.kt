package com.kobil.mcwmpgettingstarted.extensions

import java.io.PrintWriter
import java.io.StringWriter


inline fun <R> R?.orElse(block: () -> R): R {
    return this ?: block()
}

val Throwable.stackTraceAsString: String
    get() : String {
        // https://docs.oracle.com/javase/9/docs/api/java/io/StringWriter.html
        // Closing a StringWriter has no effect.
        val stringWriter = StringWriter()
        PrintWriter(stringWriter).use { printWriter ->
            printStackTrace(printWriter)
            return stringWriter.toString() // stack trace as a string
        }
    }