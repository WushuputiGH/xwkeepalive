package com.zsrh.xwkeepalivelibrary

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log

class GuardService : Service() {

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.i("GuardService", "KeepAliveService 连接成功")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i("GuardService", "KeepAliveService 断开连接，重新启动")
            startService(Intent(this@GuardService, KeepAliveService::class.java))
            bindService(Intent(this@GuardService, KeepAliveService::class.java), this, Context.BIND_IMPORTANT)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("GuardService", "GuardService 启动")
        bindService(Intent(this, KeepAliveService::class.java), connection, Context.BIND_IMPORTANT)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }
}