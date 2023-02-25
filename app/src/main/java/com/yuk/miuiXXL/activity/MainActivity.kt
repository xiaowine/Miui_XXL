package com.yuk.miuiXXL.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.dialog.MIUIDialog
import com.yuk.miuiXXL.R
import com.yuk.miuiXXL.activity.pages.AndroidPage
import com.yuk.miuiXXL.activity.pages.MainPage
import com.yuk.miuiXXL.activity.pages.MediaEditorPage
import com.yuk.miuiXXL.activity.pages.MiuiHomePage
import com.yuk.miuiXXL.activity.pages.PackageInstallerPage
import com.yuk.miuiXXL.activity.pages.PersonalAssistantPage
import com.yuk.miuiXXL.activity.pages.PowerKeeperPage
import com.yuk.miuiXXL.activity.pages.SecurityCenterPage
import com.yuk.miuiXXL.activity.pages.SettingsPage
import com.yuk.miuiXXL.activity.pages.SystemUIPage
import com.yuk.miuiXXL.activity.pages.ThemeManagerPage
import com.yuk.miuiXXL.activity.pages.UpdaterPage
import com.yuk.miuiXXL.utils.BackupUtils
import kotlin.system.exitProcess

class MainActivity : MIUIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        checkLSPosed()
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("WorldReadableFiles")
    private fun checkLSPosed() {
        try {
            setSP(getSharedPreferences("MiuiXXL_Config", MODE_WORLD_READABLE))
        } catch (exception: SecurityException) {
            isLoad = false
            MIUIDialog(this) {
                setTitle(R.string.tips)
                setMessage(R.string.not_support)
                setCancelable(false)
                setRButton(R.string.done) {
                    exitProcess(0)
                }
            }.show()
        }
    }

    init {
        activity = this
        registerPage(MainPage::class.java)
        registerPage(AndroidPage::class.java)
        registerPage(MiuiHomePage::class.java)
        registerPage(PowerKeeperPage::class.java)
        registerPage(SecurityCenterPage::class.java)
        registerPage(SystemUIPage::class.java)
        registerPage(ThemeManagerPage::class.java)
        registerPage(UpdaterPage::class.java)
        registerPage(SettingsPage::class.java)
        registerPage(MediaEditorPage::class.java)
        registerPage(PersonalAssistantPage::class.java)
        registerPage(PackageInstallerPage::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null && resultCode == RESULT_OK) {
            when (requestCode) {
                BackupUtils.CREATE_DOCUMENT_CODE -> {
                    BackupUtils.handleCreateDocument(activity, data.data)
                }

                BackupUtils.OPEN_DOCUMENT_CODE -> {
                    BackupUtils.handleReadDocument(activity, data.data)
                }

            }
        }
    }

}
