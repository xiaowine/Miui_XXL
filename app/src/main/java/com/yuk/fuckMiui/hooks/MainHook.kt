package com.yuk.fuckMiui.hooks

import android.app.Application
import android.content.Context
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.Log.logexIfThrow
import com.yuk.fuckMiui.hooks.modules.BaseHook
import com.yuk.fuckMiui.hooks.modules.android.FuckValidateTheme
import com.yuk.fuckMiui.hooks.modules.home.DoubleTapToSleep
import com.yuk.fuckMiui.hooks.modules.home.SetDeviceLevel
import com.yuk.fuckMiui.hooks.modules.thememanager.FuckTheme
import com.yuk.fuckMiui.hooks.modules.thememanager.RemoveAds
import com.yuk.fuckMiui.utils.hookBeforeMethod
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage

private const val TAG = "FuckMiui"
private val PACKAGE_NAME_HOOKED = setOf("com.miui.home", "com.android.thememanager", "android")

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
                        SetDeviceLevel, DoubleTapToSleep
                    )
                }

                "com.android.thememanager" -> {
                    initHooks(
                        RemoveAds,
                        FuckTheme,
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
