package com.yuk.miuiXXL.hooks.modules.systemui

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean

object UseNewHD : BaseHook() {
    override fun init() {
        if (!getBoolean("systemui_use_new_hd", false)) return
        runCatching {
            findMethod("com.android.systemui.statusbar.policy.HDController") { name == "isVisible" }.hookReturnConstant(true)
        }

    }
}