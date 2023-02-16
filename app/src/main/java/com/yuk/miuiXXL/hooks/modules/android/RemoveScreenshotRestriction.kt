package com.yuk.miuiXXL.hooks.modules.android

import android.os.Build
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAllConstructorBefore
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean

object RemoveScreenshotRestriction : BaseHook() {
    override fun init() {

        if (!getBoolean("android_remove_screenshot_restriction", false)) return
        findMethod("com.android.server.wm.WindowState") {
            name == "isSecureLocked"
        }.hookReturnConstant(false)
        if (Build.VERSION.SDK_INT >= 33) {
            findMethod("com.android.server.wm.WindowSurfaceController") {
                name == "setSecure"
            }.hookBefore {
                it.args[0] = false
            }
            hookAllConstructorBefore("com.android.server.wm.WindowSurfaceController") {
                var flags = it.args[2] as Int
                val secureFlag = 128
                flags = flags and secureFlag.inv()
                it.args[2] = flags
            }
        }
    }
}
