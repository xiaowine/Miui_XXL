package com.yuk.miuiXXL.hooks.modules.miuihome

import android.app.Activity
import android.view.View
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.callMethod
import com.yuk.miuiXXL.utils.callStaticMethod
import com.yuk.miuiXXL.utils.callStaticMethodOrNull
import com.yuk.miuiXXL.utils.findClass
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.hookAfterMethod
import com.yuk.miuiXXL.utils.hookBeforeAllMethods

object BlurWhenOpenFolder : BaseHook() {
    override fun init() {

        if (!getBoolean("miuihome_blur_when_open_folder", false)) return
        val isUserBlurWhenOpenFolder = "com.miui.home.launcher.common.BlurUtils".findClass().callStaticMethodOrNull("isUserBlurWhenOpenFolder")
        if (isUserBlurWhenOpenFolder != null) {
            "com.miui.home.launcher.common.BlurUtils".hookAfterMethod("isUserBlurWhenOpenFolder") {
                it.result = true
            }
        } else {
            var isShouldBlur = false
            val folderInfo = "com.miui.home.launcher.FolderInfo".findClass()
            val launcherClass = "com.miui.home.launcher.Launcher".findClass()
            val blurUtilsClass = "com.miui.home.launcher.common.BlurUtils".findClass()
            val launcherStateClass = "com.miui.home.launcher.LauncherState".findClass()
            val cancelShortcutMenuReasonClass = "com.miui.home.launcher.shortcuts.CancelShortcutMenuReason".findClass()
            launcherClass.hookAfterMethod("openFolder", folderInfo, View::class.java) {
                val mLauncher = it.thisObject as Activity
                val isInNormalEditing = mLauncher.callMethod("isInNormalEditing") as Boolean
                if (!isInNormalEditing) blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true)
            }
            launcherClass.hookAfterMethod("isFolderShowing") {
                isShouldBlur = it.result as Boolean
            }
            launcherClass.hookAfterMethod("closeFolder", Boolean::class.java) {
                isShouldBlur = false
                val mLauncher = it.thisObject as Activity
                val isInNormalEditing = mLauncher.callMethod("isInNormalEditing") as Boolean
                if (isInNormalEditing) blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
                else blurUtilsClass.callStaticMethod("fastBlur", 0.0f, mLauncher.window, true, 300L)
            }
            launcherClass.hookAfterMethod("cancelShortcutMenu", Int::class.java, cancelShortcutMenuReasonClass) {
                val mLauncher = it.thisObject as Activity
                if (isShouldBlur) blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }
            launcherClass.hookAfterMethod("onGesturePerformAppToHome") {
                val mLauncher = it.thisObject as Activity
                val blurType = blurUtilsClass.callStaticMethod("getBlurType") as Int
                if (isShouldBlur) {
                    if (blurType == 2) blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
                    else blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 300L)
                }
            }
            blurUtilsClass.hookAfterMethod("fastBlurWhenStartOpenOrCloseApp", Boolean::class.java, launcherClass) {
                val mLauncher = it.args[1] as Activity
                val isInEditing = mLauncher.callMethod("isInEditing") as Boolean
                if (isShouldBlur) it.result = blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
                else if (isInEditing) it.result = blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }
            blurUtilsClass.hookAfterMethod("fastBlurWhenFinishOpenOrCloseApp", launcherClass) {
                val mLauncher = it.args[0] as Activity
                val isInEditing = mLauncher.callMethod("isInEditing") as Boolean
                if (isShouldBlur) it.result = blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
                else if (isInEditing) it.result = blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }
            blurUtilsClass.hookAfterMethod("fastBlurWhenExitRecents", launcherClass, launcherStateClass, Boolean::class.java) {
                val mLauncher = it.args[0] as Activity
                val isInEditing = mLauncher.callMethod("isInEditing") as Boolean
                if (isShouldBlur) it.result = blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
                else if (isInEditing) it.result = blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }
            blurUtilsClass.hookBeforeAllMethods("fastBlurDirectly") {
                val blurRatio = it.args[0] as Float
                if (isShouldBlur && blurRatio == 0.0f) it.result = null
            }
        }
    }

}
