package com.yuk.fuckMiui.hooks

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.Log.logexIfThrow
import com.yuk.fuckMiui.hooks.modules.BaseHook
import com.yuk.fuckMiui.hooks.modules.home.DoubleTapToSleep
import com.yuk.fuckMiui.hooks.modules.home.SetDeviceLevel
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

private const val TAG = "FuckMiui"
private val PACKAGE_NAME_HOOKED = setOf("com.miui.home")

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName in PACKAGE_NAME_HOOKED) {
            // Init EzXHelper
            EzXHelperInit.initHandleLoadPackage(lpparam)
            EzXHelperInit.setLogTag(TAG)
            EzXHelperInit.setToastTag(TAG)
            // Init hooks
            when (lpparam.packageName) {
                "com.miui.home" -> {
                    initHooks(
                        SetDeviceLevel,
                        DoubleTapToSleep,
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
