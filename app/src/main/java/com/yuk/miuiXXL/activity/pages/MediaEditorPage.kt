package com.yuk.miuiXXL.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.miuiXXL.R

@BMPage("MediaEditorPage", hideMenu = false)
class MediaEditorPage : BasePage() {
    override fun getTitle(): String {
        setTitle(getString(R.string.mediaeditor))
        return getString(R.string.mediaeditor)
    }

    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.mediaeditor_remove_crop_restriction, tipsId = R.string.mediaeditor_remove_crop_restriction_summary),
            SwitchV("mediaeditor_remove_crop_restriction", false)
        )
    }

}
