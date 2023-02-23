package com.yuk.miuiXXL.hooks.modules.packageinstaller

import android.content.pm.ApplicationInfo
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.findClassOrNull
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.hookBeforeMethod
import de.robv.android.xposed.XposedBridge

object AllowUpdateSystemApp : BaseHook() {
    override fun init() {

        if (!getBoolean("packageinstaller_allow_update_system_app", false)) return
        var letter = 'a'
        for (i in 0..25) {
            try {
                val classIfExists = "j2.${letter}".findClassOrNull()
                classIfExists?.let {
                    findMethod(it) {
                        parameterCount == 1 && parameterTypes[0] == ApplicationInfo::class.java
                    }.hookBefore { hookParam ->
                        XposedBridge.log("hook succeed")
                        hookParam.result = false
                    }
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
