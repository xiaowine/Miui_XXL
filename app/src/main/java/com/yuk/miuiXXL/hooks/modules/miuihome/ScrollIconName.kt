package com.yuk.miuiXXL.hooks.modules.miuihome

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.utils.Log
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.callMethod
import com.yuk.miuiXXL.utils.findClass
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.getObjectField
import com.yuk.miuiXXL.utils.hookAfterMethod

object ScrollIconName : BaseHook() {
    @SuppressLint("DiscouragedApi")
    override fun init() {

        if (!getBoolean("miuihome_scroll_icon_name", false)) return
        val launcherClass = "com.miui.home.launcher.Launcher".findClass()
        val shortcutInfoClass = "com.miui.home.launcher.ShortcutInfo".findClass()

        try {
            "com.miui.home.launcher.ItemIcon".hookAfterMethod(
                "onFinishInflate"
            ) {
                val mTitle = it.thisObject.getObjectField("mTitle") as TextView
                mTitleScrolling(mTitle)
            }
            "com.miui.home.launcher.maml.MaMlWidgetView".hookAfterMethod(
                "onFinishInflate"
            ) {
                val mTitle = it.thisObject.getObjectField("mTitleTextView") as TextView
                mTitleScrolling(mTitle)
            }
            "com.miui.home.launcher.LauncherMtzGadgetView".hookAfterMethod(
                "onFinishInflate"
            ) {
                val mTitle = it.thisObject.getObjectField("mTitleTextView") as TextView
                mTitleScrolling(mTitle)
            }
            "com.miui.home.launcher.LauncherWidgetView".hookAfterMethod(
                "onFinishInflate"
            ) {
                val mTitle = it.thisObject.getObjectField("mTitleTextView") as TextView
                mTitleScrolling(mTitle)
            }
            "com.miui.home.launcher.ShortcutIcon".hookAfterMethod(
                "fromXml", Int::class.javaPrimitiveType, launcherClass, ViewGroup::class.java, shortcutInfoClass
            ) {
                val buddyIconView = it.args[3].callMethod("getBuddyIconView", it.args[2]) as View
                val mTitle = buddyIconView.getObjectField("mTitle") as TextView
                mTitleScrolling(mTitle)
            }
            "com.miui.home.launcher.ShortcutIcon".hookAfterMethod(
                "createShortcutIcon", Int::class.javaPrimitiveType, launcherClass, ViewGroup::class.java
            ) {
                val buddyIcon = it.result as View
                val mTitle = buddyIcon.getObjectField("mTitle") as TextView
                mTitleScrolling(mTitle)
            }
            "com.miui.home.launcher.common.Utilities".hookAfterMethod(
                "adaptTitleStyleToWallpaper", Context::class.java, TextView::class.java, Int::class.javaPrimitiveType, Int::class.javaPrimitiveType
            ) {
                val mTitle = it.args[1] as TextView
                if (mTitle.id == mTitle.resources.getIdentifier("icon_title", "id", "com.miui.home")) {
                    mTitleScrolling(mTitle)
                }
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
    }

    private fun mTitleScrolling(mTitle: TextView) {
        mTitle.ellipsize = TextUtils.TruncateAt.MARQUEE
        mTitle.isHorizontalFadingEdgeEnabled = true
        mTitle.setSingleLine()
        mTitle.marqueeRepeatLimit = -1
        mTitle.isSelected = true
        mTitle.setHorizontallyScrolling(true)
    }

}
