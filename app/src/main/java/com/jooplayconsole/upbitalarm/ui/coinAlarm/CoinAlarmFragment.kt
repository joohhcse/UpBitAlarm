package com.jooplayconsole.upbitalarm.ui.coinAlarm

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
import com.jooplayconsole.upbitalarm.R

class CoinAlarmFragment : Fragment() {
    var iD = 0

    companion object {
        fun newInstance() = CoinAlarmFragment()
    }

    private lateinit var coinAlarmViewModel: CoinAlarmViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coinAlarmViewModel = ViewModelProvider(this).get(CoinAlarmViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_alarm, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        val btnCreate : Button = root.findViewById(R.id.create)

        Log.d("CreateNotif", "start>>>>>>>>>>>>>>>>")
        Log.d("[LOG]", "Button create click! > $btnCreate")     //Null 출력됨

        btnCreate.setOnClickListener {
            Log.d("[LOG]", "Button create click!")
        }

        coinAlarmViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }
}
