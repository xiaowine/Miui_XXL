package com.yuk.miuiXXL.hooks.modules.android.corepatch

import com.yuk.miuiXXL.utils.prefs
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

open class CorePatchForS : CorePatchForR() {
    override fun handleLoadPackage(loadPackageParam: LoadPackageParam) {
        super.handleLoadPackage(loadPackageParam)
        if (prefs().getBoolean("digestCreak", true) && prefs().getBoolean("UsePreSig", false)) {
            findAndHookMethod("com.android.server.pm.PackageManagerService",
                loadPackageParam.classLoader,
                "doesSignatureMatchForPermissions",
                String::class.java,
                "com.android.server.pm.parsing.pkg.ParsedPackage",
                Int::class.javaPrimitiveType,
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun afterHookedMethod(param: MethodHookParam) {
                        //If we decide to crack this then at least make sure they are same apks, avoid another one that tries to impersonate.
                        if (param.result == false) {
                            val pPkgName = XposedHelpers.callMethod(param.args[1], "getPackageName") as String
                            if (pPkgName.contentEquals(param.args[0] as String)) {
                                param.result = true
                            }
                        }
                    }
                })
        }
    }

}
