package com.yuk.fuckMiui.hooks.modules.miuihome

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.yuk.fuckMiui.hooks.modules.BaseHook
import com.yuk.fuckMiui.utils.getInt

object AnimDurationRatio : BaseHook() {
    override fun init() {

        val value1 = getInt("miuihome_anim_ratio", 100).toFloat() / 100f
        val value2 = getInt("miuihome_anim_ratio_recent", 100).toFloat() / 100f
        if (value1 == 1.0f && value2 == 1.0f) return
        findMethod("com.miui.home.recents.util.RectFSpringAnim") {
            name == "getModifyResponse"
        }.hookBefore {
            it.result = it.args[0] as Float * value1
        }
        findMethod("com.miui.home.launcher.common.DeviceLevelUtils") {
            name == "getDeviceLevelTransitionAnimRatio"
        }.hookBefore {
            it.result = value2
        }
    }

}