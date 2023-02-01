package com.yuk.miuiXXL.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.miuiXXL.R

@BMPage("UpdaterPage", hideMenu = false)
class UpdaterPage : BasePage() {
    override fun getTitle(): String {
        setTitle(getString(R.string.updater))
        return getString(R.string.updater)
    }

    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.updater_fuck_vab_update, tipsId = R.string.updater_fuck_vab_update_summary),
            SwitchV("updater_fuck_vab_update", false)
        )
    }

}
