package com.yuk.miuiXXL.hooks.modules.android.corepatch

import android.os.Build
import com.github.kyuubiran.ezxhelper.utils.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.IXposedHookZygoteInit.StartupParam
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

class CorePatchMainHook : IXposedHookLoadPackage, IXposedHookZygoteInit {
    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        if ("android" == lpparam.packageName && lpparam.processName == "android") {
            Log.d("Current sdk version " + Build.VERSION.SDK_INT)
            when (Build.VERSION.SDK_INT) {
                Build.VERSION_CODES.TIRAMISU -> CorePatchForT().handleLoadPackage(lpparam)
                Build.VERSION_CODES.S_V2 -> CorePatchForSv2().handleLoadPackage(lpparam)
                Build.VERSION_CODES.S -> CorePatchForS().handleLoadPackage(lpparam)
                Build.VERSION_CODES.R -> CorePatchForR().handleLoadPackage(lpparam)
                Build.VERSION_CODES.Q -> CorePatchForQ().handleLoadPackage(lpparam)
                else -> Log.ex("Warning: Unsupported Version of Android " + Build.VERSION.SDK_INT)
            }
        }
    }

    override fun initZygote(startupParam: StartupParam) {
        if (startupParam.startsSystemServer) {
            Log.d("Current sdk version " + Build.VERSION.SDK_INT)
            when (Build.VERSION.SDK_INT) {
                Build.VERSION_CODES.TIRAMISU -> CorePatchForT().initZygote(startupParam)
                Build.VERSION_CODES.S_V2 -> CorePatchForSv2().initZygote(startupParam)
                Build.VERSION_CODES.S -> CorePatchForS().initZygote(startupParam)
                Build.VERSION_CODES.R -> CorePatchForR().initZygote(startupParam)
                Build.VERSION_CODES.Q -> CorePatchForQ().initZygote(startupParam)
                else -> Log.ex("Warning: Unsupported Version of Android " + Build.VERSION.SDK_INT)
            }
        }
    }

}
