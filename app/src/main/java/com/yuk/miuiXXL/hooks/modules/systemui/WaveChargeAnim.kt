package com.yuk.miuiXXL.hooks.modules.systemui

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.putObject
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean

object WaveChargeAnim : BaseHook() {
    override fun init() {

        if (!getBoolean("systemui_wave_charge_animation", false)) return
        findMethod("com.android.keyguard.charge.ChargeUtils") {
            name == "supportWaveChargeAnimation"
        }.hookAfter {
            val stackTrace = Throwable().stackTrace
            var mResult = false
            val classTrue = setOf("com.android.keyguard.charge.ChargeUtils", "com.android.keyguard.charge.container.MiuiChargeContainerView")
            for (i in stackTrace.indices) {
                when (stackTrace[i].className) {
                    in classTrue -> {
                        mResult = true
                        break
                    }
                }
            }
            it.result = mResult
        }
        findMethod("com.android.keyguard.charge.wave.WaveView") {
            name == "updateWaveHeight"
        }.hookAfter {
            it.thisObject.putObject("mWaveXOffset", 0)
        }

    }

}
