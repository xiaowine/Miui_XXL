package com.yuk.miuiXXL.hooks.modules.miuihome

import com.github.kyuubiran.ezxhelper.utils.findAllMethods
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean

object RemoveSmallWindowRestriction2 : BaseHook() {
    override fun init() {

        if (!getBoolean("android_remove_small_window_restriction", false)) return
        findAllMethods("com.miui.home.launcher.RecentsAndFSGestureUtils") {
            name == "canTaskEnterSmallWindow"
        }.hookReturnConstant(true)
        findAllMethods("com.miui.home.launcher.RecentsAndFSGestureUtils") {
            name == "canTaskEnterMiniSmallWindow"
        }.hookReturnConstant(true)

    }

}
