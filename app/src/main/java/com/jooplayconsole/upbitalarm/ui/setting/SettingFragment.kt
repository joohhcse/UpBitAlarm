package com.jooplayconsole.upbitalarm.ui.setting

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jooplayconsole.upbitalarm.R
import com.jooplayconsole.upbitalarm.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel

    lateinit var binding : FragmentSettingBinding

    private val data = arrayListOf<Todo>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //original code
//        settingViewModel = ViewModelProvider(this).get(SettingViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_setting, container, false)
//        val textView: TextView = root.findViewById(R.id.text_notifications)
//        settingViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
//        return root

        binding = FragmentSettingBinding.inflate(inflater, container, false)
//        binding.recyclerView2.layoutManager = LinearLayoutManager(this)

        return binding.root
    }
}


data class Todo(
    val text: String,
    var isDone: Boolean = false
)


class TodoAdapter(
    private val myDataset: List<Todo>,
    val onClickDeleteIcon: (todo: Todo) -> Unit,     //input이 하나고 output없는
    val onClickItem: (todo: Todo) -> Unit
) :
    RecyclerView.Adapter<TodoAdapter.SettingViewHolder>() {

    class SettingViewHolder(val binding: FragmentSettingBinding) : RecyclerView.ViewHolder(binding.root)
    //binding.root 모든 binding객체는 root라는 속성있어서 어떤 뷰로부터 생성된 binding인지 알수있음

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoAdapter.SettingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alarm, parent, false)
        return SettingViewHolder(FragmentSettingBinding.bind(view))
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
//        val todo = myDataset[position]
//        holder.binding.alarm_text.text = todo.text

//        if(todo.isDone) {
//            holder.binding.todoText.apply {
//                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//                setTypeface(null, Typeface.ITALIC)
//            }
//        }
//        else {
//            holder.binding.todoText.apply {
//                paintFlags = 0
//                setTypeface(null, Typeface.NORMAL)
//            }
//        }

//        holder.binding.deleteImageView.setOnClickListener {
//            onClickDeleteIcon.invoke(todo)
//        }
//
//        holder.binding.root.setOnClickListener {
//            onClickItem.invoke(todo)
//        }

    }

    override fun getItemCount() = myDataset.size
}




