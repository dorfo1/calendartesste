package br.com.calendartesste.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.calendartesste.R
import br.com.calendartesste.model.PeriodCalendar
import kotlinx.android.synthetic.main.calendar_item_row.view.*
import java.util.*

class CalendarAdapter(val periodList: List<PeriodCalendar>, val listener : (Date) -> Unit) : RecyclerView.Adapter<CalendarAdapter.CalendarHolder>() {


    private var selectedPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_item_row, parent, false)
        return CalendarHolder(view)
    }

    override fun getItemCount(): Int {
        return periodList.size
    }

    override fun onBindViewHolder(holder: CalendarHolder, position: Int) {
        holder.bind(periodList[position],listener,this)
    }

    class CalendarHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(periodCalendar: PeriodCalendar, listener: (Date) -> Unit, calendarAdapter: CalendarAdapter) {
            val cal = Calendar.getInstance()
            cal.set(Calendar.MONTH,periodCalendar.month)
            itemView.calendarMonth.text = cal.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale("pt","BR"))?.capitalize() + " de "+ periodCalendar.year
            itemView.gridview.adapter = DaysAdapter(periodCalendar.days){clickedDay ->
                listener(GregorianCalendar(periodCalendar.year,periodCalendar.month,clickedDay.toInt()).time)
                setSeletecPosition(adapterPosition,calendarAdapter)
            }
        }


        private fun setSeletecPosition(position: Int, calendarAdapter: CalendarAdapter) {
            calendarAdapter.selectedPosition = if (calendarAdapter.selectedPosition != null) {
                position
            } else {
                calendarAdapter.notifyItemChanged(position)
                position
            }
        }


    }

}