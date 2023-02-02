package com.yuk.miuiXXL.utils

import android.annotation.SuppressLint
import android.os.Build
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

fun atLeastAndroidT(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

fun checkMiuiVersion(): String = when (getProp("ro.miui.ui.version.name")) {
    "V140" -> "14"
    "V130" -> "13"
    "V125" -> "12.5"
    "V12" -> "12"
    "V11" -> "11"
    "V10" -> "10"
    else -> "?"
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

