package com.yuk.miuiXXL.activity.pages

import android.content.ComponentName
import android.content.pm.PackageManager
import android.widget.Toast
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.annotation.BMMainPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextV
import cn.fkj233.ui.dialog.MIUIDialog
import com.yuk.miuiXXL.R
import com.yuk.miuiXXL.activity.MainActivity
import com.yuk.miuiXXL.utils.exec

@BMMainPage("Miui XXL")
class MainPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithArrow(
            TextSummaryV(textId = R.string.android, tipsId = R.string.android_reboot, onClickListener = { showFragment("AndroidPage") })
        )
        Line()
        TextSummaryWithArrow(TextSummaryV(textId = R.string.systemui, onClickListener = { showFragment("SystemUIPage") }))
        TextSummaryWithArrow(TextSummaryV(textId = R.string.settings, onClickListener = { showFragment("SettingsPage") }))
        TextSummaryWithArrow(TextSummaryV(textId = R.string.miuihome, onClickListener = { showFragment("MiuiHomePage") }))
        TextSummaryWithArrow(TextSummaryV(textId = R.string.updater, onClickListener = { showFragment("UpdaterPage") }))
        TextSummaryWithArrow(TextSummaryV(textId = R.string.personalassistant, onClickListener = { showFragment("PersonalAssistantPage") }))
        TextSummaryWithArrow(TextSummaryV(textId = R.string.securitycenter, onClickListener = { showFragment("SecurityCenterPage") }))
        TextSummaryWithArrow(TextSummaryV(textId = R.string.thememanager, onClickListener = { showFragment("ThemeManagerPage") }))
        TextSummaryWithArrow(TextSummaryV(textId = R.string.mediaeditor, onClickListener = { showFragment("MediaEditorPage") }))
        TextSummaryWithArrow(TextSummaryV(textId = R.string.powerkeeper, onClickListener = { showFragment("PowerKeeperPage") }))
        TextSummaryWithArrow(TextSummaryV(textId = R.string.packageinstaller, onClickListener = { showFragment("PackageInstallerPage") }))
        Line()
        TextWithSwitch(TextV(textId = R.string.hide_desktop_icon), SwitchV("hide_desktop_icon", onClickListener = {
            val pm = MIUIActivity.activity.packageManager
            val mComponentEnabledState: Int = if (it) {
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            } else {
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            }
            pm.setComponentEnabledSetting(
                ComponentName(MIUIActivity.activity, MainActivity::class.java.name + "Alias"), mComponentEnabledState, PackageManager.DONT_KILL_APP
            )
        }))
        TextSummaryWithArrow(TextSummaryV(textId = R.string.restart_scope) {
            MIUIDialog(activity) {
                setTitle(R.string.tips)
                setMessage(R.string.restart_scope_summary)
                setLButton(R.string.cancel) {
                    dismiss()
                }
                setRButton(R.string.done) {
                    val command = arrayOf(
                        "am force-stop com.android.thememanager",
                        "am force-stop com.android.settings",
                        "am force-stop com.android.updater",
                        "am force-stop com.miui.gallery",
                        "am force-stop com.miui.home",
                        "am force-stop com.miui.mediaeditor",
                        "am force-stop com.miui.packageinstaller",
                        "am force-stop com.miui.personalassistant",
                        "am force-stop com.miui.powerkeeper",
                        "am force-stop com.miui.screenshot",
                        "am force-stop com.miui.securitycenter",
                        "killall com.android.systemui"
                    )
                    exec(command)
                    Toast.makeText(activity, getString(R.string.restart_scope_finished), Toast.LENGTH_SHORT).show()
                    Thread.sleep(500)
                    dismiss()
                }
            }.show()
        })
        TextSummaryWithArrow(TextSummaryV(textId = R.string.reboot_system) {
            MIUIDialog(activity) {
                setTitle(R.string.tips)
                setMessage(R.string.reboot_system_summary)
                setLButton(R.string.cancel) {
                    dismiss()
                }
                setRButton(R.string.done) {
                    exec("reboot")
                }
            }.show()
        })
    }

}
