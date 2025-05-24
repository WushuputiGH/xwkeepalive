package com.zsrh.xwkeepalivelibrary

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import android.provider.Settings

object BatteryOptimizationUtil {


    // 判读是否打开了电池优化
    fun isOpenIgnoreBattery(context: Context): Boolean{
        val packageName = context.packageName
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return pm.isIgnoringBatteryOptimizations(packageName)
    }


    // 请求忽略电池优化
    fun requestIgnoreBatteryOptimization(context: Context) {
        val packageName = context.packageName
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            intent.data = Uri.parse("package:$packageName")
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity(intent)
        }
    }
}