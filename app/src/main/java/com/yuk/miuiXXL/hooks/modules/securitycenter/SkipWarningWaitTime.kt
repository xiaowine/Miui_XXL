package com.yuk.miuiXXL.hooks.modules.securitycenter

import android.widget.TextView
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.hookBeforeMethod

object SkipWarningWaitTime : BaseHook() {
    override fun init() {

        if (!getBoolean("securitycenter_skip_warning_wait_time", false)) return
        TextView::class.java.hookBeforeMethod(
            "setText", CharSequence::class.java, TextView.BufferType::class.java, Boolean::class.java, Int::class.java
        ) {
            if (getBoolean("securitycenter_skip_warning_wait_time", false)) {
                if (it.args.isNotEmpty() && it.args[0]?.toString()?.startsWith("确定(") == true) {
                    it.args[0] = "确定"
                }
            }
        }
        TextView::class.java.hookBeforeMethod(
            "setEnabled", Boolean::class.java
        ) {
            if (getBoolean("securitycenter_skip_warning_wait_time", false)) {
                it.args[0] = true
            }
        }
    }

}
