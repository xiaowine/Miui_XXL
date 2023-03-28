@file:Suppress("DEPRECATION")

package com.yuk.miuiXXL.activity.pages

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.annotation.BMMenuPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextV
import cn.fkj233.ui.dialog.MIUIDialog
import com.yuk.miuiXXL.R
import com.yuk.miuiXXL.activity.MainActivity
import com.yuk.miuiXXL.utils.BackupUtils
import com.yuk.miuiXXL.utils.exec

@BMMenuPage("MenuPage")
class MenuPage : BasePage() {
    @SuppressLint("WorldReadableFiles")
    override fun onCreate() {
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
        TextWithArrow(TextV(textId = R.string.backup, onClickListener = {
            BackupUtils.backup(activity, activity.createDeviceProtectedStorageContext().getSharedPreferences("MiuiXXL_Config", Context.MODE_WORLD_READABLE))
        }))
        TextWithArrow(TextV(textId = R.string.recovery, onClickListener = {
            BackupUtils.recovery(activity, activity.createDeviceProtectedStorageContext().getSharedPreferences("MiuiXXL_Config", Context.MODE_WORLD_READABLE))
        }))
        TextWithArrow(TextV(textId = R.string.restart_scope) {
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
                        "am force-stop com.miui.guardprovider",
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
        TextWithArrow(TextV(textId = R.string.reboot_system) {
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
        TextWithArrow(TextV(textId = R.string.ResetModule, onClickListener = {
            MIUIDialog(activity) {
                setTitle(R.string.ResetModuleDialog)
                setMessage(R.string.ResetModuleDialogTips)
                setLButton(R.string.done) {
                    activity.getSharedPreferences("MiuiXXL_Config", Activity.MODE_WORLD_READABLE).edit().clear().apply()
                    Toast.makeText(activity, activity.getString(R.string.ResetSuccess), Toast.LENGTH_LONG).show()
                }
                setRButton(R.string.cancel)
                finally { dismiss() }
            }.show()
        }))
    }

}