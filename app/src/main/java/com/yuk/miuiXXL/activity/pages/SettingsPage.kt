package com.yuk.miuiXXL.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.miuiXXL.R

@BMPage("SettingsPage", hideMenu = false)
class SettingsPage : BasePage() {
    override fun getTitle(): String {
        setTitle(getString(R.string.settings))
        return getString(R.string.settings)
    }

    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.settings_notification_importance, tipsId = R.string.settings_notification_importance_summary),
            SwitchV("settings_notification_importance", false)
        )
    }

}
