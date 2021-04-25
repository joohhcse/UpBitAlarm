package com.jooplayconsole.upbitalarm.ui.coinAlarm

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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jooplayconsole.upbitalarm.MainActivity
import com.jooplayconsole.upbitalarm.R
import kotlinx.android.synthetic.main.fragment_alarm.*

class CoinAlarmFragment : Fragment() {

    interface OnDataPassListener {
        fun onDataPass()
    }

    lateinit var dataPassListener: OnDataPassListener

    var iD = 0

    companion object {
        fun newInstance() = CoinAlarmFragment()
    }

    private lateinit var coinAlarmViewModel: CoinAlarmViewModel

    //new
//    var mCoinAlarmActivity : CoinAlarmActivity? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mCoinAlarmActivity = this.applicationConext as CoinAlarmActivity
//
//    } //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iD = activity?.intent?.getIntExtra("id", 0) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setContentView(R.layout.fragment_alarm)
        

        //패캠강의 메모
        //fragment가 인터페이스를 처음 그릴때 onCreateView 호출된다
        //(activity같은 경우는 onCreate가 호출됨)

        //inflater : 뷰를 그려주는 역할
        //container : 부모뷰
//        return inflater.inflate(R.layout.fragment_alarm, container, false)

        coinAlarmViewModel = ViewModelProvider(this).get(CoinAlarmViewModel::class.java)
        val fragmentAlarm = inflater.inflate(R.layout.fragment_alarm, container, false)
        val textView: TextView = fragmentAlarm.findViewById(R.id.text_dashboard)
        val btnCreate : Button = fragmentAlarm.findViewById(R.id.btn_create)
        val btnRemove : Button = fragmentAlarm.findViewById(R.id.btn_remove)

        Log.d("[LOG:CoinAlarmFrg]", "Button create click! > $btnCreate")     //Null 출력됨



        coinAlarmViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return fragmentAlarm
    }

    private fun setContentView(fragmentAlarm: Int) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_create.setOnClickListener {
            Log.d("[LOG:CoinAlarmFrg]", "Button create click!")

            val mActivity = activity as MainActivity
            mActivity.show()

            //error
//            (activity as CoinAlarmActivity).show()
//            val mActivity = activity as CoinAlarmActivity
//            mActivity.show()

            Log.d("[LOG:CoinAlarmFrg]", "Button create click finished!")
        }

        btn_remove.setOnClickListener {
            Log.d("[LOG:CoinAlarmFrg]", "Button remove click!")
//            hide()
            Log.d("[LOG:CoinAlarmFrg]", "Button remove click finished!")
        }

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        btn_create.setOnClickListener {
//            dataPassListener.onDataPass("GoodBye")
//        }
//    }


}
