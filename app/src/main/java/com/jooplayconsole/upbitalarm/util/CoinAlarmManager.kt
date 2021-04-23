package com.jooplayconsole.upbitalarm.util

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
//import android.arch.lifecycle.LiveData
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.util.Log
//import de.der_nik.wakeup.AlarmReceiver
//import de.der_nik.wakeup.model.AlarmTime
import java.util.*

class CoinAlarmManager {

    private var ringtone: Ringtone? = null

//    fun activateAlarm(context: Context, alarm: AlarmTime): Boolean {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val pendingIntent = getPendingIntent(context, alarm)
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, setDate(alarm), pendingIntent)
//        alarm.active = true
//        return true
//    }
//
//    fun deactivateAlarm(context: Context, alarm: AlarmTime): Boolean {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val pendingIntent = CoinAlarmManager.getInstance().getPendingIntent(context, alarm)
//        alarmManager.cancel(pendingIntent)
//        pendingIntent.cancel()
//        alarm.active = false
//        return true
//    }
//
//    fun snooze (context: Context, alarm: AlarmTime, offset: Int): Boolean{
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val pendingIntent = getPendingIntent(context, alarm)
//        var time = Date().time + (60000 * offset)
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent)
//        alarm.active = true
//        return true
//    }
//
//
//
//    private fun getPendingIntent(context: Context, alarm: AlarmTime): PendingIntent {
//        val alarmIntent = Intent(context, AlarmReceiver::class.java)
//        val id = alarm.id
//        alarmIntent.putExtra("id", id)
//        return PendingIntent.getBroadcast(context, id, alarmIntent, 0)
//    }
//    fun setDate(alarm: AlarmTime): Long {
//        val weekDays: List<Int> = getWeekList(alarm)
//        val cal = Calendar.getInstance() // locale-specific
//        cal.time = Date()
//        val curTime = cal.timeInMillis
//        cal.set(Calendar.HOUR_OF_DAY, alarm.hour)
//        cal.set(Calendar.MINUTE, alarm.minute)
//        cal.set(Calendar.SECOND, 0)
//        cal.set(Calendar.MILLISECOND, 0)
//        val timeToSet = cal.timeInMillis
//        if (curTime > timeToSet){
//            cal.add(Calendar.DATE, 1)
//        }
//        while(weekDays.isNotEmpty() && !weekDays.contains(cal.get(Calendar.DAY_OF_WEEK))){
//            cal.add(Calendar.DATE, 1)
//        }
//        alarm.date = cal.timeInMillis
//        return alarm.date
//    }
//
//    private fun getWeekList(alarm: AlarmTime): List<Int>{
//        val list: MutableList<Int> = mutableListOf()
//        if(alarm.mo) list.add(Calendar.MONDAY)
//        if(alarm.di) list.add(Calendar.TUESDAY)
//        if(alarm.mi) list.add(Calendar.WEDNESDAY)
//        if(alarm.don) list.add(Calendar.THURSDAY)
//        if(alarm.fr) list.add(Calendar.FRIDAY)
//        if(alarm.sa) list.add(Calendar.SATURDAY)
//        if(alarm.so) list.add(Calendar.SUNDAY)
//        return list
//
//
//    }


    fun getRingtone(context: Context): Ringtone {
        if (ringtone != null) {
            val tmpringtone = ringtone!!
            return tmpringtone
        }
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val tmpRingtone = RingtoneManager.getRingtone(context, uri)
        ringtone = tmpRingtone
        return tmpRingtone
    }

    fun startAlarm(context: Context){
        getRingtone(context).play()
    }

    fun stopAlarm(context: Context) {

        getRingtone(context).stop()
    }

    companion object {
        @Volatile
        private var INSTANCE: CoinAlarmManager? = null

        fun getInstance(): CoinAlarmManager {
            val tmpInstance = INSTANCE
            if(tmpInstance != null ) {
                return tmpInstance
            }

            val instance  = CoinAlarmManager()
            INSTANCE = instance
            return instance
        }
    }
}