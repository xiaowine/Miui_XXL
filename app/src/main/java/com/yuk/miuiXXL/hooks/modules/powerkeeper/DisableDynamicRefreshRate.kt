package com.yuk.miuiXXL.hooks.modules.powerkeeper

import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.hookBeforeMethod

object DisableDynamicRefreshRate : BaseHook() {
    override fun init() {

        if (!getBoolean("powerkeeper_disable_dynamic_refresh_rate", false)) return
        "com.miui.powerkeeper.statemachine.DisplayFrameSetting".hookBeforeMethod("isFeatureOn") {
            it.result = false
        }
        "com.miui.powerkeeper.statemachine.DisplayFrameSetting".hookBeforeMethod(
            "setScreenEffect", String::class.java, Int::class.javaPrimitiveType, Int::class.javaPrimitiveType
        ) {
            it.result = null
        }
        "com.miui.powerkeeper.statemachine.DisplayFrameSetting".hookBeforeMethod(
            "setScreenEffectInternal", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType, String::class.java
        ) {
            it.result = null
        }
    }

}