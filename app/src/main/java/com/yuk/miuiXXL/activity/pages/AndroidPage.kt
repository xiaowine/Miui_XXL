package com.yuk.miuiXXL.activity.pages

import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import com.yuk.miuiXXL.R

@BMPage("AndroidPage", "Android", hideMenu = false)
class AndroidPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.android)
        Line()
        TitleText(textId = R.string.android_corepacth)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.android_corepacth_downgr, tipsId = R.string.android_corepacth_downgr_summary), SwitchV("downgrade")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.android_corepacth_authcreak, tipsId = R.string.android_corepacth_authcreak_summary), SwitchV("authcreak")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.android_corepacth_digestCreak, tipsId = R.string.android_corepacth_digestCreak_summary),
            SwitchV("digestCreak")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.android_corepacth_UsePreSig, tipsId = R.string.android_corepacth_UsePreSig_summary), SwitchV("UsePreSig")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.android_corepacth_enhancedMode, tipsId = R.string.android_corepacth_enhancedMode_summary),
            SwitchV("enhancedMode")
        )
        Line()
        TitleText(textId = R.string.other)
        TextSummaryArrow(
            TextSummaryV(textId = R.string.android_max_wallpaper_scale, onClickListener = {
                MIUIDialog(activity) {
                    setTitle(R.string.android_max_wallpaper_scale)
                    setMessage(
                        "${activity.getString(R.string.def)}1.2，${activity.getString(R.string.current)}${
                            safeSP.getFloat("android_max_wallpaper_scale", 1.2f)
                        }"
                    )
                    setEditText("", "建议输入范围：1.0-2.0")
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
    }

}
