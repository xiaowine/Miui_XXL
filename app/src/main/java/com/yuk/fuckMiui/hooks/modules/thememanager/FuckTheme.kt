package com.yuk.fuckMiui.hooks.modules.thememanager

import android.widget.TextView
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.findAllMethods
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.getObjectAs
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.putObject
import com.yuk.fuckMiui.hooks.modules.BaseHook
import com.yuk.fuckMiui.utils.getBoolean
import miui.drm.DrmManager

object FuckTheme : BaseHook() {
    override fun init() {

        if (!getBoolean("thememanager_fuck_validate_theme", false)) return
        try {
            findMethod("com.android.thememanager.controller.online.c") {
                parameterCount == 1 && returnType == DrmManager.DrmResult::class.java
            }.hookAfter {
                it.result = DrmManager.DrmResult.DRM_SUCCESS
            }
        } catch (t: Throwable) {
            Log.ex(t)
        }
        try {
            findAllMethods("com.android.thememanager.detail.theme.model.OnlineResourceDetail") {
                name == "toResource"
            }.hookAfter {
                it.thisObject.putObject("bought", true)
            }
        } catch (t: Throwable) {
            Log.ex(t)
        }
        try {
            findMethod("com.android.thememanager.basemodule.views.DiscountPriceView") {
                name == "f" && parameterCount == 1
            }.hookAfter {
                it.thisObject.getObjectAs<TextView>("b").text = "免费"
            }
        } catch (t: Throwable) {
            Log.ex(t)
        }
    }

}
