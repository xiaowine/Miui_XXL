package com.yuk.miuiXXL.hooks.modules.thememanager

import com.github.kyuubiran.ezxhelper.utils.findField
import com.github.kyuubiran.ezxhelper.utils.getObject
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.github.kyuubiran.ezxhelper.utils.invokeMethod
import com.github.kyuubiran.ezxhelper.utils.loadClass
import com.yuk.miuiXXL.utils.getBoolean
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.luckypray.dexkit.DexKitBridge
import miui.drm.DrmManager
import java.io.File
import java.lang.reflect.Method

class FuckValidateTheme2 : IXposedHookLoadPackage {

    @Throws(NoSuchMethodException::class)
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (!getBoolean("thememanager_fuck_validate_theme", false)) return
        System.loadLibrary("dexkit")
        DexKitBridge.create(lpparam.appInfo.sourceDir)?.use { bridge ->

            val map = mapOf(
                "DrmResult" to setOf("theme", "ThemeManagerTag", "/system"),
                "LargeIcon" to setOf("apply failed", "/data/system/theme/large_icons/", "default_large_icon_product_id"),
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

            val largeIcon = resultMap["LargeIcon"]!!
            assert(largeIcon.size == 1)
            val largeIconDescriptor = largeIcon.first()
            val largeIconMethod: Method = largeIconDescriptor.getMethodInstance(lpparam.classLoader)
            largeIconMethod.hookBefore {
                val resource = findField(it.thisObject.javaClass) {
                    type == loadClass("com.android.thememanager.basemodule.resource.model.Resource", lpparam.classLoader)
                }
                val productId = it.thisObject.getObject(resource.name).invokeMethod("getProductId").toString()
                File("/storage/emulated/0/Android/data/com.android.thememanager/files/MIUI/theme/.data/rights/theme/${productId}-largeicons.mra").createNewFile()
            }
        }
    }

}
