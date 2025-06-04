package com.zsrh.xwkeepalivelibrary

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {
    private val tag = "AlarmReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(tag, "AlarmReceiver 接收到广播，启动 WorkManager 和 JobScheduler")

        // 启动 WorkManager 任务
        KeepAliveManager(context).scheduleWork()

        // 启动 JobScheduler 任务
        KeepAliveManager(context).scheduleJob()
    }
}