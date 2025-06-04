package com.zsrh.xwkeepalivelibrary

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import kotlinx.coroutines.delay

class MyJobService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d("MyJobService", "Job 执行开始")


        // 在这里执行你的定时任务
        doBackgroundTask(params)

        // 返回 true 表示该 Job 尚未完成，系统应该持有 wakelock
        return true
    }

    private fun doBackgroundTask(params: JobParameters?) {
        Thread {
            try {
                // 模拟耗时操作
                for (i in 1..1000) {
                    Log.d("MyJobService", "MyJobService 后台任务执行中: $i")
                    Thread.sleep(5000) // 暂停 5 秒
                }
                Log.d("MyJobService", "MyJobService 后台任务执行完成")
            } catch (e: InterruptedException) {
                Log.e("MyJobService", "MyJobService 任务被中断: ${e.message}")
            } finally {
                // 任务完成后，调用 jobFinished() 方法
                jobFinished(params, false)
            }
        }.start()
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d("MyJobService", "Job 执行停止")

        // 返回 true 表示系统应该稍后重新调度该 Job
        return true
    }
}