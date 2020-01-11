package br.com.calendartesste

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.calendartesste.adapter.CalendarAdapter
import br.com.calendartesste.adapter.MonthAdapter
import br.com.calendartesste.utils.CalendarHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val generateCalendarInterval = CalendarHelper().generateCalendarInterval(2019, 2020)
        val manager = LinearLayoutManager(this)
        rv.adapter = CalendarAdapter(generateCalendarInterval){
            println(it)
        }
        rv.layoutManager = manager
        try {
            val position = CalendarHelper().getCurrentMonthAndYearPosition(generateCalendarInterval)
            manager.scrollToPositionWithOffset(position,0)
        }catch (ex : Exception){
            Toast.makeText(this,"Falha ao gerar calendário",Toast.LENGTH_SHORT).show()
        }


    }
}
