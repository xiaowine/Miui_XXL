package com.yuk.miuiXXL.hooks.modules.miuihome

import android.app.AndroidAppHelper
import android.content.ComponentName
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import com.yuk.miuiXXL.R
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.callMethod
import com.yuk.miuiXXL.utils.callStaticMethod
import com.yuk.miuiXXL.utils.findClass
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.getStaticObjectField
import com.yuk.miuiXXL.utils.hookAfterAllMethods
import com.yuk.miuiXXL.utils.hookBeforeMethod
import com.yuk.miuiXXL.utils.setStaticObjectField
import de.robv.android.xposed.XposedHelpers

object ShortcutAddSmallWindow : BaseHook() {
    override fun init() {

        if (!getBoolean("miuihome_shortcut_add_small_window", false)) return
        val mViewDarkModeHelper = ("com.miui.home.launcher.util.ViewDarkModeHelper").findClass()
        val mSystemShortcutMenu = ("com.miui.home.launcher.shortcuts.SystemShortcutMenu").findClass()
        val mSystemShortcutMenuItem = ("com.miui.home.launcher.shortcuts.SystemShortcutMenuItem").findClass()
        val mAppShortcutMenu = ("com.miui.home.launcher.shortcuts.AppShortcutMenu").findClass()
        val mShortcutMenuItem = ("com.miui.home.launcher.shortcuts.ShortcutMenuItem").findClass()
        val mAppDetailsShortcutMenuItem = ("com.miui.home.launcher.shortcuts.SystemShortcutMenuItem\$AppDetailsShortcutMenuItem").findClass()
        val mActivityUtilsCompat = ("com.miui.launcher.utils.ActivityUtilsCompat").findClass()

        mViewDarkModeHelper.hookAfterAllMethods("onConfigurationChanged") {
            mSystemShortcutMenuItem.callStaticMethod("createAllSystemShortcutMenuItems")
        }

        mShortcutMenuItem.hookAfterAllMethods("getShortTitle") {
            if (it.result == "应用信息") {
                it.result = "信息"
            }
        }

        mAppDetailsShortcutMenuItem.hookBeforeMethod("lambda\$getOnClickListener$0", mAppDetailsShortcutMenuItem, View::class.java) {
            val obj = it.args[0]
            val view: View = it.args[1] as View
            val mShortTitle = obj.callMethod("getShortTitle") as CharSequence
            if (mShortTitle == moduleRes.getString(R.string.miuihome_shortcut_add_small_window_title)) {
                it.result = null
                val intent = Intent()
                val mComponentName = obj.callMethod("getComponentName") as ComponentName
                intent.action = "android.intent.action.MAIN"
                intent.addCategory("android.intent.category.LAUNCHER")
                intent.component = mComponentName
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val callStaticMethod = mActivityUtilsCompat.callStaticMethod("makeFreeformActivityOptions", view.context, mComponentName.packageName)
                if (callStaticMethod != null) {
                    view.context.startActivity(intent, callStaticMethod.callMethod("toBundle") as Bundle)
                }
            }
        }

        mSystemShortcutMenu.hookAfterAllMethods("getMaxShortcutItemCount") {
            it.result = 5
        }

        mAppShortcutMenu.hookAfterAllMethods("getMaxShortcutItemCount") {
            it.result = 5
        }

        mSystemShortcutMenuItem.hookAfterAllMethods("createAllSystemShortcutMenuItems") {
            val isDarkMode =
                AndroidAppHelper.currentApplication().applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
            val mAllSystemShortcutMenuItems = mSystemShortcutMenuItem.getStaticObjectField("sAllSystemShortcutMenuItems") as Collection<Any>
            val mSmallWindowInstance = XposedHelpers.newInstance(mAppDetailsShortcutMenuItem)
            mSmallWindowInstance.callMethod("setShortTitle", moduleRes.getString(R.string.miuihome_shortcut_add_small_window_title))
            mSmallWindowInstance.callMethod(
                "setIconDrawable",
                if (isDarkMode) moduleRes.getDrawable(R.drawable.ic_small_window_dark) else moduleRes.getDrawable(R.drawable.ic_small_window_light)
            )
            val sAllSystemShortcutMenuItems = ArrayList<Any>()
            sAllSystemShortcutMenuItems.add(mSmallWindowInstance)
            sAllSystemShortcutMenuItems.addAll(mAllSystemShortcutMenuItems)
            mSystemShortcutMenuItem.setStaticObjectField("sAllSystemShortcutMenuItems", sAllSystemShortcutMenuItems)
        }
    }

}
