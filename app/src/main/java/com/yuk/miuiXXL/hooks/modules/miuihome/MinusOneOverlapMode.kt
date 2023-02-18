package com.yuk.miuiXXL.hooks.modules.miuihome

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean

object MinusOneOverlapMode : BaseHook() {
    override fun init() {

        if (!getBoolean("miuihome_minus_one_overlap_mode", false) || !getBoolean("personalassistant_minus_one_blur", false)) return
        findMethod("com.miui.home.launcher.overlay.assistant.AssistantDeviceAdapter") {
            name == "inOverlapMode"
        }.hookReturnConstant(true)
    }

}
