package br.com.calendartesste.adapter

import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList


class MonthAdapter(
    c: Context,
    month: Int,
    year: Int,
    metrics: DisplayMetrics
) :
    BaseAdapter() {
    private val mCalendar: GregorianCalendar
    private val mCalendarToday: Calendar
    private val mContext: Context
    private val mDisplayMetrics: DisplayMetrics
    private var mItems: MutableList<String> = ArrayList()
    private val mMonth: Int
    private val mYear: Int
    private var mDaysShown = 0
    private var mDaysLastMonth = 0
    private var mDaysNextMonth = 0
    private var mTitleHeight = 0
    private var mDayHeight = 0
    private val mDays =
        arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    private val mDaysInMonth =
        intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    /**
     * @param date - null if day title (0 - dd / 1 - mm / 2 - yy)
     * @param position - position in item list
     * @param item - view for date
     */


    private fun populateMonth() {
        for (day in mDays) {
            mItems.add(day)
            mDaysShown++
        }
        val firstDay = getDay(mCalendar.get(Calendar.DAY_OF_WEEK))
        val prevDay: Int
        prevDay =
            if (mMonth == 0) daysInMonth(11) - firstDay + 1 else daysInMonth(mMonth - 1) - firstDay + 1
        for (i in 0 until firstDay) {
            mItems.add((prevDay + i).toString())
            mDaysLastMonth++
            mDaysShown++
        }
        val daysInMonth = daysInMonth(mMonth)
        for (i in 1..daysInMonth) {
            mItems.add(i.toString())
            mDaysShown++
        }
        mDaysNextMonth = 1
        while (mDaysShown % 7 != 0) {
            mItems.add(mDaysNextMonth.toString())
            mDaysShown++
            mDaysNextMonth++
        }
        mTitleHeight = 0
        val rows = mDaysShown / 7
        mDayHeight = (mDisplayMetrics.heightPixels - mTitleHeight
                - rows * 8 - barHeight) / (rows - 1)
    }

    private fun daysInMonth(month: Int): Int {
        var daysInMonth = mDaysInMonth[month]
        if (month == 1 && mCalendar.isLeapYear(mYear)) daysInMonth++
        return daysInMonth
    }

    private val barHeight: Int
        private get() = when (mDisplayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_HIGH -> 48
            DisplayMetrics.DENSITY_MEDIUM -> 32
            DisplayMetrics.DENSITY_LOW -> 24
            else -> 48
        }

    private fun getDay(day: Int): Int {
        return when (day) {
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5
            Calendar.SUNDAY -> 6
            else -> 0
        }
    }

    private fun isToday(day: Int, month: Int, year: Int): Boolean {
        return mCalendarToday.get(Calendar.MONTH) === month && mCalendarToday.get(Calendar.YEAR) === year && mCalendarToday.get(
            Calendar.DAY_OF_MONTH
        ) === day
    }

    private fun getDate(position: Int): IntArray? {
        val date = IntArray(3)
        if (position <= 6) {
            return null // day names
        } else if (position <= mDaysLastMonth + 6) { // previous month
            date[0] = mItems!![position].toInt()
            if (mMonth == 0) {
                date[1] = 11
                date[2] = mYear - 1
            } else {
                date[1] = mMonth - 1
                date[2] = mYear
            }
        } else if (position <= mDaysShown - mDaysNextMonth) { // current month
            date[0] = position - (mDaysLastMonth + 6)
            date[1] = mMonth
            date[2] = mYear
        } else { // next month
            date[0] = mItems!![position].toInt()
            if (mMonth == 11) {
                date[1] = 0
                date[2] = mYear + 1
            } else {
                date[1] = mMonth + 1
                date[2] = mYear
            }
        }
        return date
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = TextView(mContext)
        view.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
        view.text = mItems[position]
        view.setTextColor(Color.BLACK)
        val date = getDate(position)
        if (date != null) {
            view.height = mDayHeight
            if (date[1] != mMonth) { // previous or next month
                view.setBackgroundColor(Color.rgb(234, 234, 250))
            } else { // current month
                view.setBackgroundColor(Color.rgb(244, 244, 244))
                if (isToday(date[0], date[1], date[2])) {
                    view.setTextColor(Color.RED)
                }
            }
        } else {
            view.setBackgroundColor(Color.argb(100, 10, 80, 255))
            view.height = mTitleHeight
        }
        return view
    }

    override fun getCount(): Int {
        return mItems.size
    }

    override fun getItem(position: Int): Any {
        return mItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    init {
        mContext = c
        mMonth = month
        mYear = year
        mCalendar = GregorianCalendar(mYear, mMonth, 1)
        mCalendarToday = Calendar.getInstance()
        mDisplayMetrics = metrics
        populateMonth()
    }
}