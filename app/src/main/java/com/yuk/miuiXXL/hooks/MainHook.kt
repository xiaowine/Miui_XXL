package com.yuk.miuiXXL.hooks

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.Log.logexIfThrow
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.hooks.modules.android.FuckValidateTheme
import com.yuk.miuiXXL.hooks.modules.android.corepatch.CorePatchMainHook
import com.yuk.miuiXXL.hooks.modules.miuihome.AnimDurationRatio
import com.yuk.miuiXXL.hooks.modules.miuihome.CategoryFeatures
import com.yuk.miuiXXL.hooks.modules.miuihome.DisableRecentViewWallpaperDarkening
import com.yuk.miuiXXL.hooks.modules.miuihome.DoubleTapToSleep
import com.yuk.miuiXXL.hooks.modules.miuihome.ModifyRecentViewRemoveCardAnimation
import com.yuk.miuiXXL.hooks.modules.miuihome.ModifyUnlockAnimation
import com.yuk.miuiXXL.hooks.modules.miuihome.ScrollIconName
import com.yuk.miuiXXL.hooks.modules.miuihome.SetDeviceLevel
import com.yuk.miuiXXL.hooks.modules.miuihome.TwoXOneIconRoundedCornerFollowing
import com.yuk.miuiXXL.hooks.modules.powerkeeper.DisableDynamicRefreshRate
import com.yuk.miuiXXL.hooks.modules.systemui.StatusbarShowSeconds
import com.yuk.miuiXXL.hooks.modules.thememanager.FuckTheme
import com.yuk.miuiXXL.hooks.modules.thememanager.RemoveAds
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage

private const val TAG = "FuckMiui"
private val PACKAGE_NAME_HOOKED = setOf("android", "com.android.systemui", "com.android.thememanager", "com.miui.home", "com.miui.powerkeeper")

class MainHook : IXposedHookLoadPackage, IXposedHookZygoteInit {

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        EzXHelperInit.initZygote(startupParam)
        CorePatchMainHook().initZygote(startupParam)
        initHooks(FuckValidateTheme)
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName in PACKAGE_NAME_HOOKED) {
            // Init EzXHelper
            EzXHelperInit.initHandleLoadPackage(lpparam)
            EzXHelperInit.setLogTag(TAG)
            EzXHelperInit.setToastTag(TAG)
            // Init hooks
            when (lpparam.packageName) {
                "android" -> {
                    CorePatchMainHook().handleLoadPackage(lpparam)
                    initHooks(
                        FuckValidateTheme,
                    )
                }

                "com.miui.home" -> {
                    initHooks(
                        SetDeviceLevel,
                        DoubleTapToSleep,
                        ScrollIconName,
                        AnimDurationRatio,
                        ModifyUnlockAnimation,
                        DisableRecentViewWallpaperDarkening,
                        ModifyRecentViewRemoveCardAnimation,
                        CategoryFeatures,
                        TwoXOneIconRoundedCornerFollowing,
                    )
                }

                "com.android.thememanager" -> {
                    initHooks(
                        RemoveAds,
                        FuckTheme,
                    )
                }

                "com.android.systemui" -> {
                    initHooks(
                        StatusbarShowSeconds,
                    )
                }

                "com.miui.powerkeeper" -> {
                    initHooks(
                        DisableDynamicRefreshRate,
                    )
                }

                else -> return
            }
        }
    }

    private fun initHooks(vararg hook: BaseHook) {
        hook.forEach {
            runCatching {
                if (it.isInit) return@forEach
                it.init()
                it.isInit = true
                Log.i("Inited hook: ${it.javaClass.simpleName}")
            }.logexIfThrow("Failed init hook: ${it.javaClass.simpleName}")
        }
    }

}
