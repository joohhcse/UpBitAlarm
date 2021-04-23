package com.jooplayconsole.upbitalarm.ui.coinAlarm

//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jooplayconsole.upbitalarm.R
//import de.der_nik.wakeup.R

class CoinAlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_alarm)
//        setSupportActionBar(findViewById(R.id.toolbar))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CoinAlarmFragment.newInstance())
                .commitNow()
        }
    }

}
