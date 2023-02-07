package com.yuk.miuiXXL.hooks.modules.miuihome

import android.app.Activity
import android.view.View
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.callMethod
import com.yuk.miuiXXL.utils.callStaticMethod
import com.yuk.miuiXXL.utils.findClass
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.getObjectField
import com.yuk.miuiXXL.utils.hookAfterMethod
import com.yuk.miuiXXL.utils.isAlpha

object BlurWhenOpenFolder : BaseHook() {
    override fun init() {

        if (!getBoolean("miuihome_blur_when_open_folder", false)) return
        if (isAlpha()) {
            "com.miui.home.launcher.common.BlurUtils".hookAfterMethod("isUserBlurWhenOpenFolder") {
                it.result = true
            }
        } else {
            val blurClass = "com.miui.home.launcher.common.BlurUtils".findClass()
            val folderInfo = "com.miui.home.launcher.FolderInfo".findClass()
            val launcherClass = "com.miui.home.launcher.Launcher".findClass()
            val launcherStateClass = "com.miui.home.launcher.LauncherState".findClass()
            val cancelShortcutMenuReasonClass = "com.miui.home.launcher.shortcuts.CancelShortcutMenuReason".findClass()
            launcherClass.hookAfterMethod("openFolder", folderInfo, View::class.java) {
                val mLauncher = it.thisObject as Activity
                val isInEditing = mLauncher.callMethod("isInEditing") as Boolean
                if (!isInEditing) blurClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true)
            }
            launcherClass.hookAfterMethod("closeFolder", Boolean::class.java) {
                val mLauncher = it.thisObject as Activity
                val isInEditing = mLauncher.callMethod("isInEditing") as Boolean
                if (isInEditing) blurClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
                else blurClass.callStaticMethod("fastBlur", 0.0f, mLauncher.window, true, 300L)
            }
            launcherClass.hookAfterMethod("cancelShortcutMenu", Int::class.java, cancelShortcutMenuReasonClass) {
                val mLauncher = it.thisObject as Activity
                val isFolderShowing = mLauncher.callMethod("isFolderShowing") as Boolean
                if (isFolderShowing) blurClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }
            blurClass.hookAfterMethod(
                "fastBlurWhenStartOpenOrCloseApp", Boolean::class.java, launcherClass
            ) {
                val mLauncher = it.args[1] as Activity
                val isFolderShowing = mLauncher.callMethod("isFolderShowing") as Boolean
                val isInEditing = mLauncher.callMethod("isInEditing") as Boolean
                if (isFolderShowing) it.result = blurClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
                else if (isInEditing) it.result = blurClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }
            blurClass.hookAfterMethod(
                "fastBlurWhenFinishOpenOrCloseApp", launcherClass
            ) {
                val mLauncher = it.args[0] as Activity
                val isFolderShowing = mLauncher.callMethod("isFolderShowing") as Boolean
                val isInEditing = mLauncher.callMethod("isInEditing") as Boolean
                if (isFolderShowing) it.result = blurClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
                else if (isInEditing) it.result = blurClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }
            blurClass.hookAfterMethod(
                "fastBlurWhenExitRecents", launcherClass, launcherStateClass, Boolean::class.java
            ) {
                val mLauncher = it.args[0] as Activity
                val isFolderShowing = mLauncher.callMethod("isFolderShowing") as Boolean
                val isInEditing = mLauncher.callMethod("isInEditing") as Boolean
                if (isFolderShowing) it.result = blurClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
                else if (isInEditing) it.result = blurClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }
            launcherClass.hookAfterMethod("onGesturePerformAppToHome") {
                val mLauncher = it.thisObject as Activity
                val isFolderShowing = mLauncher.callMethod("isFolderShowing") as Boolean
                if (isFolderShowing) blurClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 300L)
            }
            "com.miui.home.launcher.Workspace".hookAfterMethod("setEditMode") {
                val mLauncher = it.thisObject.getObjectField("mLauncher") as Activity
                val isFolderShowing = mLauncher.callMethod("isFolderShowing") as Boolean
                if (isFolderShowing) blurClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }
            "com.miui.home.launcher.DropTargetBar".hookAfterMethod("onDragEnd", "com.miui.home.launcher.DragObject".findClass()) {
                val mLauncher = it.thisObject.getObjectField("mLauncher") as Activity
                val isInDisableEditing = mLauncher.callMethod("isInDisableEditing") as Boolean
                val isFolderShowing = mLauncher.callMethod("isFolderShowing") as Boolean
                if (isFolderShowing && isInDisableEditing) blurClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }
        }
    }

}
