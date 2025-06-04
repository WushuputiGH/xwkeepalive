package com.zsrh.xwkeepalivelibrary

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class MyJobService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d("MyJobService", "Job 执行开始")

        // 在这里执行你的定时任务
        doSomeWork(params)

        // 返回 true 表示该 Job 尚未完成，系统应该持有 wakelock
        return true
    }

    private fun doSomeWork(params: JobParameters?) {
        Thread {
            // 模拟耗时操作
            for (i in 1..1000) {
                Log.d("MyJobService", "MyJobService 任务执行中: $i")
                Thread.sleep(5000)
            }

            Log.d("MyJobService", "Job 执行完成")

            // 任务完成后，通知系统 Job 已经完成
            jobFinished(params, false)
        }.start()
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d("MyJobService", "Job 执行停止")

        // 返回 true 表示系统应该稍后重新调度该 Job
        return true
    }
}