package com.yuk.miuiXXL.hooks.modules.systemui

import android.view.View
import android.widget.LinearLayout
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.getObjectField
import com.yuk.miuiXXL.utils.hookAfterMethod
import com.yuk.miuiXXL.utils.hookBeforeMethod

object RemoveLockScreenCamera : BaseHook() {
    override fun init() {

        if (!getBoolean("systemui_lockscreen_remove_camera", false)) return
        "com.android.systemui.statusbar.phone.KeyguardBottomAreaView".hookAfterMethod("onFinishInflate") {
            (it.thisObject.getObjectField("mRightAffordanceViewLayout") as LinearLayout).visibility = View.GONE
        }
        "com.android.keyguard.KeyguardMoveRightController".hookBeforeMethod("onTouchMove", Float::class.java, Float::class.java) {
            it.result = false
        }
        "com.android.keyguard.KeyguardMoveRightController".hookBeforeMethod("reset") {
            it.result = null

        }
    }

}
