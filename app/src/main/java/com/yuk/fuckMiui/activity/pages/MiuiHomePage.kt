package com.yuk.fuckMiui.activity.pages

import android.widget.Toast
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import com.yuk.fuckMiui.R
import com.yuk.fuckMiui.utils.exec

@BMPage("MiuiHomePage", "MiuiHome", hideMenu = false)
class MiuiHomePage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.miuihome)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.double_tap_to_sleep, tipsId = R.string.double_tap_to_sleep_tips),
            SwitchV("home_double_tap_to_sleep", false)
        )
    }
}