package com.yuk.miuiXXL.hooks.modules.miuihome

import android.app.Activity
import android.view.MotionEvent
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.callStaticMethod
import com.yuk.miuiXXL.utils.findClass
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.getObjectField
import com.yuk.miuiXXL.utils.hookBeforeMethod

object UseCompleteBlur : BaseHook() {
    override fun init() {

        if (!getBoolean("miuihome_use_complete_blur", false)) return
        val blurUtilsClass = "com.miui.home.launcher.common.BlurUtils".findClass()
        val navStubViewClass = "com.miui.home.recents.NavStubView".findClass()

        findMethod(blurUtilsClass) {
            name == "getBlurType"
        }.hookBefore {
            it.result = 2
        }

        if (getBoolean("miuihome_complete_blur_fix", false)) {
            navStubViewClass.hookBeforeMethod("appTouchResolution", MotionEvent::class.java) {
                val mLauncher = it.thisObject.getObjectField("mLauncher") as Activity?
                blurUtilsClass.callStaticMethod("fastBlurDirectly", 1.0f, mLauncher?.window)
            }
        }
    }

}
