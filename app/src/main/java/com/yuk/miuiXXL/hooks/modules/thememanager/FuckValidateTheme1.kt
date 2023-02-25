package com.yuk.miuiXXL.hooks.modules.thememanager

import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.findAllMethods
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.github.kyuubiran.ezxhelper.utils.putObject
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean

object FuckValidateTheme1 : BaseHook() {
    override fun init() {

        if (!getBoolean("thememanager_fuck_validate_theme", false)) return
        try {
            findAllMethods("com.android.thememanager.detail.theme.model.OnlineResourceDetail") {
                name == "toResource"
            }.hookAfter {
                it.thisObject.putObject("bought", true)
            }
        } catch (t: Throwable) {
            Log.ex(t)
        }

        try {
            findAllMethods("com.android.thememanager.basemodule.views.DiscountPriceView") {
                parameterCount == 2 && parameterTypes[0] == Int::class.javaPrimitiveType && parameterTypes[1] == Int::class.javaPrimitiveType
            }.hookBefore {
                it.args[0] = 0
                it.args[1] = 0
            }
        } catch (t: Throwable) {
            Log.ex(t)
        }
    }

}
