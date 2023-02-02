package com.yuk.miuiXXL.hooks.modules.settings

import android.app.NotificationChannel
import com.github.kyuubiran.ezxhelper.init.InitFields
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.callMethod
import com.yuk.miuiXXL.utils.findClass
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.getObjectField
import com.yuk.miuiXXL.utils.hookAfterMethod
import com.yuk.miuiXXL.utils.hookBeforeAllMethods
import com.yuk.miuiXXL.utils.setObjectField
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy


object NotificationImportance : BaseHook() {
    override fun init() {

        if (!getBoolean("settings_notification_importance", false)) return
        val mBaseNotificationSettings = "com.android.settings.notification.BaseNotificationSettings".findClass()
        val mChannelNotificationSettings = "com.android.settings.notification.ChannelNotificationSettings".findClass()
        mBaseNotificationSettings.hookBeforeAllMethods("setPrefVisible") {
            val pref = it.args[0]
            if (pref != null) {
                val prefKey = pref.callMethod("getKey") as String
                if ("importance" == prefKey) it.args[1] = true
            }
        }
        mChannelNotificationSettings.hookAfterMethod("setupChannelDefaultPrefs") {
            val pref = it.thisObject.callMethod("findPreference", "importance")
            it.thisObject.setObjectField("mImportance", pref)
            val mBackupImportance = it.thisObject.getObjectField("mBackupImportance") as Int
            if (mBackupImportance > 0) {
                val index = pref?.callMethod("findSpinnerIndexOfValue", mBackupImportance.toString()) as Int
                if (index > -1) pref.callMethod("setValueIndex", index)
                val importanceListenerClass = ("androidx.preference.Preference\$OnPreferenceChangeListener").findClass()
                val handler = InvocationHandler { _, method, args ->
                    if (method.name == "onPreferenceChange") {
                        it.thisObject.setObjectField("mBackupImportance", (args[1] as String).toInt())
                        val mChannel = it.thisObject.getObjectField("mChannel") as NotificationChannel
                        mChannel.importance = (args[1] as String).toInt()
                        mChannel.callMethod("lockFields", 4)
                        val mBackend = it.thisObject.getObjectField("mBackend")
                        val mPkg = it.thisObject.getObjectField("mPkg") as String
                        val mUid = it.thisObject.getObjectField("mUid") as Int
                        mBackend?.callMethod("updateChannel", mPkg, mUid, mChannel)
                        it.thisObject.callMethod("updateDependents", false)
                    }
                    true
                }
                val mImportanceListener: Any = Proxy.newProxyInstance(InitFields.ezXClassLoader, arrayOf(importanceListenerClass), handler)
                pref.callMethod("setOnPreferenceChangeListener", mImportanceListener)
            }
        }

    }

}