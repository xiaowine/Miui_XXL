package com.yuk.miuiXXL.hooks.modules.thememanager

import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.yuk.miuiXXL.utils.getBoolean
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.luckypray.dexkit.DexKitBridge
import miui.drm.DrmManager
import java.lang.reflect.Method

class FuckValidateTheme2 : IXposedHookLoadPackage {

    @Throws(NoSuchMethodException::class)
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (!getBoolean("thememanager_fuck_validate_theme", false)) return
        System.loadLibrary("dexkit")
        DexKitBridge.create(lpparam.appInfo.sourceDir)?.use { bridge ->

            val map = mapOf(
                "DrmResult" to setOf("theme", "ThemeManagerTag", "/system"),
            )

            val resultMap = bridge.batchFindMethodsUsingStrings {
                queryMap(map)
            }

            val drmResult = resultMap["DrmResult"]!!
            assert(drmResult.size == 1)
            val drmResultDescriptor = drmResult.first()
            val drmResultMethod: Method = drmResultDescriptor.getMethodInstance(lpparam.classLoader)
            drmResultMethod.hookAfter {
                it.result = DrmManager.DrmResult.DRM_SUCCESS
            }
        }
    }

}
