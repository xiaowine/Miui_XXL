package com.yuk.miuiXXL.hooks.modules.systemui

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.getObjectAs
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.github.kyuubiran.ezxhelper.utils.putObject
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean

object ShowWifiStandard : BaseHook() {
    override fun init() {

        if (!getBoolean("systemui_show_wifi_standard", false)) return
        findMethod("com.android.systemui.statusbar.StatusBarWifiView") {
            name == "initViewState" && parameterCount == 1
        }.hookBefore {
            findMethod("com.android.systemui.statusbar.phone.StatusBarSignalPolicy\$WifiIconState") {
                name == "copyTo" && parameterCount == 1
            }.hookBefore {
                val wifiStandard = it.thisObject.getObjectAs<Int>("wifiStandard")
                it.thisObject.putObject("showWifiStandard", wifiStandard != 0)
            }
        }
        findMethod("com.android.systemui.statusbar.StatusBarWifiView") {
            name == "updateState" && parameterCount == 1
        }.hookBefore {
            findMethod("com.android.systemui.statusbar.phone.StatusBarSignalPolicy\$WifiIconState") {
                name == "copyTo" && parameterCount == 1
            }.hookBefore {
                val wifiStandard = it.thisObject.getObjectAs<Int>("wifiStandard")
                it.thisObject.putObject("showWifiStandard", wifiStandard != 0)

            }
        }
    }

}
