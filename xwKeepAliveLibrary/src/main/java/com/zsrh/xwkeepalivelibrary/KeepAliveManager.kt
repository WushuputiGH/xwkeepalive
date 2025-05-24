package com.zsrh.xwkeepalivelibrary

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class KeepAliveManager(val context: Context) {

    private var isInitialized = false

    fun start() {
        if (isInitialized) {
            return
        }

        //
        // 启动 KeepAliveService
        val keepAliveIntent = Intent(context, KeepAliveService::class.java)
        context.startService(keepAliveIntent)

        // 启动 GuardService
        val guardIntent = Intent(context, GuardService::class.java)
        context.startService(guardIntent)

        // 调度 JobScheduler
        scheduleJob()

        // 调度 WorkManager
        scheduleWork()

        // 设置 AlarmManager
        AlarmUtil.setAlarm(context)

        isInitialized = true
    }

    fun stop() {
        if (!isInitialized) {
            return
        }

        // 取消 AlarmManager
        AlarmUtil.cancelAlarm(context)

        isInitialized = false
    }

    private fun scheduleJob() {
        val componentName = ComponentName(context, MyJobService::class.java)
        val builder = JobInfo.Builder(123, componentName)
            .setPeriodic(15 * 60 * 1000) // 15 minutes
            .setPersisted(true)

        builder.setRequiresCharging(false)
        builder.setRequiresDeviceIdle(false)

        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(builder.build())
    }

    private fun scheduleWork() {
        val workRequest = PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }
}

