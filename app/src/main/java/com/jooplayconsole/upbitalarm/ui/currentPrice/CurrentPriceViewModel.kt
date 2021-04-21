package com.jooplayconsole.upbitalarm.ui.currentPrice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentPriceViewModel : ViewModel() {

    //2nd View
    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text
}