package com.yuk.miuiXXL.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.miuiXXL.R

@BMPage("SystemUIPage", hideMenu = false)
class SystemUIPage : BasePage() {
    override fun getTitle(): String {
        setTitle(getString(R.string.systemui))
        return getString(R.string.systemui)
    }

    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.systemui_show_wifi_standard, tipsId = R.string.systemui_show_wifi_standard_summary),
            SwitchV("systemui_show_wifi_standard", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.systemui_lockscreen_show_current, tipsId = R.string.systemui_lockscreen_show_current_summary),
            SwitchV("systemui_lockscreen_show_current", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.systemui_statusbar_show_seconds, tipsId = R.string.systemui_statusbar_show_seconds_summary),
            SwitchV("systemui_statusbar_show_seconds", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.systemui_lockscreen_show_seconds, tipsId = R.string.systemui_lockscreen_show_seconds_summary),
            SwitchV("systemui_lockscreen_show_seconds", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.systemui_lockscreen_remove_minus, tipsId = R.string.systemui_lockscreen_remove_minus_summary),
            SwitchV("systemui_lockscreen_remove_minus", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.systemui_lockscreen_remove_camera, tipsId = R.string.systemui_lockscreen_remove_camera_summary),
            SwitchV("systemui_lockscreen_remove_camera", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.systemui_disable_bluetooth_restrict, tipsId = R.string.systemui_disable_bluetooth_restrict_summary
            ), SwitchV("systemui_disable_bluetooth_restrict", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.systemui_wave_charge_animation, tipsId = R.string.systemui_wave_charge_animation_summary
            ), SwitchV("systemui_wave_charge_animation", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.systemui_use_new_hd, tipsId = R.string.systemui_use_new_summary),
            SwitchV("systemui_use_new_hd", false)
        )
    }

}
