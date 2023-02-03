package com.yuk.miuiXXL.hooks.modules.android

import android.content.Context
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean

object RemoveSmallWindowRestriction1 : BaseHook() {
    override fun init() {

        if (!getBoolean("android_remove_small_window_restriction", false)) return
        findMethod("com.android.server.wm.Task") {
            name == "isResizeable"
        }.hookReturnConstant(true)

        findMethod("android.util.MiuiMultiWindowAdapter") {
            name == "getFreeformBlackList"
        }.hookAfter {
            it.result = (it.result as MutableList<*>).apply { clear() }
        }

        findMethod("android.util.MiuiMultiWindowAdapter") {
            name == "getFreeformBlackListFromCloud" && parameterTypes[0] == Context::class.java
        }.hookAfter {
            it.result = (it.result as MutableList<*>).apply { clear() }
        }

        findMethod("android.util.MiuiMultiWindowUtils") {
            name == "supportFreeform"
        }.hookReturnConstant(true)

    }

}
