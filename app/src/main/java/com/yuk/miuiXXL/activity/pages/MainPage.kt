package com.yuk.miuiXXL.activity.pages

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.annotation.BMMainPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextV
import cn.fkj233.ui.dialog.MIUIDialog
import com.yuk.miuiXXL.R
import com.yuk.miuiXXL.activity.MainActivity
import com.yuk.miuiXXL.utils.BackupUtils
import com.yuk.miuiXXL.utils.atLeastAndroidT
import com.yuk.miuiXXL.utils.exec

@BMMainPage("Miui XXL")
class MainPage : BasePage() {
    @SuppressLint("WorldReadableFiles")
    override fun onCreate() {
        Page(activity.getDrawable(R.drawable.ic_android)!!, pageNameId = R.string.android, round = 8f, onClickListener = { showFragment("AndroidPage") })
        Line()
        Page(
            activity.getDrawable(if (atLeastAndroidT()) R.drawable.ic_systemui_13 else R.drawable.ic_systemui_12)!!,
            pageNameId = R.string.systemui, round = 8f,
            onClickListener = { showFragment("SystemUIPage") })
        Page(activity.getDrawable(R.drawable.ic_settings)!!, pageNameId = R.string.settings, round = 8f, onClickListener = { showFragment("SettingsPage") })
        Page(activity.getDrawable(R.drawable.ic_miuihome)!!, pageNameId = R.string.miuihome, round = 8f, onClickListener = { showFragment("MiuiHomePage") })
        Page(activity.getDrawable(R.drawable.ic_update)!!, pageNameId = R.string.updater, round = 8f, onClickListener = { showFragment("UpdaterPage") })
        Page(
            activity.getDrawable(R.drawable.ic_personalassistant)!!,
            pageNameId = R.string.personalassistant,
            round = 8f,
            onClickListener = { showFragment("PersonalAssistantPage") })
        Page(
            activity.getDrawable(R.drawable.ic_securitycenter)!!,
            pageNameId = R.string.securitycenter,
            round = 8f,
            onClickListener = { showFragment("SecurityCenterPage") })
        Page(activity.getDrawable(R.drawable.ic_thememanager)!!, pageNameId = R.string.thememanager, round = 8f, onClickListener = { showFragment("ThemeManagerPage") })
        Page(activity.getDrawable(R.drawable.ic_mediaeditor)!!, pageNameId = R.string.mediaeditor, round = 8f, onClickListener = { showFragment("MediaEditorPage") })
        Page(activity.getDrawable(R.drawable.ic_powerkeeper)!!, pageNameId = R.string.powerkeeper, round = 8f, onClickListener = { showFragment("PowerKeeperPage") })
        Page(
            activity.getDrawable(R.drawable.ic_packageinstaller)!!,
            pageNameId = R.string.packageinstaller,
            round = 8f,
            onClickListener = { showFragment("PackageInstallerPage") })
        Line()

    }

}
