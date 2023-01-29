package com.yuk.fuckMiui.activity.pages

import android.widget.Toast
import cn.fkj233.ui.activity.annotation.BMMenuPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.dialog.MIUIDialog
import com.yuk.fuckMiui.R
import com.yuk.fuckMiui.utils.exec

@BMMenuPage("Menu")
class MenuPage : BasePage() {
    override fun onCreate() {
        Text(textId = R.string.restart_scope) {
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
        }
        Text(textId = R.string.reboot_system) {
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
        }
    }
}