package com.yuk.fuckMiui.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yuk.fuckMiui.R

@BMPage("MiuiHomePage", "MiuiHome", hideMenu = false)
class MiuiHomePage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.miuihome)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_double_tap_to_sleep, tipsId = R.string.miuihome_double_tap_to_sleep_tips),
            SwitchV("miuihome_double_tap_to_sleep", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_highend_device, tipsId = R.string.miuihome_highend_device_tips),
            SwitchV("miuihome_highend_device", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_recentwiew_wallpaper_darkening, tipsId = R.string.miuihome_recentwiew_wallpaper_darkening_tips),
            SwitchV("miuihome_recentwiew_wallpaper_darkening", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_unlock_animation, tipsId = R.string.miuihome_unlock_animation_tips),
            SwitchV("miuihome_unlock_animation", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_recentview_remove_card_animation, tipsId = R.string.miuihome_recentview_remove_card_animation_tips),
            SwitchV("miuihome_recentview_remove_card_animation", false)
        )
        TextSummary(textId = R.string.miuihome_anim_ratio, tipsId = R.string.miuihome_anim_ratio_tips)
        SeekBarWithText("miuihome_anim_ratio", 0, 300, 100)
        TextSummary(textId = R.string.miuihome_anim_ratio_recent, tipsId = R.string.miuihome_anim_ratio_recent_tips)
        SeekBarWithText("miuihome_anim_ratio_recent", 0, 300, 100)
    }
}