package com.yuk.miuiXXL.hooks.modules.thememanager

import android.widget.TextView
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.findAllMethods
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.getObjectAs
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.putObject
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.findClass
import com.yuk.miuiXXL.utils.getBoolean
import miui.drm.DrmManager

object FuckValidateTheme1 : BaseHook() {
    override fun init() {

        if (!getBoolean("thememanager_fuck_validate_theme", false)) return
        var letter = 'a'
        for (i in 0..25) {
            val classIfExists = ("com.android.thememanager.controller.online.${letter}").findClass()
            try {
                findMethod(classIfExists) {
                    parameterCount == 1 && returnType == DrmManager.DrmResult::class.java
                }.hookAfter {
                    it.result = DrmManager.DrmResult.DRM_SUCCESS
                }
            } catch (t: Throwable) {
                letter++
            }
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
