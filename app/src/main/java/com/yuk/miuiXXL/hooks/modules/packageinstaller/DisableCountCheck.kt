package com.yuk.miuiXXL.hooks.modules.packageinstaller

import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.findClass
import com.yuk.miuiXXL.utils.getBoolean

object DisableCountCheck : BaseHook() {
    override fun init() {

        if (!getBoolean("packageinstaller_disable_count_check", false)) return
        val riskControlRulesClass = "com.miui.packageInstaller.model.RiskControlRules".findClass()

        try {
            findMethod(riskControlRulesClass) {
                name == "getCurrentLevel"
            }.hookReturnConstant(0)
        } catch (t: Throwable) {
            Log.ex(t)
        }

    }

}
