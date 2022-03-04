package com.kobil.mcwmpgettingstarted.extensions

import android.os.Build


inline val Any.DEVICE_MODEL: String
    get() {
        return "${Build.MANUFACTURER} ${Build.MODEL}"
    }

@Suppress("DEPRECATION")
inline val Any.DEVICE_CPU_ARCHITECTURE: String
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Build.SUPPORTED_ABIS[0]
            } catch (e: Exception) {
                Build.CPU_ABI
            }
        } else {
            Build.CPU_ABI
        }
    }