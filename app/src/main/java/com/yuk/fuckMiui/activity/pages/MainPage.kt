package com.yuk.fuckMiui.activity.pages

import cn.fkj233.ui.activity.annotation.BMMainPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.fuckMiui.R

@BMMainPage("FuckMiui")
class MainPage : BasePage() {
    override fun onCreate() {
        TextSummaryArrow(TextSummaryV(textId = R.string.miuihome, onClickListener = { showFragment("MiuiHomePage") }))
        TextSummaryArrow(TextSummaryV(textId = R.string.thememanager, onClickListener = { showFragment("ThemeManagerPage") }))
    }
}