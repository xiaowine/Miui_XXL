package com.yuk.miuiXXL.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.miuiXXL.R

@BMPage("PowerKeeperPage", "PowerKeeper", hideMenu = false)
class PowerKeeperPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.powerkeeper)
        Line()
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.powerkeeper_disable_dynamic_refresh_rate, tipsId = R.string.powerkeeper_disable_dynamic_refresh_rate_summary),
            SwitchV("powerkeeper_disable_dynamic_refresh_rate", false)
        )
    }

}
