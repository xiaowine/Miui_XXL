package com.yuk.fuckMiui.utils

import com.yuk.fuckMiui.BuildConfig
import de.robv.android.xposed.XSharedPreferences

fun prefs() = XSharedPreferences(BuildConfig.APPLICATION_ID, "FuckMiuiConfig")

fun getBoolean(key: String, defValue: Boolean): Boolean {
    if (prefs().hasFileChanged()) prefs().reload()
    return prefs().getBoolean(key, defValue)
}

fun getInt(key: String, defValue: Int): Int {
    if (prefs().hasFileChanged()) prefs().reload()
    return prefs().getInt(key, defValue)
}

fun getFloat(key: String, defValue: Float): Float {
    if (prefs().hasFileChanged()) prefs().reload()
    return prefs().getFloat(key, defValue)
}
