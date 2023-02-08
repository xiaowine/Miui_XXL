package com.yuk.miuiXXL.hooks.modules.systemui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.provider.Settings
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.utils.findConstructor
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.paramCount
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean
import java.lang.reflect.Method
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

object StatusBarShowSeconds : BaseHook() {
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun init() {

        if (!getBoolean("systemui_statusbar_show_seconds", false)) return
        try {
            var c: Context? = null
            try {
                findConstructor("com.android.systemui.statusbar.views.MiuiClock") {
                    paramCount == 3
                }.hookAfter {
                    try {
                        c = it.args[0] as Context
                        val textV = it.thisObject as TextView?
                        if (textV != null) {
                            val d: Method = textV.javaClass.getDeclaredMethod("updateTime")
                            val r = Runnable {
                                d.isAccessible = true
                                d.invoke(textV)
                            }

                            class T : TimerTask() {
                                override fun run() {
                                    Handler(textV.context.mainLooper).post(r)
                                }
                            }
                            if (textV.resources.getResourceEntryName(textV.id) == "clock") {
                                Timer().scheduleAtFixedRate(T(), 1000 - System.currentTimeMillis() % 1000, 1000)
                            }
                        }
                    } catch (_: Exception) {
                    }
                }
            } catch (_: Exception) {
            }
            try {
                findMethod("com.android.systemui.statusbar.views.MiuiClock") {
                    name == "updateTime"
                }.hookAfter {
                    try {
                        val textV = it.thisObject as TextView?
                        if (textV != null && c != null) {
                            val t = Settings.System.getString(c!!.contentResolver, Settings.System.TIME_12_24)
                            if (textV.resources.getResourceEntryName(textV.id) == "clock") {
                                if (t == "24") {
                                    textV.text = SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().time)
                                } else {
                                    var string = ""
                                    if (Locale.getDefault().country == "US") {
                                        string = textV.text.toString().substring(textV.text.toString().length - 3, textV.text.toString().length)
                                    }
                                    val text = textV.text.toString().replace("AM", "").replace("PM", "").replace(" ", "")
                                    textV.text = text + SimpleDateFormat(":ss").format(Calendar.getInstance().time) + string
                                }
                            }
                        }
                    } catch (_: Exception) {
                    }
                }
            } catch (_: Exception) {
            }
        } catch (_: Exception) {
        }
    }

}
