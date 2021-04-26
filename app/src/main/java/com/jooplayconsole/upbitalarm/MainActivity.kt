package com.jooplayconsole.upbitalarm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
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

//                    val jsonStr = """$content""".trimIndent()
                    val jsonStr = "$content"
                    Log.d("[MainActivity:jsonStr]", "jsonString > $jsonStr")
                    val jsonObj = JSONObject(jsonStr)
                    val curPrice = jsonObj.getDouble("trade_price")

//                    val curPrice = JSONObject(jsonStr).getJSONArray("trade_price")
//                    val curPrice = JSONArray(jsonStr).
                    Log.d("[MainActivity]", "curPrice > $curPrice")

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
    
}