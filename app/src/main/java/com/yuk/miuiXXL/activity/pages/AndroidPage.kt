package com.yuk.miuiXXL.activity.pages

import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import com.yuk.miuiXXL.R

@BMPage("AndroidPage", hideMenu = false)
class AndroidPage : BasePage() {
    override fun getTitle(): String {
        setTitle(getString(R.string.android))
        return getString(R.string.android)
    }

    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.android_remove_small_window_restriction, tipsId = R.string.android_remove_small_window_restriction_summary),
            SwitchV("android_remove_small_window_restriction")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.android_remove_screenshot_restriction, tipsId = R.string.android_remove_screenshot_restriction_summary),
            SwitchV("android_remove_screenshot_restriction")
        )
        TextSummaryWithArrow(
            TextSummaryV(textId = R.string.android_max_wallpaper_scale, tipsId = R.string.android_max_wallpaper_scale_summary, onClickListener = {
                MIUIDialog(activity) {
                    setTitle(R.string.android_max_wallpaper_scale)
                    setMessage(
                        "${activity.getString(R.string.def)}1.2，${activity.getString(R.string.current)}${
                            safeSP.getFloat("android_max_wallpaper_scale", 1.2f)
                        }"
                    )
                    setEditText("", "${activity.getString(R.string.scope)}1.0-2.0")
                    setLButton(textId = R.string.cancel) {
                        dismiss()
                    }
                    setRButton(textId = R.string.done) {
                        if (getEditText() != "") {
                            safeSP.putAny("android_max_wallpaper_scale", getEditText().toFloat())
                        }
                        dismiss()
                    }
                }.show()
            })
        )
        Line()
        TitleText(textId = R.string.android_corepacth)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.android_corepacth_downgr, tipsId = R.string.android_corepacth_downgr_summary),
            SwitchV("downgrade")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.android_corepacth_authcreak, tipsId = R.string.android_corepacth_authcreak_summary),
            SwitchV("authcreak")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.android_corepacth_digestCreak, tipsId = R.string.android_corepacth_digestCreak_summary),
            SwitchV("digestCreak")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.android_corepacth_UsePreSig, tipsId = R.string.android_corepacth_UsePreSig_summary),
            SwitchV("UsePreSig")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.android_corepacth_enhancedMode, tipsId = R.string.android_corepacth_enhancedMode_summary),
            SwitchV("enhancedMode")
        )
    }

}
