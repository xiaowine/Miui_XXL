package com.yuk.fuckMiui.hooks.modules.miuihome

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.yuk.fuckMiui.hooks.modules.BaseHook
import com.yuk.fuckMiui.utils.getBoolean
import com.yuk.fuckMiui.utils.getObjectField

object TwoXOneIconRoundedCornerFollowing : BaseHook() {
    override fun init() {

        if (!getBoolean("two_x_one_icon_rounded_corner_following", false)) return
        findMethod("com.miui.home.launcher.bigicon.BigIconUtil") {
            name == "getCroppedFromCorner" && parameterCount == 4
        }.hookBefore {
            it.args[0] = 2
            it.args[1] = 2
        }

        findMethod(
            "com.miui.home.launcher.maml.MaMlHostView"
        ) {
            name == "getCornerRadius"
        }.hookBefore {
            it.result = it.thisObject.getObjectField("mEnforcedCornerRadius") as Float
        }

        findMethod("com.miui.home.launcher.maml.MaMlHostView") {
            name == "computeRoundedCornerRadius" && parameterCount == 1
        }.hookBefore {
            it.result = it.thisObject.getObjectField("mEnforcedCornerRadius") as Float
        }

        findMethod("com.miui.home.launcher.LauncherAppWidgetHostView") {
            name == "computeRoundedCornerRadius" && parameterCount == 1
        }.hookBefore {
            it.result = it.thisObject.getObjectField("mEnforcedCornerRadius") as Float
        }
    }

}