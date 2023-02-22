package com.yuk.miuiXXL.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextV
import com.yuk.miuiXXL.R

@BMPage("PackageInstallerPage", hideMenu = false)
class PackageInstallerPage : BasePage() {
    override fun getTitle(): String {
        setTitle(getString(R.string.packageinstaller))
        return getString(R.string.packageinstaller)
    }

    override fun onCreate() {
        TextWithSwitch(
            TextV(textId = R.string.packageinstaller_remove_ads),
            SwitchV("packageinstaller_remove_ads", false)
        )
        TextWithSwitch(
            TextV(textId = R.string.packageinstaller_allow_update_system_app),
            SwitchV("packageinstaller_allow_update_system_app", false)
        )
        TextWithSwitch(
            TextV(textId = R.string.packageinstaller_show_more_apk_info),
            SwitchV("packageinstaller_show_more_apk_info", false)
        )
        TextWithSwitch(
            TextV(textId = R.string.packageinstaller_disable_count_check),
            SwitchV("packageinstaller_disable_count_check", false)
        )
    }

}
