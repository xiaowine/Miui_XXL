package com.yuk.miuiXXL.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.miuiXXL.R

@BMPage("UpdaterPage", "Updater", hideMenu = false)
class UpdaterPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.updater)
        Line()
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.updater_fuck_vab_update, tipsId = R.string.updater_fuck_vab_update_summary),
            SwitchV("updater_fuck_vab_update", false)
        )
    }

}
