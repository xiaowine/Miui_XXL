package com.yuk.fuckMiui.hooks

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.Log.logexIfThrow
import com.yuk.fuckMiui.hooks.modules.BaseHook
import com.yuk.fuckMiui.hooks.modules.android.FuckValidateTheme
import com.yuk.fuckMiui.hooks.modules.miuihome.AnimDurationRatio
import com.yuk.fuckMiui.hooks.modules.miuihome.DisableRecentViewWallpaperDarkening
import com.yuk.fuckMiui.hooks.modules.miuihome.DoubleTapToSleep
import com.yuk.fuckMiui.hooks.modules.miuihome.ModifyRecentViewRemoveCardAnimation
import com.yuk.fuckMiui.hooks.modules.miuihome.ModifyUnlockAnimation
import com.yuk.fuckMiui.hooks.modules.miuihome.ScrollIconName
import com.yuk.fuckMiui.hooks.modules.miuihome.SetDeviceLevel
import com.yuk.fuckMiui.hooks.modules.powerkeeper.DisableDynamicRefreshRate
import com.yuk.fuckMiui.hooks.modules.systemui.StatusbarShowSeconds
import com.yuk.fuckMiui.hooks.modules.thememanager.FuckTheme
import com.yuk.fuckMiui.hooks.modules.thememanager.RemoveAds
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage

private const val TAG = "FuckMiui"
private val PACKAGE_NAME_HOOKED = setOf("android", "com.android.systemui", "com.android.thememanager", "com.miui.home", "com.miui.powerkeeper")

class MainHook : IXposedHookLoadPackage, IXposedHookZygoteInit {

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        EzXHelperInit.initZygote(startupParam)
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
