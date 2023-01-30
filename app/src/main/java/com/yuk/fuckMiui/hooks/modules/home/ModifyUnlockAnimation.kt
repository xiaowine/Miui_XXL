package com.yuk.fuckMiui.hooks.modules.home

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.yuk.fuckMiui.hooks.modules.BaseHook
import com.yuk.fuckMiui.utils.getBoolean

object ModifyUnlockAnimation : BaseHook() {
    override fun init() {

        if (!getBoolean("miuihome_unlock_animation", false)) return
        findMethod("com.miui.home.launcher.compat.UserPresentAnimationCompatV12Phone") {
            name == "getSpringAnimator" && parameterCount == 6
        }.hookBefore {
            it.args[4] = 0.5f
            it.args[5] = 0.5f
        }
    }

}
