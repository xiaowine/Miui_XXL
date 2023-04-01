package com.yuk.miuiXXL.hooks.modules.miuihome

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.callMethod
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.getIntField

object InfiniteScroll : BaseHook() {
    override fun init() {
        if (!getBoolean("miuihome_infinite_scroll", false)) return
        findMethod("com.miui.home.launcher.ScreenView") { name == "getSnapToScreenIndex" }.hookAfter {
            if (it.args[0] !== it.result) return@hookAfter
            val screenCount = it.thisObject.callMethod("getScreenCount") as Int
            if (it.args[2] as Int == -1 && it.args[0] as Int == 0) it.result = screenCount
            else if (it.args[2] as Int == 1 && it.args[0] as Int == screenCount - 1) it.result = 0
        }
        findMethod("com.miui.home.launcher.ScreenView") { name == "getSnapUnitIndex" }.hookAfter {
            val mCurrentScreenIndex = it.thisObject.getIntField("mCurrentScreenIndex")
            if (mCurrentScreenIndex != it.result as Int) return@hookAfter
            val screenCount = it.thisObject.callMethod("getScreenCount") as Int
            if (it.result as Int == 0) it.result = screenCount
            else if (it.result as Int == screenCount - 1) it.result = 0
        }
    }
}
