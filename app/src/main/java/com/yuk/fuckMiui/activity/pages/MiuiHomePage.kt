package com.yuk.fuckMiui.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.fuckMiui.R

@BMPage("MiuiHomePage", "MiuiHome", hideMenu = false)
class MiuiHomePage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.miuihome)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_double_tap_to_sleep, tipsId = R.string.miuihome_double_tap_to_sleep_tips),
            SwitchV("miuihome_double_tap_to_sleep", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_highend_device, tipsId = R.string.miuihome_highend_device_tips),
            SwitchV("miuihome_highend_device", true)
        )
    }
}