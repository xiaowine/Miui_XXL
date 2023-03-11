package com.yuk.miuiXXL.activity.pages

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SeekBarWithTextV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextV
import com.yuk.miuiXXL.R

@BMPage("MiuiHomePage", hideMenu = false)
class MiuiHomePage : BasePage() {
    override fun getTitle(): String {
        setTitle(getString(R.string.miuihome))
        return getString(R.string.miuihome)
    }

    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_always_show_statusbar_clock, tipsId = R.string.miuihome_always_show_statusbar_clock_summary),
            SwitchV("miuihome_always_show_statusbar_clock", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_double_tap_to_sleep, tipsId = R.string.miuihome_double_tap_to_sleep_summary),
            SwitchV("miuihome_double_tap_to_sleep", false)
        )
        val blurBinding = GetDataBinding({ safeSP.getBoolean("miuihome_use_complete_blur", false) }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_use_complete_blur, tipsId = R.string.miuihome_use_complete_blur_summary),
            SwitchV("miuihome_use_complete_blur", false, dataBindingSend = blurBinding.bindingSend)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_complete_blur_fix, tipsId = R.string.miuihome_complete_blur_fix_summary),
            SwitchV("miuihome_complete_blur_fix", false),
            dataBindingRecv = blurBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_highend_device, tipsId = R.string.miuihome_highend_device_summary),
            SwitchV("miuihome_highend_device", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_recentwiew_wallpaper_darkening, tipsId = R.string.miuihome_recentwiew_wallpaper_darkening_summary),
            SwitchV("miuihome_recentwiew_wallpaper_darkening", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_unlock_cell_count, tipsId = R.string.miuihome_unlock_cell_count_summary),
            SwitchV("miuihome_unlock_cell_count", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_unlock_animation, tipsId = R.string.miuihome_unlock_animation_summary),
            SwitchV("miuihome_unlock_animation", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_recentview_remove_card_animation, tipsId = R.string.miuihome_recentview_remove_card_animation_summary),
            SwitchV("miuihome_recentview_remove_card_animation", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_shortcut_menu_blur, tipsId = R.string.miuihome_shortcut_menu_blur_summary),
            SwitchV("miuihome_shortcut_menu_blur", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_blur_when_open_folder, tipsId = R.string.miuihome_blur_when_open_folder_summary),
            SwitchV("miuihome_blur_when_open_folder", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_hide_allapps_category_all, tipsId = R.string.miuihome_hide_allapps_category_all_summary),
            SwitchV("miuihome_hide_allapps_category_all", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_hide_allapps_category_paging_edit, tipsId = R.string.miuihome_hide_allapps_category_paging_edit_summary),
            SwitchV("miuihome_hide_allapps_category_paging_edit", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_two_x_one_icon_rounded_corner_following, tipsId = R.string.miuihome_two_x_one_icon_rounded_corner_following_summary),
            SwitchV("miuihome_two_x_one_icon_rounded_corner_following", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_shortcut_add_small_window, tipsId = R.string.miuihome_shortcut_add_small_window_summary),
            SwitchV("miuihome_shortcut_add_small_window", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_scroll_icon_name, tipsId = R.string.miuihome_scroll_icon_name_summary),
            SwitchV("miuihome_scroll_icon_name", false)
        )
        val animRatioBinding = GetDataBinding({ safeSP.getBoolean("miuihome_anim_ratio_binding", false) }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_anim_ratio_binding, tipsId = R.string.miuihome_anim_ratio_binding_summary),
            SwitchV("miuihome_anim_ratio_binding", dataBindingSend = animRatioBinding.bindingSend)
        )
        TextSummaryWithSeekBar(
            TextSummaryV(textId = R.string.miuihome_anim_ratio, tipsId = R.string.miuihome_anim_ratio_summary),
            SeekBarWithTextV("miuihome_anim_ratio", 0, 300, 100),
            dataBindingRecv = animRatioBinding.getRecv(1)
        )
        TextSummaryWithSeekBar(
            TextSummaryV(textId = R.string.miuihome_anim_ratio_recent, tipsId = R.string.miuihome_anim_ratio_recent_summary),
            SeekBarWithTextV("miuihome_anim_ratio_recent", 0, 300, 100),
            dataBindingRecv = animRatioBinding.getRecv(1)
        )
        val cardSizeBinding = GetDataBinding({ safeSP.getBoolean("miuihome_task_view_card_size_binding", false) }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.miuihome_task_view_card_size_binding, tipsId = R.string.miuihome_task_view_card_size_binding_summary),
            SwitchV("miuihome_task_view_card_size_binding", dataBindingSend = cardSizeBinding.bindingSend)
        )
        TextWithSeekBar(
            TextV(textId = R.string.miuihome_task_view_card_size_vertical),
            SeekBarWithTextV("miuihome_task_view_card_size_vertical", 80, 120, 100),
            dataBindingRecv = cardSizeBinding.getRecv(1)
        )
        TextWithSeekBar(
            TextV(textId = R.string.miuihome_task_view_card_size_horizontal1),
            SeekBarWithTextV("miuihome_task_view_card_size_horizontal1", 80, 120, 100),
            dataBindingRecv = cardSizeBinding.getRecv(1)
        )
        TextWithSeekBar(
            TextV(textId = R.string.miuihome_task_view_card_size_horizontal2),
            SeekBarWithTextV("miuihome_task_view_card_size_horizontal2", 80, 120, 100),
            dataBindingRecv = cardSizeBinding.getRecv(1)
        )
    }

}
