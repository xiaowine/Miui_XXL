package com.yuk.miuiXXL.hooks.modules.miuihome

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.callMethod
import com.yuk.miuiXXL.utils.callStaticMethod
import com.yuk.miuiXXL.utils.findClass
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.getObjectField
import com.yuk.miuiXXL.utils.hookAfterAllMethods
import com.yuk.miuiXXL.utils.hookBeforeAllMethods

// Code from WINI, now only optimized for effects triggered in folders.
object BlurWhenShowShortcutMenu : BaseHook() {
    override fun init() {

        if (!getBoolean("miuihome_shortcut_menu_blur", false)) return
        val shortcutMenuLayerClass = "com.miui.home.launcher.ShortcutMenuLayer".findClass()
        val shortcutMenuClass = "com.miui.home.launcher.shortcuts.ShortcutMenu".findClass()
        val blurUtilsClass = "com.miui.home.launcher.common.BlurUtils".findClass()
        val applicationClass = "com.miui.home.launcher.Application".findClass()
        val utilitiesClass = "com.miui.home.launcher.common.Utilities".findClass()
        val launcherStateClass = "com.miui.home.launcher.LauncherState".findClass()
        val allBlurredDrawable: MutableList<Drawable> = ArrayList()

        fun showBlurDrawable() {
            allBlurredDrawable.forEach { drawable ->
                drawable.callMethod("setVisible", true, false)
            }
        }

        fun hideBlurDrawable() {
            allBlurredDrawable.forEach { drawable ->
                drawable.callMethod("setVisible", false, false)
            }
        }

        var isShouldBlur = false
        var dragLayer: ViewGroup? = null
        var targetView: View? = null
        var dragLayerBackground: Drawable? = null

        shortcutMenuLayerClass.hookBeforeAllMethods("showShortcutMenu") {
            hideBlurDrawable()
            val dragObject = it.args[0]
            val dragViewInfo = dragObject.callMethod("getDragInfo")
            // 抽屉内图标不模糊
            val mLauncher = applicationClass.callStaticMethod("getLauncher") as Activity
            val launcherStatusField = launcherStateClass.getDeclaredField("ALL_APPS")
            launcherStatusField.isAccessible = true
            val allAppsStatus = launcherStatusField.get(null)
            val stateManager = mLauncher.callMethod("getStateManager")
            val currentState = stateManager?.callMethod("getState")
            if (currentState == allAppsStatus) return@hookBeforeAllMethods
            // 特殊图标不模糊
            val itemType = dragViewInfo?.getObjectField("itemType") as Int
            val iconTitle = dragViewInfo.callMethod("getTitle") as String
            val BLUR_ICON_APP_NAME = arrayOf("锁屏", "手电筒", "数据", "飞行模式", "蓝牙", "WLAN 热点")
            if (itemType == 0 && !BLUR_ICON_APP_NAME.contains(iconTitle)
            //|| itemType == 2 || itemType == 21 || itemType == 22
            ) {
                val targetBlurView = mLauncher.callMethod("getScreen") as View
                val dragView = dragObject.callMethod("getDragView") as View
                targetView = dragView.callMethod("getContent") as View
                val isFolderShowing = mLauncher.callMethod("isFolderShowing") as Boolean
                val renderEffectArray = arrayOfNulls<RenderEffect>(51)
                for (index in 0..50) {
                    renderEffectArray[index] = RenderEffect.createBlurEffect((index + 1).toFloat(), (index + 1).toFloat(), Shader.TileMode.MIRROR)
                }
                val valueAnimator = ValueAnimator.ofInt(0, 50)
                valueAnimator.addUpdateListener { animator ->
                    val value = animator.animatedValue as Int
                    if (!(getBoolean("miuihome_blur_when_open_folder", false) && isFolderShowing)) {
                        blurUtilsClass.callStaticMethod("fastBlurDirectly", value / 50f, mLauncher.window)
                    }
                    targetBlurView.setRenderEffect(renderEffectArray[value])
                }
                dragLayer = targetBlurView.parent as ViewGroup
                valueAnimator.duration = 200
                valueAnimator.start()
                isShouldBlur = true
            }
        }

        shortcutMenuLayerClass.hookBeforeAllMethods("onDragStart") {
            if (isShouldBlur) {
                targetView?.alpha = 0f
                val dragObject = it.args[1]
                val dragView = dragObject.callMethod("getDragView") as View
                val dragViewParent = dragView.parent as View
                val bitmap = Bitmap.createBitmap(dragLayer!!.width, dragLayer!!.height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                val originalScale = dragView.scaleX
                dragView.scaleX = 1f
                dragView.scaleY = 1f
                dragViewParent.draw(canvas)
                dragView.scaleX = originalScale
                dragView.scaleY = originalScale
                dragLayerBackground = BitmapDrawable(dragLayer!!.context.resources, bitmap)
            }
        }

        shortcutMenuLayerClass.hookBeforeAllMethods("onDragEnd") {
            if (isShouldBlur) {
                targetView?.alpha = 0f
                val isLocked = utilitiesClass.callStaticMethod("isScreenCellsLocked") as Boolean
                if (isLocked) {
                    dragLayer?.background = dragLayerBackground
                } else {
                    val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
                    valueAnimator.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            dragLayer?.background = dragLayerBackground
                        }
                    })
                    valueAnimator.duration = 200
                    valueAnimator.start()
                }
            }
        }

        shortcutMenuClass.hookBeforeAllMethods("reset") {
            if (isShouldBlur) {
                isShouldBlur = false
                //targetView?.alpha = 1f
                val mLauncher = applicationClass.callStaticMethod("getLauncher") as Activity
                blurUtilsClass.callStaticMethod("fastBlurDirectly", 0f, mLauncher.window)
            }
        }

        shortcutMenuLayerClass.hookBeforeAllMethods("hideShortcutMenu") {
            if (isShouldBlur) {
                val shortcutMenuLayer = it.thisObject as FrameLayout
                val mLauncher = applicationClass.callStaticMethod("getLauncher") as Activity
                val targetBlurView = mLauncher.callMethod("getScreen") as View
                val isFolderShowing = mLauncher.callMethod("isFolderShowing") as Boolean
                val isInEditing = mLauncher.callMethod("isInEditing") as Boolean
                val valueAnimator = ValueAnimator.ofInt(50, 0)
                val renderEffectArray = arrayOfNulls<RenderEffect>(51)
                for (index in 0..50) {
                    renderEffectArray[index] = RenderEffect.createBlurEffect((index + 1).toFloat(), (index + 1).toFloat(), Shader.TileMode.MIRROR)
                }
                valueAnimator.addUpdateListener { animator ->
                    val value = animator.animatedValue as Int
                    targetBlurView.setRenderEffect(renderEffectArray[value])
                    if (!(getBoolean("miuihome_blur_when_open_folder", false) && isFolderShowing)) {
                        blurUtilsClass.callStaticMethod("fastBlurDirectly", value / 50f, mLauncher.window)
                    }
                }
                valueAnimator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        shortcutMenuLayer.background = null
                        showBlurDrawable()
                        if (!(isFolderShowing && isInEditing)) targetView?.alpha = 1f
                        targetBlurView.setRenderEffect(null)
                        dragLayer?.background = null
                    }
                })
                valueAnimator.duration = 200
                valueAnimator.start()
            }
            isShouldBlur = false
        }

        blurUtilsClass.hookBeforeAllMethods("fastBlurDirectly") {
            val blurRatio = it.args[0] as Float
            if (isShouldBlur && blurRatio == 0.0f) {
                it.result = null
            }
        }

        blurUtilsClass.hookAfterAllMethods("fastBlurWhenEnterRecents") {
            it.args[0]?.callMethod("hideShortcutMenuWithoutAnim")
            hideBlurDrawable()
        }

        blurUtilsClass.hookAfterAllMethods("fastBlurWhenExitRecents") {
            showBlurDrawable()
        }
    }

}
