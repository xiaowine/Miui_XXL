package com.yuk.miuiXXL.hooks.modules.miuihome

import android.view.View
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.callMethod
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.getObjectField

object HideSeekPoint : BaseHook() {
    override fun init() {

        if (!getBoolean("miuihome_hide_seek_point", false)) return
        findMethod("com.miui.home.launcher.ScreenView") { name == "updateSeekPoints" }.hookAfter {
            showSeekBar(it.thisObject as View)
        }
        findMethod("com.miui.home.launcher.ScreenView") { name == "addView" }.hookAfter {
            showSeekBar(it.thisObject as View)
        }
        findMethod("com.miui.home.launcher.ScreenView") { name == "removeScreen" }.hookAfter {
            showSeekBar(it.thisObject as View)
        }
        findMethod("com.miui.home.launcher.ScreenView") { name == "removeScreensInLayout" }.hookAfter {
            showSeekBar(it.thisObject as View)
        }
    }

    private fun showSeekBar(view: View) {
        if ("Workspace" != view.javaClass.simpleName) return
        val mScreenSeekBar = view.getObjectField("mScreenSeekBar") as View
        val isInEditingMode = view.callMethod("isInNormalEditingMode") as Boolean
        if (!isInEditingMode) {
            mScreenSeekBar.animate().cancel()
            mScreenSeekBar.visibility = View.GONE
        }

    }
}
