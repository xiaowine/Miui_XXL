package com.yuk.miuiXXL.hooks.modules.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean

object RemoveScreenshotRestriction : BaseHook() {
    override fun init() {

        if (!getBoolean("android_remove_screenshot_restriction", false)) return
        findMethod("com.android.server.wm.WindowState") {
            name == "isSecureLocked"
        }.hookReturnConstant(false)
    }

}
