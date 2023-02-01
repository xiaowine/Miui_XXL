package com.yuk.miuiXXL.activity.pages

import cn.fkj233.ui.activity.annotation.BMMainPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.miuiXXL.R

@BMMainPage("Miui XXL")
class MainPage : BasePage() {
    override fun onCreate() {
        TextSummaryArrow(TextSummaryV(textId = R.string.android, onClickListener = { showFragment("AndroidPage") }))
        TextSummaryArrow(TextSummaryV(textId = R.string.systemui, onClickListener = { showFragment("SystemUIPage") }))
        TextSummaryArrow(TextSummaryV(textId = R.string.miuihome, onClickListener = { showFragment("MiuiHomePage") }))
        TextSummaryArrow(TextSummaryV(textId = R.string.updater, onClickListener = { showFragment("UpdaterPage") }))
        TextSummaryArrow(TextSummaryV(textId = R.string.securitycenter, onClickListener = { showFragment("SecurityCenterPage") }))
        TextSummaryArrow(TextSummaryV(textId = R.string.thememanager, onClickListener = { showFragment("ThemeManagerPage") }))
        TextSummaryArrow(TextSummaryV(textId = R.string.powerkeeper, onClickListener = { showFragment("PowerKeeperPage") }))
    }

}
