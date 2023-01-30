package com.yuk.fuckMiui.utils

import com.yuk.fuckMiui.BuildConfig
import de.robv.android.xposed.XSharedPreferences

fun getBoolean(key: String, defValue: Boolean): Boolean {
    val prefs = XSharedPreferences(BuildConfig.APPLICATION_ID, "FuckMiuiConfig")
    if (prefs.hasFileChanged()) prefs.reload()
    return prefs.getBoolean(key, defValue)
}

fun getInt(key: String, defValue: Int): Int {
    val prefs = XSharedPreferences(BuildConfig.APPLICATION_ID, "FuckMiuiConfig")
    if (prefs.hasFileChanged()) prefs.reload()
    return prefs.getInt(key, defValue)
}

fun getFloat(key: String, defValue: Float): Float {
    val prefs = XSharedPreferences(BuildConfig.APPLICATION_ID, "FuckMiuiConfig")
    if (prefs.hasFileChanged()) prefs.reload()
    return prefs.getFloat(key, defValue)
}
