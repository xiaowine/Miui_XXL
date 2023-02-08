package com.yuk.miuiXXL.hooks.modules.systemui

import android.content.Context
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.hookBeforeMethod

object DisableBluetoothRestrict : BaseHook() {
    override fun init() {

        if (!getBoolean("systemui_disable_bluetooth_restrict", false)) return
        "com.android.settingslib.bluetooth.LocalBluetoothAdapter".hookBeforeMethod("isSupportBluetoothRestrict", Context::class.java) {
            it.result = false
        }
    }

}
