package com.yuk.fuckMiui.utils

import android.annotation.SuppressLint
import android.os.Build
import com.yuk.fuckMiui.BuildConfig
import de.robv.android.xposed.XSharedPreferences
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

@SuppressLint("PrivateApi")
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun getProp(mKey: String): String =
    Class.forName("android.os.SystemProperties").getMethod("get", String::class.java).invoke(Class.forName("android.os.SystemProperties"), mKey)
        .toString()

@SuppressLint("PrivateApi")
fun getProp(mKey: String, defaultValue: Boolean): Boolean =
    Class.forName("android.os.SystemProperties").getMethod("getBoolean", String::class.java, Boolean::class.javaPrimitiveType)
        .invoke(Class.forName("android.os.SystemProperties"), mKey, defaultValue) as Boolean


fun atLeastAndroidS(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

fun atLeastAndroidT(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

fun checkMiuiVersion(): Float = when (getProp("ro.miui.ui.version.name")) {
    "V140" -> 14f
    "V130" -> 13f
    "V125" -> 12.5f
    "V12" -> 12f
    "V11" -> 11f
    "V10" -> 10f
    else -> 0f
}

fun checkAndroidVersion(): String = getProp("ro.build.version.release")

fun exec(command: String): String {
    var process: Process? = null
    var reader: BufferedReader? = null
    var `is`: InputStreamReader? = null
    var os: DataOutputStream? = null
    return try {
        process = Runtime.getRuntime().exec("su")
        `is` = InputStreamReader(process.inputStream)
        reader = BufferedReader(`is`)
        os = DataOutputStream(process.outputStream)
        os.writeBytes(command.trimIndent())
        os.writeBytes("\nexit\n")
        os.flush()
        var read: Int
        val buffer = CharArray(4096)
        val output = StringBuilder()
        while (reader.read(buffer).also { read = it } > 0) output.appendRange(buffer, 0, read)
        process.waitFor()
        output.toString()
    } catch (e: IOException) {
        throw RuntimeException(e)
    } catch (e: InterruptedException) {
        throw RuntimeException(e)
    } finally {
        try {
            os?.close()
            `is`?.close()
            reader?.close()
            process?.destroy()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

fun exec(commands: Array<String>): String {
    val stringBuilder = java.lang.StringBuilder()
    for (command in commands) {
        stringBuilder.append(exec(command))
        stringBuilder.append("\n")
    }
    return stringBuilder.toString()
}

fun getBoolean(key: String, defValue: Boolean): Boolean {
    val prefs = XSharedPreferences(BuildConfig.APPLICATION_ID, "FuckMiuiConfig")
    if (prefs.hasFileChanged()) prefs.reload()
    return prefs.getBoolean(key, defValue)
}
