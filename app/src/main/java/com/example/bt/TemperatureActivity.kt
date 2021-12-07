package com.example.bt

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bt.data.DataRepository
import com.example.bt.data.SensorDataDatabase
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class TemperatureActivity: AppCompatActivity() {
    lateinit var m_adress: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temperature_layout)
        ControlActivity.m_adress = intent.getStringExtra(MainActivity.EXTRA_ADRESS).toString()
        setLineChartData()
        findViewById<Button>(R.id.Main).setOnClickListener(View.OnClickListener { mainActivity() })
        findViewById<Button>(R.id.barometer).setOnClickListener(View.OnClickListener { barometerActivity() })
        findViewById<Button>(R.id.uv).setOnClickListener(View.OnClickListener { uvActivity() })
        val repository = DataRepository(SensorDataDatabase.getDatabase(applicationContext).dataDao())


        repository.readAllBarometerData.observe(this,{ Log.i("tag",it.size.toString()) })
    }
    fun mainActivity(){
        val myIntent: Intent = Intent(this@TemperatureActivity, ControlActivity::class.java)
        myIntent.putExtra(MainActivity.EXTRA_ADRESS, ControlActivity.m_adress)
        this@TemperatureActivity.startActivity(myIntent)
    }
    fun barometerActivity(){
        val myIntent: Intent = Intent(this@TemperatureActivity, BarometerActivity::class.java)
        myIntent.putExtra(MainActivity.EXTRA_ADRESS, ControlActivity.m_adress)
        this@TemperatureActivity.startActivity(myIntent)
    }
    fun uvActivity(){
        val myIntent: Intent = Intent(this@TemperatureActivity, UVActivity::class.java)
        myIntent.putExtra(MainActivity.EXTRA_ADRESS, ControlActivity.m_adress)
        this@TemperatureActivity.startActivity(myIntent)
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
        lineentry.add(Entry(18f,1))
        lineentry.add(Entry(22f,2))
        lineentry.add(Entry(21f,3))
        lineentry.add(Entry(23f,4))
        lineentry.add(Entry(27f,5))
        lineentry.add(Entry(26f,6))
        lineentry.add(Entry(30f,7))
        lineentry.add(Entry(32f,8))





        val linedataset = LineDataSet(lineentry, "FIRST")
        linedataset.color=resources.getColor(R.color.humidity_green_data)

        linedataset.circleRadius= 5f
        linedataset.setCircleColor(R.color.humidity_green_data)
        linedataset.setDrawFilled(true)
        linedataset.fillColor = resources.getColor(R.color.humidity_green_data)
        val data = LineData(xvalue,linedataset)
        linedataset.fillAlpha = 30


        ez.data = data
        ez.setBackgroundColor(resources.getColor(R.color.graf_background))
        ez.animateXY(1000,1000)



    }
}