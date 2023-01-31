package com.yuk.fuckMiui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.dialog.MIUIDialog
import com.yuk.fuckMiui.R
import com.yuk.fuckMiui.activity.pages.AndroidPage
import com.yuk.fuckMiui.activity.pages.MainPage
import com.yuk.fuckMiui.activity.pages.MenuPage
import com.yuk.fuckMiui.activity.pages.MiuiHomePage
import com.yuk.fuckMiui.activity.pages.PowerKeeperPage
import com.yuk.fuckMiui.activity.pages.SystemUIPage
import com.yuk.fuckMiui.activity.pages.ThemeManagerPage
import kotlin.system.exitProcess

class MainActivity : MIUIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        checkLSPosed()
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("WorldReadableFiles")
    private fun checkLSPosed() {
        try {
            setSP(getSharedPreferences("FuckMiuiConfig", MODE_WORLD_READABLE))
        } catch (exception: SecurityException) {
            isLoad = false
            MIUIDialog(this) {
                setTitle(R.string.tips)
                setMessage(R.string.not_support)
                setCancelable(false)
                setRButton(R.string.yes) {
                    exitProcess(0)
                }
            }.show()
        }
    }

    init {
        activity = this
        registerPage(MainPage::class.java)
        registerPage(MenuPage::class.java)
        registerPage(AndroidPage::class.java)
        registerPage(MiuiHomePage::class.java)
        registerPage(PowerKeeperPage::class.java)
        registerPage(SystemUIPage::class.java)
        registerPage(ThemeManagerPage::class.java)
    }
}
