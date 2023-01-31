package com.yuk.fuckMiui.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.fuckMiui.R

@BMPage("AndroidPage", "Android", hideMenu = false)
class AndroidPage : BasePage() {
    override fun onCreate() {
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
            TextSummaryV(
                textId = R.string.android_corepacth_enhancedMode, tipsId = R.string.android_corepacth_enhancedMode_summary
            ), SwitchV("enhancedMode")
        )
    }

}
