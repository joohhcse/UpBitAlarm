package com.jooplayconsole.upbitalarm

import android.Manifest
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jooplayconsole.upbitalarm.ui.coinAlarm.CoinAlarmFragment
import com.jooplayconsole.upbitalarm.ui.currentPrice.CurrentPriceFragment
import com.jooplayconsole.upbitalarm.ui.setting.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private var FINISH_INTERVAL_TIME: Long = 1500
    private var backPressedTime: Long = 0
    var REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val num=1
    val num2 =2

    //20210425
    private var content: FrameLayout? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_current_price -> {

                val fragment = CurrentPriceFragment.Companion.newInstance()
                addFragment(fragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_alarm -> {
                val fragment = CoinAlarmFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_setting -> {
                val fragment = SettingFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    //20210428 coin_array spinner
    val items: Array<String> = resources.getStringArray(R.array.coin_array)
    val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
//    val myAdapter = ArrayAdapter.createFromResource(this, items, android.R.layout.simple_spinner_dropdown_item)

//    coin_spinner.adapter = myAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        content = findViewById(R.id.content)
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragment = CurrentPriceFragment.Companion.newInstance()
        addFragment(fragment)

//        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//        val navController = findNavController(R.id.nav_host_fragment)
//
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_current_price, R.id.navigation_alarm, R.id.navigation_setting
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    /**
     * add/replace fragment in container [FrameLayout]
     */
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime
        if (!(0 > intervalTime || FINISH_INTERVAL_TIME < intervalTime)) {
            finishAffinity()
            System.runFinalization()
            System.exit(0)
        } else {
            backPressedTime = tempTime
            Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            return
        }
        super.onBackPressed()
    }

    //Alarm 호출
    public fun show() {
        Log.d("CoinAlarmActivity", "CoinAlarmActivity show()!")

        var builder = NotificationCompat.Builder(this, "default")
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle("CoinAlarm Title")
            .setContentText("CoinAlarm detail text")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        var intent = Intent(this, MainActivity::class.java)

        var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        builder.setContentIntent(pendingIntent)

        var largeIcon : Bitmap
        largeIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)

        builder.setLargeIcon(largeIcon)
        builder.setColor(Color.RED)

        //RingtoneManager.TYPE_ALARM
//        val ringtoneUri : Uri = RingtoneManager.getActualDefaultRingtoneUri(this,
//            RingtoneManager.TYPE_ALARM)     //null : NullPointerException
//        builder.setSound(ringtoneUri)

        val ringtoneUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        builder.setSound(ringtoneUri)

        var vibrate = longArrayOf(0, 100, 200, 300)
        builder.setVibrate(vibrate)
        builder.setAutoCancel(true)

        val notificationManager = getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

        //오레오 버전 이상에서만
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //// Create the NotificationChannel
//            val name = getString(R.string.channel_name)
//            val descriptionText = getString(R.string.channel_description)
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("default", "defaultChannel", NotificationManager.IMPORTANCE_DEFAULT)
            mChannel.description = "defaultChannel"

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(mChannel)
        }

        notificationManager.notify(1, builder.build())  //return notification object by builder
    }

    //Alarm 해제
    public fun hide() {
        Log.d("CoinAlarmActivity", "CoinAlarmActivity hide()!")
//        NotificationCompat.from(this).cancel(1)
    }

    fun networking(urlString: String) {
        thread(start=true) {
            // 네트워킹 예외처리를 위한 try ~ catch 문
            try {
                val url = URL(urlString)

                // 서버와의 연결 생성
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"

                if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    // 데이터 읽기
                    val streamReader = InputStreamReader(urlConnection.inputStream)
                    val buffered = BufferedReader(streamReader)

                    val content = StringBuilder()
                    while(true) {
                        val line = buffered.readLine() ?: break
                        content.append(line)
                    }

                    Log.d("[MainActivity]", "fun networking!!!")
                    Log.d("[MainActivity]", "content > $content")

                    val jsonStr = content.toString()
                    Log.d("[MainActivity:jsonStr]", "jsonString > $jsonStr")

//                    [
//                        {
//                            "market":"KRW-BTC",   1
//                            "candle_date_time_utc":"2021-04-26T13:49:00", 2
//                            "candle_date_time_kst":"2021-04-26T22:49:00", 3
//                            "opening_price":63486000.00000000,    4
//                            "high_price":63486000.00000000,   5
//                            "low_price":63481000.00000000,    6
//                            "trade_price":63484000.00000000,  7
//                            "timestamp":1619444952832,    8
//                            "candle_acc_trade_price":49435153.10161000,   9
//                            "candle_acc_trade_volume":0.77873530, 10
//                            "unit":1
//                        }
//                    ]

//                    val jsonObj = JSONArray(jsonStr)
//                    val curPrice = jsonObj.getString(0)
                    val jArray = JSONArray(jsonStr)
                    for (i in 0 until jArray.length()) {
                        val obj = jArray.getJSONObject(i)
                        val market = obj.getString("market")
                        val tradePrice = obj.getInt("trade_price")
                        Log.d("[MainActivity]", "market($i) > $market")
                        Log.d("[MainActivity]", "market($i) > $tradePrice")
                    }

//TEST
//                    val curPrice = JSONObject(jsonStr).getJSONArray("trade_price")
//                    val curPrice = JSONArray(jsonStr).
//                    Log.d("[MainActivity]", "curPrice > $curPrice")

                    // 스트림과 커넥션 해제
                    buffered.close()
                    urlConnection.disconnect()
                    runOnUiThread {
                    // UI 작업
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //Test Temp function
    fun onRequestCoinPrice(): Double {
        val url = URL("https://api.upbit.com/v1/candles/minutes/1?market=KRW-BTC&count=1")
        val conn = url.openConnection() as HttpURLConnection

        BufferedReader(InputStreamReader (conn.inputStream, Charset.forName("UTF-8"))).use { reader ->
            // Bid : 살 때,
            // Ask : 팔 때,
            // Last : 최근 거래 가격
            // {"success":true,"message":"","result":{"Bid":0.65900000,"Ask":0.65993000,"Last":0.65993000}}
            val response = reader.readLine()
            val json = JSONObject(response)
            val curPrice = (json["result"] as JSONObject).get("trade_price")
            Log.d("[MainActivity]", "curPrice > $curPrice")

            return curPrice as Double
        }
    }

    //alarmlist(btn2)
    //Temp & Exam..
    fun showDialogToSaveAlarm() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.alarm_list_1, null)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("코인 알람 설정하기")
            .setPositiveButton("확인") { dialog, which ->

                //  editText -> invitee로 받아오기
                //  findViewById로 연결해줘야함
//                val textView: TextView = view.findViewById(R.id.editText)
//                val textView: TextView = "HelloWorld"
//                var invitee = textView.text.toString()

                // ...

            }
            .setNeutralButton("취소", null)
            .create()

        //  여백 눌러도 창 안없어지게
        alertDialog.setCancelable(false)

        alertDialog.setView(view)
        alertDialog.show()
    }



}