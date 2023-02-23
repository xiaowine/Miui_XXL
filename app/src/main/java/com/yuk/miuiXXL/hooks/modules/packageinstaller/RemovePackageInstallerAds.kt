package com.yuk.miuiXXL.hooks.modules.packageinstaller

import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.findAllMethods
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.findClass
import com.yuk.miuiXXL.utils.findClassOrNull
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.setBooleanField

object RemovePackageInstallerAds : BaseHook() {
    override fun init() {

        if (!getBoolean("packageinstaller_remove_ads", false)) return
        val miuiSettingsCompatClass = "com.android.packageinstaller.compat.MiuiSettingsCompat".findClass()

        try {
            findAllMethods(miuiSettingsCompatClass) {
                name == "isPersonalizedAdEnabled" && returnType == Boolean::class.java
            }.hookReturnConstant(false)
        } catch (t: Throwable) {
            Log.ex(t)
        }

        var letter = 'a'
        for (i in 0..25) {
            try {
                val classIfExists = "com.miui.packageInstaller.ui.listcomponets.${letter}0".findClassOrNull()
                classIfExists?.let {
                    findMethod(it) {
                        name == "a"
                    }.hookAfter { hookParam ->
                        hookParam.thisObject.setBooleanField("l", false)
                    }
                }
            } catch (t: Throwable) {
                letter++
            }
        }
    }

}
