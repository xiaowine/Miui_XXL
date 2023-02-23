package com.yuk.miuiXXL.hooks.modules.thememanager

import android.widget.TextView
import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.findAllMethods
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.getObjectAs
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.putObject
import com.yuk.miuiXXL.R
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.findClassOrNull
import com.yuk.miuiXXL.utils.getBoolean
import miui.drm.DrmManager

object FuckValidateTheme1 : BaseHook() {
    override fun init() {

        if (!getBoolean("thememanager_fuck_validate_theme", false)) return
        var letter = 'a'
        for (i in 0..25) {
            try {
                val classIfExists = "com.android.thememanager.controller.online.${letter}".findClassOrNull()
                classIfExists?.let {
                    findMethod(it) {
                        parameterCount == 1 && returnType == DrmManager.DrmResult::class.java
                    }.hookAfter { hookParam ->
                        hookParam.result = DrmManager.DrmResult.DRM_SUCCESS
                    }
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
                name == "f"
            }.hookAfter {
                it.thisObject.getObjectAs<TextView>("b").text = moduleRes.getString(R.string.thememanager_fuck_validate_theme_fonts_title)
            }
        } catch (t: Throwable) {
            Log.ex(t)
        }
    }

}
