package com.jooplayconsole.upbitalarm.ui.coinAlarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jooplayconsole.upbitalarm.R

class CoinAlarmFragment : Fragment() {

    private lateinit var coinAlarmViewModel: CoinAlarmViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coinAlarmViewModel = ViewModelProvider(this).get(CoinAlarmViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_alarm, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)

        coinAlarmViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
