package com.yuk.miuiXXL.hooks.modules.fileexplorer

import android.widget.TextView
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.getObject
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean


object SelectName : BaseHook() {
    override fun init() {
        if (!getBoolean("file_explorer_can_selectable", false) && !getBoolean("file_explorer_is_single_line", false)) return

        findMethod("com.android.fileexplorer.view.FileListItem") { name == "onFinishInflate" }.hookAfter {
            (it.thisObject.getObject("mFileNameTextView") as TextView).apply {
                setTextIsSelectable(getBoolean("file_explorer_can_selectable", false))
                isSingleLine = getBoolean("file_explorer_is_single_line", false)
            }
        }
    }

}