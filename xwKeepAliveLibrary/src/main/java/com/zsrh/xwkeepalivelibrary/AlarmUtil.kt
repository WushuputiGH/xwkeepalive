package com.zsrh.xwkeepalivelibrary


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log

object AlarmUtil {

    private val tag = "AlarmUtil"
    private val alarmAction = "com.zsrh.xwkeeplivelibrary.ALARM_ACTION"

    fun setAlarm(context: Context) {
        Log.d(tag, "Setting alarm")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = alarmAction
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val intervalMillis = 60 * 1000L // 1 minute
        val triggerAtMillis = System.currentTimeMillis() + intervalMillis

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
    }

    fun cancelAlarm(context: Context) {
        Log.d(tag, "Canceling alarm")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = alarmAction
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        alarmManager.cancel(pendingIntent)
    }
}