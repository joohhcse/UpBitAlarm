package com.jooplayconsole.upbitalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.jooplayconsole.upbitalarm.ui.coinAlarm.CoinAlarmActivity
import com.jooplayconsole.upbitalarm.util.CoinAlarmManager

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmReceiver", "#AlarmReceiver#")

        CoinAlarmManager.getInstance().startAlarm(context!!)
        val activityIntent = Intent(context, CoinAlarmActivity::class.java)
        activityIntent.putExtra("id", intent?.getIntExtra("id", 0))
        context.startActivity(activityIntent)
    }

    //    override fun onReceive(context: Context?, intent: Intent?) {
//
//        CoinAlarmManager.getInstance().startAlarm(context!!)
//        val activityIntent = Intent(context, CoinAlarmActivity::class.java)
//        activityIntent.putExtra("id", intent?.getIntExtra("id", 0))
//        context.startActivity(activityIntent)
//    }

}