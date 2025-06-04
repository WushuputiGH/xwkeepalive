package com.zsrh.xwkeepalivelibrary

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest.Companion.MIN_BACKOFF_MILLIS
import java.util.Calendar
import java.util.concurrent.TimeUnit

class KeepAliveManager(val context: Context) {

    private var isInitialized = false
    private val tag = "KeepAliveManager"

    fun start() {
        if (isInitialized) {
            Log.d(tag, "KeepAliveManager 已经初始化，无需再次启动")
            return
        }

        scheduleJob()

        // 调度 WorkManager
        scheduleWork()

        // 启动 AlarmManager
        startAlarm()

        isInitialized = true
    }


    private fun scheduleJob() {
        val componentName = ComponentName(context, MyJobService::class.java)
        val jobInfo = JobInfo.Builder(123, componentName)
            .setPeriodic(15 * 60 * 1000) // 设置 Job 的执行周期为 15 分钟
            .setPersisted(true) // 设置设备重启后 Job 是否继续执行
            .setRequiresCharging(false) // 设置是否需要在充电时执行
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // 设置是否需要在有网络连接时执行
            .build()

        val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val result = scheduler.schedule(jobInfo)

        if (result == JobScheduler.RESULT_SUCCESS) {
            println("Job scheduled successfully!")
        } else {
            println("Job scheduled failed!")
        }
    }

    private fun scheduleWork() {
        val workRequest = PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

    private fun startAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = "com.example.backgroundkeepalive.ALARM_ACTION"
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.MINUTE, 10)

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, 10 * 60 * 1000, pendingIntent)
    }

    private fun cancelWork() {
        WorkManager.getInstance(context).cancelAllWork()
        Log.d(tag, "所有 WorkManager 任务取消成功")
    }
}