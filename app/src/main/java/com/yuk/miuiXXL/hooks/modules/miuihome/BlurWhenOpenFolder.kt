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
import com.yuk.miuiXXL.utils.hookBeforeAllMethods
import com.yuk.miuiXXL.utils.isAlpha

object BlurWhenOpenFolder : BaseHook() {
    override fun init() {

        if (!getBoolean("miuihome_blur_when_open_folder", false)) return
        if (isAlpha()) {
            "com.miui.home.launcher.common.BlurUtils".hookAfterMethod("isUserBlurWhenOpenFolder") {
                it.result = true
            }
        } else {
            var isShouldBlur = false
            val blurUtilsClass = "com.miui.home.launcher.common.BlurUtils".findClass()
            val folderInfo = "com.miui.home.launcher.FolderInfo".findClass()
            val launcherClass = "com.miui.home.launcher.Launcher".findClass()
            val launcherStateClass = "com.miui.home.launcher.LauncherState".findClass()
            val cancelShortcutMenuReasonClass = "com.miui.home.launcher.shortcuts.CancelShortcutMenuReason".findClass()
            val workspaceClass = "com.miui.home.launcher.Workspace".findClass()
            val dragObjectClass = "com.miui.home.launcher.DragObject".findClass()
            val dropTargetBarClass = "com.miui.home.launcher.DropTargetBar".findClass()
            launcherClass.hookAfterMethod("openFolder", folderInfo, View::class.java) {
                isShouldBlur = true
                val mLauncher = it.thisObject as Activity
                val isInEditing = mLauncher.callMethod("isInEditing") as Boolean
                if (!isInEditing) blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true)
            }
            launcherClass.hookAfterMethod("closeFolder", Boolean::class.java) {
                isShouldBlur = false
                val mLauncher = it.thisObject as Activity
                val isInEditing = mLauncher.callMethod("isInEditing") as Boolean
                if (isInEditing) blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
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
            workspaceClass.hookAfterMethod("setEditMode") {
                val mLauncher = it.thisObject.getObjectField("mLauncher") as Activity
                if (isShouldBlur) blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }
            dropTargetBarClass.hookAfterMethod("onDragEnd", dragObjectClass) {
                val mLauncher = it.thisObject.getObjectField("mLauncher") as Activity
                if (isShouldBlur) blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }
            dropTargetBarClass.hookAfterMethod("onDragOverThresholdWhenShortcutMenuShowing", dragObjectClass) {
                val mLauncher = it.thisObject.getObjectField("mLauncher") as Activity
                if (isShouldBlur) blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }
            dropTargetBarClass.hookAfterMethod("onSecondaryPointerDownWhenShortcutMenuShowing", dragObjectClass) {
                val mLauncher = it.thisObject.getObjectField("mLauncher") as Activity
                if (isShouldBlur) blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }
        }
    }

}
