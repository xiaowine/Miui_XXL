package com.yuk.miuiXXL.hooks.modules.systemui

import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.hookBeforeMethod

object RemoveLockScreenMinus : BaseHook() {
    override fun init() {

        if (!getBoolean("systemui_lockscreen_remove_minus", false)) return
        "com.android.keyguard.negative.MiuiKeyguardMoveLeftViewContainer".hookBeforeMethod("inflateLeftView") {
            it.result = null
        }
    }

}
