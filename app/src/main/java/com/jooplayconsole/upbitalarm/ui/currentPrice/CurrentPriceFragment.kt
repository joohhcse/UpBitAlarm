package com.jooplayconsole.upbitalarm.ui.currentPrice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jooplayconsole.upbitalarm.R

class CurrentPriceFragment : Fragment() {

    private lateinit var currentPriceViewModel: CurrentPriceViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        currentPriceViewModel =
                ViewModelProvider(this).get(CurrentPriceViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_current_price, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        currentPriceViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}