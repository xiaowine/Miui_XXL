package com.yuk.fuckMiui.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.fuckMiui.R

@BMPage("PowerKeeperPage", "PowerKeeper", hideMenu = false)
class PowerKeeperPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.powerkeeper)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.powerkeeper_disable_dynamic_refresh_rate, tipsId = R.string.powerkeeper_disable_dynamic_refresh_rate_summary),
            SwitchV("powerkeeper_disable_dynamic_refresh_rate", false)
        )
    }

}
