package com.yuk.miuiXXL.hooks.modules.personalassistant

import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.callMethod
import com.yuk.miuiXXL.utils.findClass
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.getIntField
import com.yuk.miuiXXL.utils.hookBeforeAllMethods
import com.yuk.miuiXXL.utils.hookBeforeMethod
import com.yuk.miuiXXL.utils.new
import com.yuk.miuiXXL.utils.replaceMethod
import com.yuk.miuiXXL.utils.setObjectField

object BlurWhenGotoMinusOne : BaseHook() {
    override fun init() {

        if (!getBoolean("personalassistant_minus_one_blur", false)) return
        val deviceAdapter = "com.miui.personalassistant.device.DeviceAdapter".findClass()
        val foldableDeviceAdapter = "com.miui.personalassistant.device.FoldableDeviceAdapter".findClass()
        deviceAdapter.hookBeforeAllMethods("create") {
            it.result = foldableDeviceAdapter.new(it.args[0])
        }
        foldableDeviceAdapter.hookBeforeMethod("onOpened") {
            it.thisObject.setObjectField("mScreenSize", 3)
        }
        foldableDeviceAdapter.replaceMethod("onScroll", Float::class.java) {
            val f = it.args[0] as Float
            val i = (f * 100.0f).toInt()
            val mCurrentBlurRadius: Int = it.thisObject.getIntField("mCurrentBlurRadius")
            if (mCurrentBlurRadius != i) {
                if (mCurrentBlurRadius <= 0 || i >= 0) {
                    it.thisObject.setObjectField("mCurrentBlurRadius", i)
                } else {
                    it.thisObject.setObjectField("mCurrentBlurRadius", 0)
                }
                it.thisObject.callMethod("blurOverlayWindow", mCurrentBlurRadius)
            }
        }
    }

}
