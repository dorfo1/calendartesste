package br.com.calendartesste.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.com.calendartesste.R
import br.com.calendartesste.model.PeriodCalendarDay

class DaysAdapter(private val days: List<PeriodCalendarDay>,val listener: (String) -> Unit) :
    BaseAdapter() {

    private var selectedPosition: Int? = null


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val periodCalendarDay = days[position]
        val view: View = convertView ?: LayoutInflater.from(parent?.context).inflate(
            R.layout.text_day,
            parent,
            false
        )

        val tv = view.findViewById<TextView>(R.id.textday)
        when {
            periodCalendarDay.isInvisible -> {
                tv.visibility = View.INVISIBLE
            }
            periodCalendarDay.isWeekend -> {
                tv.setTextColor(Color.LTGRAY)
                tv.text = periodCalendarDay.label
            }
            periodCalendarDay.currentDay -> {
                tv.setTextColor(Color.GREEN)
                tv.text = periodCalendarDay.label
            }
            else -> {
                tv.text = periodCalendarDay.label
            }
        }

        if(selectedPosition == position){
            view.background = parent?.context?.getDrawable(R.drawable.circle)
            tv.setTextColor(Color.WHITE)
        }else{
            view.background = null
        }

        view.setOnClickListener {
            if (!periodCalendarDay.isInvisible) {
                view.background = parent?.context?.getDrawable(R.drawable.circle)
                setSeletecPosition(position)
                if(!periodCalendarDay.isInvisible) listener(periodCalendarDay.label)
            }
        }

        return view
    }

    private fun setSeletecPosition(position: Int) {
        selectedPosition = if (selectedPosition != null) {
            position
        } else {
            notifyDataSetChanged()
            position
        }
    }


    override fun getItem(p0: Int): Any {
        return days[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return days.size
    }

}
