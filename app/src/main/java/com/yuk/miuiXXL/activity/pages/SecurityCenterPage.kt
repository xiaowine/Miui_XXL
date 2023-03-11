package com.yuk.miuiXXL.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextV
import com.yuk.miuiXXL.R

@BMPage("SecurityCenterPage", hideMenu = false)
class SecurityCenterPage : BasePage() {
    override fun getTitle(): String {
        setTitle(getString(R.string.securitycenter))
        return getString(R.string.securitycenter)
    }

    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.securitycenter_skip_warning_wait_time, tipsId = R.string.securitycenter_skip_warning_wait_time_summary),
            SwitchV("securitycenter_skip_warning_wait_time", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.securitycenter_show_battery_temperature, tipsId = R.string.securitycenter_show_battery_temperature_summary),
            SwitchV("securitycenter_show_battery_temperature", false)
        )
        TextWithSwitch(
            TextV(textId = R.string.securitycenter_remove_macro_blacklist,),
            SwitchV("securitycenter_remove_macro_blacklist", false)
        )
    }

}
