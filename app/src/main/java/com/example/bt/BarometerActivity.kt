package com.example.bt

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class BarometerActivity: AppCompatActivity() {
    lateinit var m_adress: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temperature_layout)
   //     ControlActivity.m_adress = intent.getStringExtra(MainActivity.EXTRA_ADRESS).toString()
        setLineChartData()
        findViewById<Button>(R.id.Main).setOnClickListener(View.OnClickListener { mainActivity() })
        findViewById<Button>(R.id.temperature_humidity).setOnClickListener(View.OnClickListener { temperatureActivity() })
        findViewById<Button>(R.id.uv).setOnClickListener(View.OnClickListener { uvActivity() })
    }

    fun mainActivity(){
        val myIntent: Intent = Intent(this@BarometerActivity, ControlActivity::class.java)
     //   myIntent.putExtra(MainActivity.EXTRA_ADRESS, ControlActivity.m_adress)
        this@BarometerActivity.startActivity(myIntent)
    }
    fun temperatureActivity(){
        val myIntent: Intent = Intent(this@BarometerActivity, TemperatureActivity::class.java)
     //   myIntent.putExtra(MainActivity.EXTRA_ADRESS_GRAF1, ControlActivity.m_adress)
        this@BarometerActivity.startActivity(myIntent)
    }
    fun uvActivity(){
        val myIntent: Intent = Intent(this@BarometerActivity, UVActivity::class.java)
     //  myIntent.putExtra(MainActivity.EXTRA_ADRESS_GRAF3, ControlActivity.m_adress)
        this@BarometerActivity.startActivity(myIntent)
    }
    fun setLineChartData(){
        val ez = findViewById<LineChart>(R.id.linechart)
        val xvalue = ArrayList<String>()
        xvalue.add("11:00 AM")
        xvalue.add("12:00 AM")
        xvalue.add("1:00 PM")
        xvalue.add("2:00 PM")
        xvalue.add("3:00 PM")
        xvalue.add("4:00 PM")
        xvalue.add("5:00 PM")
        xvalue.add("6:00 PM")




        val lineentry = ArrayList<Entry>()
        lineentry.add(Entry(1120f,1))
        lineentry.add(Entry(1115f,2))
        lineentry.add(Entry(1030f,3))
        lineentry.add(Entry(1195f,4))
        lineentry.add(Entry(1200f,5))
        lineentry.add(Entry(1150f,6))
        lineentry.add(Entry(1142f,7))
        lineentry.add(Entry(1100f,8))






        val linedataset = LineDataSet(lineentry, "FIRST")
        linedataset.color=resources.getColor(R.color.barometer_blue_data)

        linedataset.circleRadius= 5f
        linedataset.setCircleColor(R.color.barometer_blue_data)
        linedataset.setDrawFilled(true)
        linedataset.fillColor = resources.getColor(R.color.barometer_blue_data)
        val data = LineData(xvalue,linedataset)
        linedataset.fillAlpha = 30


        ez.data = data
        ez.setBackgroundColor(resources.getColor(R.color.graf_background))
        ez.animateXY(1000,1000)



    }
}