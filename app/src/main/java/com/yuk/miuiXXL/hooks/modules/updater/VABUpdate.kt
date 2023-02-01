package com.yuk.miuiXXL.hooks.modules.updater

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean

object VABUpdate : BaseHook() {
    override fun init() {

        if (!getBoolean("updater_fuck_vab_update", false)) return
        findMethod("miui.util.FeatureParser") {
            name == "hasFeature" && parameterCount == 2
        }.hookBefore {
            if (it.args[0] == "support_ota_validate") {
                it.result = false
            }
        }
    }

}
