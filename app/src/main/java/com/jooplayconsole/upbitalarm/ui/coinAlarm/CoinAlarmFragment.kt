package com.jooplayconsole.upbitalarm.ui.coinAlarm

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.jooplayconsole.upbitalarm.MainActivity
import com.jooplayconsole.upbitalarm.R
import com.jooplayconsole.upbitalarm.MyDialog
import com.jooplayconsole.upbitalarm.databinding.FragmentAlarmBinding
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

    lateinit var binding: FragmentAlarmBinding

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

        //Fragment에서는 액티비티와 다르게 LayoutInflater를 onCreateView에서 넘겨받음
        //lateinit으로 첨에 선언해주는게 편함
        binding = FragmentAlarmBinding.inflate(inflater, container, false)

        //패캠강의 메모
        //fragment가 인터페이스를 처음 그릴때 onCreateView 호출된다
        //(activity같은 경우는 onCreate가 호출됨)

        //inflater : 뷰를 그려주는 역할
        //container : 부모뷰
//        return inflater.inflate(R.layout.fragment_alarm, container, false)

        coinAlarmViewModel = ViewModelProvider(this).get(CoinAlarmViewModel::class.java)
        val fragmentAlarm = inflater.inflate(R.layout.fragment_alarm, container, false)
        val textView: TextView = fragmentAlarm.findViewById(R.id.fragment_alarm_title)
//        val btnCreate : Button = fragmentAlarm.findViewById(R.id.btn_create)
//        val btnRemove : Button = fragmentAlarm.findViewById(R.id.btn_remove)
//        Log.d("[LOG:CoinAlarmFrg]", "Button create click! > $btnCreate")     //Null 출력됨


        coinAlarmViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return fragmentAlarm
    }

    private fun setContentView(fragmentAlarm: Int) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        btn_create.setOnClickListener {
//            dataPassListener.onDataPass("GoodBye")
//        }
//    }

}


//DB없이 데이터 save/load
//SharedPreference 방식


//
//class TodoAdapter(
//    private val myDataset: List<Todo>,
//    val onClickDeleteIcon: (todo: Todo) -> Unit,     //input이 하나고 output없는
//    val onClickItem: (todo: Todo) -> Unit
//) :
//    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
//
//    class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): TodoAdapter.TodoViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_todo, parent, false)
//        return TodoViewHolder(ItemTodoBinding.bind(view))
//    }
//
//    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
//        val todo = myDataset[position]
//        holder.binding.todoText.text = todo.text
//
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
//
//        holder.binding.deleteImageView.setOnClickListener {
//            onClickDeleteIcon.invoke(todo)
//        }
//
//        holder.binding.root.setOnClickListener {
//            onClickItem.invoke(todo)
//        }
//
//    }
//
//    override fun getItemCount() = myDataset.size
//}