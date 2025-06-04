package com.zsrh.xwkeepalivelibrary

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import kotlin.coroutines.cancellation.CancellationException

class MyWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    private val tag = "MyWorker"

    override suspend fun doWork(): Result {
        Log.d(tag, "WorkManager 任务开始执行")

        // 设置 AlarmManager 闹钟
        AlarmUtil.setAlarm(applicationContext)
        Log.d(tag, "WorkManager 任务中设置 AlarmManager 闹钟")

        return try {
            for (i in 1..1000) {
                Log.d(tag, "WorkManager 任务执行中: $i")
                delay(5000) // 模拟耗时操作
            }
            Log.d(tag, "WorkManager 任务执行成功")
            Result.success()
        } catch (e: CancellationException) {
            Log.e(tag, "WorkManager 任务被取消: ${e.message}")
            // 重新调度任务
            return Result.retry()
        } catch (e: Exception) {
            Log.e(tag, "WorkManager 任务执行失败: ${e.message}")
            Result.failure()
        } finally {
            Log.d(tag, "WorkManager 任务执行结束")
        }
    }
}