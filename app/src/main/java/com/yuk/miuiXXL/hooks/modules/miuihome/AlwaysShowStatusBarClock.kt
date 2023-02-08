package com.yuk.miuiXXL.hooks.modules.miuihome

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean

object AlwaysShowStatusBarClock : BaseHook() {
    override fun init() {

        if (!getBoolean("miuihome_always_show_statusbar_clock", false)) return
        try {
            findMethod("com.miui.home.launcher.Workspace") {
                name == "isScreenHasClockGadget"
            }
        } catch (e: Exception) {
            findMethod("com.miui.home.launcher.Workspace") {
                name == "isScreenHasClockWidget"
            }
        } catch (e: Exception) {
            findMethod("com.miui.home.launcher.Workspace") {
                name == "isClockWidget"
            }
        }.hookReturnConstant(false)
    }

}
