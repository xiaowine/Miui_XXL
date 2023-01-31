package com.yuk.miuiXXL.hooks.modules.systemui

import android.app.AndroidAppHelper
import android.content.Context
import android.os.BatteryManager
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.getObject
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.yuk.miuiXXL.R
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean
import kotlin.math.abs

object LockScreenShowCurrent : BaseHook() {
    override fun init() {

        if (!getBoolean("systemui_lockscreen_show_current", false)) return
        findMethod("com.android.keyguard.charge.ChargeUtils") {
            name == "getChargingHintText" && parameterCount == 3
        }.hookAfter {
            it.result = getCurrent() + "\n" + it.result
        }

        findMethod("com.android.systemui.statusbar.phone.KeyguardBottomAreaView") {
            name == "onFinishInflate"
        }.hookAfter {
            val mIndicationText = it.thisObject.getObject("mIndicationText") as TextView
            mIndicationText.isSingleLine = false
        }
    }

    private fun getCurrent(): String {
        val batteryManager = AndroidAppHelper.currentApplication().getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val current = abs(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW) / 1000)
        return moduleRes.getString(R.string.systemui_lockscreen_show_current_title) + current + "mA"
    }

}
