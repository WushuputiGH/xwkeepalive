package com.zsrh.xwkeepalivelibrary

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            // 启动所有保活机制
            KeepAliveManager(context).start();
        }
    }
}