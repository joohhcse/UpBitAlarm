package com.jooplayconsole.upbitalarm.ui.currentPrice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jooplayconsole.upbitalarm.MainActivity
import com.jooplayconsole.upbitalarm.R
import kotlinx.android.synthetic.main.fragment_current_price.*

class CurrentPriceFragment : Fragment() {
    /**
     * Initialize newInstance for passing parameters
     */
    companion object {
        fun newInstance(): CurrentPriceFragment {
            val fragmentHome = CurrentPriceFragment()
            val args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }

    }

    private lateinit var currentPriceViewModel: CurrentPriceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentPriceViewModel = ViewModelProvider(this).get(CurrentPriceViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_current_price, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)

//        val btnCreate : Button = fragment_current_price.findViewById(R.id.btn_create)
//        val btnRemove : Button = fragment_current_price.findViewById(R.id.btn_remove)
//        Log.d("[LOG:CoinAlarmFrg]", "Button create click! > $btnCreate")     //Null 출력됨


        currentPriceViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
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

            val mActivity = activity as MainActivity
            mActivity.networking("https://api.upbit.com/v1/candles/minutes/1?market=KRW-BTC&count=1")

            Log.d("[LOG:CoinAlarmFrg]", "Button remove click finished!")
        }

        //함수 주석처리 : onRequestCoinPrice()
//        val mActivity = activity as MainActivity
//        mActivity.onRequestCoinPrice()

        btn_test2.setOnClickListener {
            Log.d("[LOG:CoinAlarmFrg]", "Button test2 click!")

            val mActivity = activity as MainActivity
            mActivity.showSaveCoinAlarmDlg()
        }
    }
}