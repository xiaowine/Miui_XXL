package com.yuk.miuiXXL.hooks.modules.miuihome

import android.content.Context
import com.github.kyuubiran.ezxhelper.utils.unhookAll
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.callMethod
import com.yuk.miuiXXL.utils.callStaticMethod
import com.yuk.miuiXXL.utils.findClass
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.getObjectField
import com.yuk.miuiXXL.utils.getStaticObjectField
import com.yuk.miuiXXL.utils.hookAfterAllMethods
import com.yuk.miuiXXL.utils.hookAfterMethod
import com.yuk.miuiXXL.utils.hookBeforeAllMethods
import com.yuk.miuiXXL.utils.replaceMethod
import com.yuk.miuiXXL.utils.setStaticObjectField
import de.robv.android.xposed.XC_MethodHook

object IconCellCount : BaseHook() {
    override fun init() {

        if (!getBoolean("miuihome_unlock_cell_count", false)) return
        val deviceConfigClass = "com.miui.home.launcher.DeviceConfig".findClass()
        val launcherCellCountCompatDeviceClass = "com.miui.home.launcher.compat.LauncherCellCountCompatDevice".findClass()
        val launcherCellCountCompatResourceClass = "com.miui.home.launcher.compat.LauncherCellCountCompatResource".findClass()
        val utilitiesClass = "com.miui.home.launcher.common.Utilities".findClass()
        val screenUtilsClass = "com.miui.home.launcher.ScreenUtils".findClass()
        val miuiHomeSettings = "com.miui.home.settings.MiuiHomeSettings".findClass()

        launcherCellCountCompatDeviceClass.replaceMethod("shouldUseDeviceValue") {
            return@replaceMethod false
        }

        miuiHomeSettings.hookAfterMethod("onCreatePreferences") {
            it.thisObject.getObjectField("mScreenCellsConfig")?.callMethod("setVisible", true)
        }

        deviceConfigClass.hookAfterMethod("loadCellsCountConfig", Context::class.java, Boolean::class.javaPrimitiveType) {
            val sCellCountY = deviceConfigClass.getStaticObjectField("sCellCountY") as Int
            if (sCellCountY > 6) {
                val cellHeight = deviceConfigClass.callStaticMethod("getCellHeight") as Int
                deviceConfigClass.setStaticObjectField("sFolderCellHeight", cellHeight)
            }
        }

        launcherCellCountCompatResourceClass.replaceMethod("getCellCountXMax", Context::class.java) {
            return@replaceMethod 8
        }
        launcherCellCountCompatResourceClass.replaceMethod("getCellCountYMax", Context::class.java) {
            return@replaceMethod 12
        }

        var hook: Set<XC_MethodHook.Unhook>? = null
        screenUtilsClass.hookBeforeAllMethods("getScreenCellsSizeOptions") {
            hook = utilitiesClass.hookBeforeAllMethods("isNoWordModel") {
                it.result = false
            }
        }
        screenUtilsClass.hookAfterAllMethods("getScreenCellsSizeOptions") {
            hook?.unhookAll()
        }
    }

}
