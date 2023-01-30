package com.yuk.fuckMiui.hooks.modules.miuihome

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.github.kyuubiran.ezxhelper.utils.putObject
import com.yuk.fuckMiui.hooks.modules.BaseHook
import com.yuk.fuckMiui.utils.getBoolean

object DisableRecentViewWallpaperDarkening : BaseHook() {
    override fun init() {

        if (!getBoolean("miuihome_recentwiew_wallpaper_darkening", false)) return
        findMethod("com.miui.home.recents.DimLayer") {
            name == "dim" && parameterCount == 3
        }.hookBefore {
            it.args[0] = 0.0f
            it.thisObject.putObject("mCurrentAlpha", 0.0f)
        }

    }

}