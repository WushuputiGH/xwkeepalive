package com.zsrh.xwkeepalivelibrary

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest.Companion.MIN_BACKOFF_MILLIS
import java.util.concurrent.TimeUnit

class KeepAliveManager(val context: Context) {

    private var isInitialized = false
    private val tag = "KeepAliveManager"

    fun start() {
        if (isInitialized) {
            Log.d(tag, "KeepAliveManager 已经初始化，无需再次启动")
            return
        }

        Log.d(tag, "KeepAliveManager 启动")

        // 设置 AlarmManager
        AlarmUtil.setAlarm(context)
        Log.d(tag, "AlarmManager 设置")

        // 调度 JobScheduler
        scheduleJob()
        Log.d(tag, "JobScheduler 调度")

        // 调度 WorkManager
        scheduleWork()
        Log.d(tag, "WorkManager 调度")

        isInitialized = true
    }

    fun stop() {
        if (!isInitialized) {
            Log.d(tag, "KeepAliveManager 未初始化，无需停止")
            return
        }

        Log.d(tag, "KeepAliveManager 停止")

        // 取消 AlarmManager
        AlarmUtil.cancelAlarm(context)
        Log.d(tag, "AlarmManager 取消")

        // 停止 JobScheduler
        cancelJob()
        Log.d(tag, "JobScheduler 停止")

        // 停止 WorkManager
        cancelWork()
        Log.d(tag, "WorkManager 停止")

        isInitialized = false
    }

    fun scheduleJob() {
        val componentName = ComponentName(context, MyJobService::class.java)
        val builder = JobInfo.Builder(123, componentName)
            .setPeriodic(15 * 60 * 1000) // 15 minutes
            .setPersisted(true)

        builder.setRequiresCharging(false)
        builder.setRequiresDeviceIdle(false)

        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(builder.build())
        Log.d(tag, "JobScheduler 任务调度成功")
    }

    private fun cancelJob() {
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(123)
        Log.d(tag, "JobScheduler 任务取消成功")
    }

    fun scheduleWork() {
        // 创建约束条件


        // 创建一次性任务
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setBackoffCriteria( // 设置退避策略
                BackoffPolicy.EXPONENTIAL,
                MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        // 创建定期任务（例如，每隔 15 分钟执行一次）
        val periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
            .build()

        // 获取 WorkManager 实例
        val workManager = WorkManager.getInstance(context)

        // 提交一次性任务
        workManager.enqueue(oneTimeWorkRequest)
        Log.d(tag, "一次性 WorkManager 任务提交成功")

        // 提交定期任务
        workManager.enqueueUniquePeriodicWork("uniqueWorkName",
            ExistingPeriodicWorkPolicy.KEEP,periodicWorkRequest)
        Log.d(tag, "定期 WorkManager 任务提交成功")
    }

    private fun cancelWork() {
        WorkManager.getInstance(context).cancelAllWork()
        Log.d(tag, "所有 WorkManager 任务取消成功")
    }
}