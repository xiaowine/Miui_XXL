package com.yuk.miuiXXL.hooks.modules.systemui


import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Typeface
import android.os.BatteryManager
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.utils.findConstructor
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.getObjectAs
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.getBoolean
import com.yuk.miuiXXL.utils.getObjectFieldAs
import java.util.*
import kotlin.math.abs


@SuppressLint("StaticFieldLeak")
object StatusBarBattery : BaseHook() {
    var textview: TextView? = null
    var context: Context? = null

    @SuppressLint("SetTextI18n")
    override fun init() {
        if (!getBoolean("systemui_show_statusbar_battery", false)) return
        findMethod("com.android.systemui.statusbar.phone.DarkIconDispatcherImpl") { name == "applyIconTint" }.hookAfter {
            val color = it.thisObject.getObjectFieldAs<Int>("mIconTint")
            if (textview != null) {
                textview!!.setTextColor(color)
            }
        }

        findConstructor("com.android.systemui.statusbar.phone.MiuiPhoneStatusBarView") { parameterCount == 2 }.hookAfter {
            context = it.args[0] as Context
        }
        findMethod("com.android.systemui.statusbar.phone.MiuiPhoneStatusBarView") { name == "onFinishInflate" }.hookAfter {
            val mStatusBarLeftContainer = it.thisObject.getObjectAs<LinearLayout>("mStatusBarLeftContainer")
            textview = TextView(context).apply {
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 8f)
                typeface = Typeface.DEFAULT_BOLD
                isSingleLine = false
                setLineSpacing(0F, 0.8F)
                setPadding(8, 0, 0, 0)
            }
            mStatusBarLeftContainer.addView(textview)

            context!!.registerReceiver(BatteryReceiver(), IntentFilter().apply { addAction(Intent.ACTION_BATTERY_CHANGED) })
        }
    }

    class BatteryReceiver : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            val temperature = (intent.getIntExtra("temperature", 0) / 10.0)
            val current = abs(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW) / 1000 / 1000.0)
            val status = intent.getIntExtra("status", 0)
            if (textview !== null) {
                if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                    textview!!.text = "${"%.2f".format(current)}A\n${"%.1f".format(temperature)}℃"
                    textview!!.visibility = View.VISIBLE
                } else {
                    textview!!.visibility = View.GONE
                }
            }
        }
    }
}