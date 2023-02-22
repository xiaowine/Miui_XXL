package com.yuk.miuiXXL.hooks.modules.packageinstaller

import android.app.Application
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.findAllMethods
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.findClass
import com.yuk.miuiXXL.utils.findClassOrNull
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.hookBeforeMethod

object AllowUpdateSystemApp : BaseHook() {
    override fun init() {

        if (!getBoolean("packageinstaller_allow_update_system_app", false)) return
        val miuiSettingsCompatClass = "com.android.packageinstaller.compat.MiuiSettingsCompat".findClass()

        try {
            findAllMethods(miuiSettingsCompatClass) {
                name == "isPersonalizedAdEnabled"
            }.hookReturnConstant(false)
        } catch (t: Throwable) {
            Log.ex(t)
        }

        var letter = 'a'
        for (i in 0..25) {
            val classIfExists = ("j2.${letter}").findClassOrNull()
            try {
                classIfExists?.let {
                    findAllMethods(it) {
                        parameterCount == 1 && returnType == Boolean::class.java && parameterTypes[0] == Application::class.java
                    }.hookReturnConstant(false)
                }
            } catch (t: Throwable) {
                letter++
            }
        }

        try {
            "android.os.SystemProperties".hookBeforeMethod(
                "getBoolean", String::class.java, Boolean::class.java
            ) {
                if (it.args[0] == "persist.sys.allow_sys_app_update") it.result = true
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
    }

}
