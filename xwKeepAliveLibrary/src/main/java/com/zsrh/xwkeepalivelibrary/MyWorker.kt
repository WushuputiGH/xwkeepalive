package com.zsrh.xwkeepalivelibrary

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.util.Log
import kotlinx.coroutines.delay

class MyWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    private val tag = "MyWorker"

    override fun doWork(): Result {
        Log.d("MyWorker", "Work 执行开始")

        // 在这里执行你的后台任务
        try {
            for (i in 1..1000) {
                Log.d(tag, "WorkManager 任务执行中: $i")
                Thread.sleep(5000)
            }

        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        Log.d("MyWorker", "Work 执行完成")

        // Indicate whether the task finished successfully with the Result
        return Result.success()
    }




}