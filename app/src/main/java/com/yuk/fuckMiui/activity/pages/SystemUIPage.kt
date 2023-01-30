package com.yuk.fuckMiui.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.fuckMiui.R

@BMPage("SystemUIPage", "SystemUI", hideMenu = false)
class SystemUIPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.systemui)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.systemui_statusbar_show_seconds, tipsId = R.string.systemui_statusbar_show_seconds_tips),
            SwitchV("systemui_statusbar_show_seconds", false)
        )
    }
}