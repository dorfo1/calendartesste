package br.com.calendartesste.utils

import br.com.calendartesste.model.PeriodCalendar
import br.com.calendartesste.model.PeriodCalendarDay
import java.lang.Exception

import java.util.*

class CalendarHelper {

    fun generateCalendarInterval(initialYear : Int, lastYear:Int) : List<PeriodCalendar>{
        val list = mutableListOf<PeriodCalendar>()
        for(year in initialYear..lastYear){
            for(month in Calendar.JANUARY..Calendar.DECEMBER){
                val calendar = GregorianCalendar(year,month,1)
                list.add(PeriodCalendar(year,month,populateDaysInMonth(calendar),Calendar.getInstance().isCurrentMonth(year, month)))
            }
        }
        return list
    }

    fun getCurrentMonthAndYearPosition(list : List<PeriodCalendar>) : Int{
       list.forEachIndexed{ index, periodCalendar ->
           if(periodCalendar.currentMonth) return index
       }
        throw Exception()
    }

    private fun populateDaysInMonth(calendar: GregorianCalendar) : List<PeriodCalendarDay> {
        val days = mutableListOf<PeriodCalendarDay>()
        val firstDay = calendar.get(Calendar.DAY_OF_WEEK).minus(1)
        val prevDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH).minus(1)
        for (i in 0 until firstDay) {
            val label = (prevDay+1).toString()
            days.add(PeriodCalendarDay(label, isInvisible = true, isWeekend = false, currentDay = false))
        }

        val daysInMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH).minus(1) + 1
        for (i in 1..daysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH,i)
            days.add(PeriodCalendarDay(i.toString(),false,calendar.isWeekend(),calendar.isCurrentDay(Calendar.getInstance())))
        }
        return days
    }

}


fun Calendar.isCurrentDay(currentCalendar : Calendar): Boolean {
    return  get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR) &&
            get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH) &&
            get(Calendar.DAY_OF_MONTH) == currentCalendar.get(Calendar.DAY_OF_MONTH)
}

fun Calendar.isCurrentMonth(year:Int,month:Int) : Boolean{
    return get(Calendar.YEAR) == year && get(Calendar.MONTH) == month
}

fun Calendar.isWeekend() : Boolean{
    return get(Calendar.DAY_OF_WEEK) == 7 || get(Calendar.DAY_OF_WEEK) == 1
}