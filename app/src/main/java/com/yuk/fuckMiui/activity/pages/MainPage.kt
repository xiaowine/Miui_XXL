package com.yuk.fuckMiui.activity.pages

import android.widget.Toast
import cn.fkj233.ui.activity.annotation.BMMainPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import com.yuk.fuckMiui.R
import com.yuk.fuckMiui.utils.exec

@BMMainPage("FuckMiui")
class MainPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.miuihome)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.double_tap_to_sleep, tipsId = R.string.double_tap_to_sleep_tips),
            SwitchV("home_double_tap_to_sleep", false)
        )
        TitleText(textId = R.string.reboot)
        TextSummaryArrow(TextSummaryV(textId = R.string.restart_scope) {
            MIUIDialog(activity) {
                setTitle(R.string.tips)
                setMessage(R.string.restart_scope_tips)
                setLButton(R.string.cancel) {
                    dismiss()
                }
                setRButton(R.string.yes) {
                    val command = arrayOf("am force-stop com.miui.home")
                    exec(command)
                    Toast.makeText(activity, getString(R.string.restart_scope_finished), Toast.LENGTH_SHORT).show()
                }
            }.show()
        })
        TextSummaryArrow(TextSummaryV(textId = R.string.reboot_system) {
            MIUIDialog(activity) {
                setTitle(R.string.tips)
                setMessage(R.string.reboot_system_tips)
                setLButton(R.string.cancel) {
                    dismiss()
                }
                setRButton(R.string.yes) {
                    exec("/system/bin/sync;/system/bin/svc power reboot || reboot")
                }
            }.show()
        })
    }
}