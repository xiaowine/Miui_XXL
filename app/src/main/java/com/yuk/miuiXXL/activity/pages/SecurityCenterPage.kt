package com.yuk.miuiXXL.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.miuiXXL.R

@BMPage("SecurityCenterPage", "SecurityCenter", hideMenu = false)
class SecurityCenterPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.securitycenter)
        Line()
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.securitycenter_skip_warning_wait_time, tipsId = R.string.securitycenter_skip_warning_wait_time_summary),
            SwitchV("securitycenter_skip_warning_wait_time", false)
        )
    }

}
