package br.com.calendartesste.model

data class PeriodCalendar(val year :Int,val month : Int, val days : List<PeriodCalendarDay>,val currentMonth : Boolean)

data class PeriodCalendarDay( val label : String,val isInvisible : Boolean,val isWeekend : Boolean,val currentDay:Boolean)
