package com.yuk.miuiXXL.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.miuiXXL.R

@BMPage("FileExplorerPage", hideMenu = false)
class FileExplorer : BasePage() {
    override fun getTitle(): String {
        setTitle(getString(R.string.file_explorer))
        return getString(R.string.file_explorer)
    }

    override fun onCreate() {
        TextSSw(getString(R.string.file_explorer_can_selectable), key = "file_explorer_can_selectable")
        TextSSw(getString(R.string.file_explorer_is_single_line), key = "file_explorer_is_single_line")
    }

}
