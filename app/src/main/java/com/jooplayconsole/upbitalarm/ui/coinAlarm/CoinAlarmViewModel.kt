package com.jooplayconsole.upbitalarm.ui.coinAlarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CoinAlarmViewModel : ViewModel() {

    //1st View
    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text


}