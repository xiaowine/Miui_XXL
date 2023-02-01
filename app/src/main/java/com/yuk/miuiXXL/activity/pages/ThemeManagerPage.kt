package com.yuk.miuiXXL.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.miuiXXL.R

@BMPage("ThemeManagerPage", "ThemeManager", hideMenu = false)
class ThemeManagerPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.thememanager)
        Line()
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.thememanager_remove_ads, tipsId = R.string.thememanager_remove_ads_summary),
            SwitchV("thememanager_remove_ads", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.thememanager_fuck_validate_theme, tipsId = R.string.thememanager_fuck_validate_theme_summary),
            SwitchV("thememanager_fuck_validate_theme", false)
        )
    }

}