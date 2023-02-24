package com.yuk.miuiXXL.hooks.modules.securitycenter

import android.annotation.SuppressLint
import android.app.AndroidAppHelper
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import cn.fkj233.ui.activity.dp2px
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.getObjectAs
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.isStatic
import com.github.kyuubiran.ezxhelper.utils.paramCount
import com.yuk.miuiXXL.hooks.modules.BaseHook
import com.yuk.miuiXXL.utils.findClassOrNull
import com.yuk.miuiXXL.utils.getBoolean

object ShowBatteryTemperature : BaseHook() {
    @SuppressLint("DiscouragedApi")
    override fun init() {

        if (!getBoolean("securitycenter_show_battery_temperature", false)) return
        val batteryFragmentClass = "com.miui.powercenter.BatteryFragment".findClassOrNull()
        if (batteryFragmentClass != null) {
            findMethod("com.miui.powercenter.BatteryFragment") {
                paramCount == 1 && returnType == String::class.java && isStatic
            }
        } else {
            findMethod("com.miui.powercenter.a") {
                paramCount == 1 && returnType == String::class.java && isStatic
            }
        }.hookAfter {
            it.result = getBatteryTemperature(it.args[0] as Context).toString()
        }

        if (batteryFragmentClass != null) {
            findMethod("com.miui.powercenter.BatteryFragment\$a") {
                name == "run"
            }
        } else {
            findMethod("com.miui.powercenter.a\$a") {
                name == "run"
            }
        }.hookAfter { hookParam ->
            val context = AndroidAppHelper.currentApplication().applicationContext
            val isDarkMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
            val currentTemperatureState = context.resources.getIdentifier("current_temperature_state", "id", "com.miui.securitycenter")
            val view = hookParam.thisObject.getObjectAs<View>("a")

            val textView = view.findViewById<TextView>(currentTemperatureState)
            textView.apply {
                when (layoutParams) {
                    is LinearLayout.LayoutParams -> {
                        (layoutParams as LinearLayout.LayoutParams).topMargin = 0
                    }

                    is RelativeLayout.LayoutParams -> {
                        (layoutParams as RelativeLayout.LayoutParams).topMargin = dp2px(context, 15f)
                    }
                }
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 36.4f)
                setPadding(0, dp2px(context, 4f), 0, 0)
                gravity = Gravity.NO_GRAVITY
                typeface = Typeface.create(null, 700, false)
                height = dp2px(context, 49f)
                textAlignment = View.TEXT_ALIGNMENT_VIEW_START
            }

            val temperatureContainer = context.resources.getIdentifier("temperature_container", "id", "com.miui.securitycenter")
            val childView = view.findViewById<LinearLayout>(temperatureContainer).getChildAt(1)
            when (childView) {
                is LinearLayout -> {
                    childView.orientation = LinearLayout.VERTICAL
                    val l1 = childView.getChildAt(0)
                    val l2 = childView.getChildAt(1)
                    val linearLayout = LinearLayout(context)
                    val linearLayout1 = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }
                    val tempView = TextView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        (layoutParams as LinearLayout.LayoutParams).marginStart = dp2px(context, 3.599976f)
                        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13.1f)
                        setTextColor(Color.parseColor(if (isDarkMode) "#e6e6e6" else "#333333"))
                        setPadding(0, dp2px(context, 26f), 0, 0)
                        text = "℃"
                        gravity = Gravity.NO_GRAVITY
                        typeface = Typeface.create(null, 700, false)
                        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                    }
                    childView.removeAllViews()
                    linearLayout.addView(l1)
                    linearLayout1.addView(l2)
                    linearLayout1.addView(tempView)
                    childView.addView(linearLayout)
                    childView.addView(linearLayout1)
                }

                is RelativeLayout -> {
                    val l1 = childView.getChildAt(0)
                    val l2 = childView.getChildAt(1)
                    val linearLayout = LinearLayout(context)
                    val linearLayout1 = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }
                    val tempView = TextView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        (layoutParams as LinearLayout.LayoutParams).also {
                            it.marginStart = dp2px(context, 3.599976f)
                            it.topMargin = dp2px(context, 15f)
                        }
                        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13.1f)
                        setTextColor(Color.parseColor(if (isDarkMode) "#e6e6e6" else "#333333"))
                        text = "℃"
                        gravity = Gravity.NO_GRAVITY
                        typeface = Typeface.create(null, 700, false)
                        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                    }
                    childView.removeAllViews()
                    linearLayout.addView(l1)
                    linearLayout1.addView(l2)
                    linearLayout1.addView(tempView)
                    childView.addView(linearLayout)
                    childView.addView(linearLayout1)
                }
            }
        }
    }

    private fun getBatteryTemperature(context: Context): Int {
        return context.registerReceiver(null as BroadcastReceiver?, IntentFilter("android.intent.action.BATTERY_CHANGED"))!!
            .getIntExtra("temperature", 0) / 10
    }
}
