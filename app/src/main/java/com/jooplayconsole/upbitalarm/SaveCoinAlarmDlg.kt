package com.jooplayconsole.upbitalarm

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.widget.Toast
import kotlinx.android.synthetic.main.dlg_set_alarm.*

class SaveCoinAlarmDlg(context: Context, var title: String, var content: String) : Dialog(context) {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dlg_set_alarm)

        text_title.text = title
//        text_title.setTextColor(R.color.black)
        btn_positive.text = "설정"
        btn_negative.text = "닫기"
        text_description_coin_name.text = "코인이름"
        text_description_alarm_price.text = "코인가격"
        text_description_condition.text = "조건"

        btn_positive.setOnClickListener {
            Toast.makeText(context, "설정 되었습니다", Toast.LENGTH_LONG).show()
            dismiss()   //Dialog class method
        }

        btn_negative.setOnClickListener {
//            Toast.makeText(context, "취소됬습니다.", Toast.LENGTH_LONG).show()
            dismiss()
        }
    }

}