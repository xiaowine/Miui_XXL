package com.yuk.miuiXXL.hooks

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.Log.logexIfThrow
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.hooks.modules.android.FuckValidateTheme3
import com.yuk.miuiXXL.hooks.modules.android.MaxWallpaperScale
import com.yuk.miuiXXL.hooks.modules.android.RemoveScreenshotRestriction
import com.yuk.miuiXXL.hooks.modules.android.RemoveSmallWindowRestriction1
import com.yuk.miuiXXL.hooks.modules.android.corepatch.CorePatchMainHook
import com.yuk.miuiXXL.hooks.modules.mediaeditor.RemoveCropRestriction
import com.yuk.miuiXXL.hooks.modules.miuihome.AlwaysShowStatusBarClock
import com.yuk.miuiXXL.hooks.modules.miuihome.AnimDurationRatio
import com.yuk.miuiXXL.hooks.modules.miuihome.BlurWhenOpenFolder
import com.yuk.miuiXXL.hooks.modules.miuihome.BlurWhenShowShortcutMenu
import com.yuk.miuiXXL.hooks.modules.miuihome.CategoryFeatures
import com.yuk.miuiXXL.hooks.modules.miuihome.DisableRecentViewWallpaperDarkening
import com.yuk.miuiXXL.hooks.modules.miuihome.DoubleTapToSleep
import com.yuk.miuiXXL.hooks.modules.miuihome.IconCellCount
import com.yuk.miuiXXL.hooks.modules.miuihome.MinusOneOverlapMode
import com.yuk.miuiXXL.hooks.modules.miuihome.RecentViewRemoveCardAnim
import com.yuk.miuiXXL.hooks.modules.miuihome.RemoveSmallWindowRestriction2
import com.yuk.miuiXXL.hooks.modules.miuihome.ScrollIconName
import com.yuk.miuiXXL.hooks.modules.miuihome.SetDeviceLevel
import com.yuk.miuiXXL.hooks.modules.miuihome.ShortcutAddSmallWindow
import com.yuk.miuiXXL.hooks.modules.miuihome.TaskViewCardSize
import com.yuk.miuiXXL.hooks.modules.miuihome.TwoXOneIconRoundedCornerFollowing
import com.yuk.miuiXXL.hooks.modules.miuihome.UnlockAnim
import com.yuk.miuiXXL.hooks.modules.miuihome.UseCompleteBlur
import com.yuk.miuiXXL.hooks.modules.packageinstaller.AllowUpdateSystemApp
import com.yuk.miuiXXL.hooks.modules.packageinstaller.DisableCountCheck
import com.yuk.miuiXXL.hooks.modules.packageinstaller.RemovePackageInstallerAds
import com.yuk.miuiXXL.hooks.modules.packageinstaller.ShowMoreApkInfo
import com.yuk.miuiXXL.hooks.modules.personalassistant.BlurWhenGotoMinusOne
import com.yuk.miuiXXL.hooks.modules.powerkeeper.DisableDynamicRefreshRate
import com.yuk.miuiXXL.hooks.modules.securitycenter.RemoveMacroBlacklist
import com.yuk.miuiXXL.hooks.modules.securitycenter.ShowBatteryTemperature
import com.yuk.miuiXXL.hooks.modules.securitycenter.SkipWarningWaitTime
import com.yuk.miuiXXL.hooks.modules.settings.NotificationImportance
import com.yuk.miuiXXL.hooks.modules.systemui.DisableBluetoothRestrict
import com.yuk.miuiXXL.hooks.modules.systemui.LockScreenShowBatteryCV
import com.yuk.miuiXXL.hooks.modules.systemui.LockScreenShowSeconds
import com.yuk.miuiXXL.hooks.modules.systemui.RemoveLockScreenCamera
import com.yuk.miuiXXL.hooks.modules.systemui.RemoveLockScreenMinus
import com.yuk.miuiXXL.hooks.modules.systemui.RemoveSmallWindowRestriction3
import com.yuk.miuiXXL.hooks.modules.systemui.ShowWifiStandard
import com.yuk.miuiXXL.hooks.modules.systemui.StatusBarShowSeconds
import com.yuk.miuiXXL.hooks.modules.systemui.UseNewHD
import com.yuk.miuiXXL.hooks.modules.systemui.WaveChargeAnim
import com.yuk.miuiXXL.hooks.modules.thememanager.FuckValidateTheme1
import com.yuk.miuiXXL.hooks.modules.thememanager.FuckValidateTheme2
import com.yuk.miuiXXL.hooks.modules.thememanager.RemoveThemeManagerAds
import com.yuk.miuiXXL.hooks.modules.updater.VABUpdate
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage

private const val TAG = "Miui XXL"
private val PACKAGE_NAME_HOOKED = setOf(
    "android",
    "com.android.settings",
    "com.android.systemui",
    "com.android.thememanager",
    "com.android.updater",
    "com.miui.gallery",
    "com.miui.home",
    "com.miui.mediaeditor",
    "com.miui.packageinstaller",
    "com.miui.personalassistant",
    "com.miui.powerkeeper",
    "com.miui.screenshot",
    "com.miui.securitycenter"
)

class MainHook : IXposedHookLoadPackage, IXposedHookZygoteInit {

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        EzXHelperInit.initZygote(startupParam)
        CorePatchMainHook().initZygote(startupParam)
        initHooks(FuckValidateTheme3)
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName in PACKAGE_NAME_HOOKED) {
            // Init EzXHelper
            EzXHelperInit.initHandleLoadPackage(lpparam)
            EzXHelperInit.setLogTag(TAG)
            EzXHelperInit.setToastTag(TAG)
            // Init hooks
            when (lpparam.packageName) {
                "android" -> {
                    CorePatchMainHook().handleLoadPackage(lpparam)
                    initHooks(
                        FuckValidateTheme3,
                        MaxWallpaperScale,
                        RemoveSmallWindowRestriction1,
                        RemoveScreenshotRestriction,
                    )
                }

                "com.android.settings" -> {
                    initHooks(
                        NotificationImportance,
                    )
                }

                "com.android.systemui" -> {
                    initHooks(
                        StatusBarShowSeconds,
                        LockScreenShowBatteryCV,
                        RemoveLockScreenMinus,
                        RemoveLockScreenCamera,
                        DisableBluetoothRestrict,
                        RemoveSmallWindowRestriction3,
                        WaveChargeAnim,
                        ShowWifiStandard,
                        LockScreenShowSeconds,
                        UseNewHD,
                    )
                }

                "com.android.thememanager" -> {
                    FuckValidateTheme2().handleLoadPackage(lpparam)
                    initHooks(
                        RemoveThemeManagerAds,
                        FuckValidateTheme1,
                    )
                }

                "com.android.updater" -> {
                    initHooks(
                        VABUpdate,
                    )
                }

                "com.miui.gallery" -> {
                    initHooks(
                        RemoveCropRestriction,
                    )
                }

                "com.miui.home" -> {
                    initHooks(
                        SetDeviceLevel,
                        DoubleTapToSleep,
                        ScrollIconName,
                        AnimDurationRatio,
                        UnlockAnim,
                        DisableRecentViewWallpaperDarkening,
                        RecentViewRemoveCardAnim,
                        CategoryFeatures,
                        TwoXOneIconRoundedCornerFollowing,
                        ShortcutAddSmallWindow,
                        RemoveSmallWindowRestriction2,
                        BlurWhenOpenFolder,
                        AlwaysShowStatusBarClock,
                        TaskViewCardSize,
                        BlurWhenShowShortcutMenu,
                        UseCompleteBlur,
                        MinusOneOverlapMode,
                        IconCellCount,
                    )
                }

                "com.miui.mediaeditor" -> {
                    initHooks(
                        RemoveCropRestriction,
                    )
                }

                "com.miui.packageinstaller" -> {
                    initHooks(
                        RemovePackageInstallerAds,
                        AllowUpdateSystemApp,
                        ShowMoreApkInfo,
                        DisableCountCheck,
                    )
                }

                "com.miui.personalassistant" -> {
                    initHooks(
                        BlurWhenGotoMinusOne,
                    )
                }

                "com.miui.powerkeeper" -> {
                    initHooks(
                        DisableDynamicRefreshRate,
                    )
                }

                "com.miui.screenshot" -> {
                    initHooks(
                        RemoveCropRestriction,
                    )
                }

                "com.miui.securitycenter" -> {
                    RemoveMacroBlacklist().handleLoadPackage(lpparam)
                    initHooks(
                        SkipWarningWaitTime,
                        ShowBatteryTemperature,
                    )
                }

                else -> return
            }
        }
    }

    private fun initHooks(vararg hook: BaseHook) {
        hook.forEach {
            runCatching {
                if (it.isInit) return@forEach
                it.init()
                it.isInit = true
                Log.i("Inited hook: ${it.javaClass.simpleName}")
            }.logexIfThrow("Failed init hook: ${it.javaClass.simpleName}")
        }
    }

}
