package com.example.bt

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bt.data.DataRepository
import com.example.bt.data.SensorDataDatabase
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class temperatureFragment : Fragment() {


    var viewh : View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = DataRepository(SensorDataDatabase.getDatabase(requireActivity().application).dataDao())
        repository.readAllBarometerData.observe(this,{ Log.i("tag",it.size.toString()) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         viewh = inflater.inflate(R.layout.fragment_temperature2, container, false)


        setLineChartData()
        return viewh

    }

    fun setLineChartData() {
        val ez = viewh?.findViewById<LineChart>(R.id.linechart1)
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
        lineentry.add(Entry(18f, 1))
        lineentry.add(Entry(22f, 2))
        lineentry.add(Entry(21f, 3))
        lineentry.add(Entry(23f, 4))
        lineentry.add(Entry(27f, 5))
        lineentry.add(Entry(26f, 6))
        lineentry.add(Entry(30f, 7))
        lineentry.add(Entry(32f, 8))


        val linedataset = LineDataSet(lineentry, "FIRST")
        linedataset.color = resources.getColor(R.color.humidity_green_data)

        linedataset.circleRadius = 5f
        linedataset.setCircleColor(R.color.humidity_green_data)
        linedataset.setDrawFilled(true)
        linedataset.fillColor = resources.getColor(R.color.humidity_green_data)
        val data = LineData(xvalue, linedataset)
        linedataset.fillAlpha = 30


        ez?.data = data
        ez?.setBackgroundColor(resources.getColor(R.color.graf_background))
        ez?.animateXY(1000, 1000)


    }

}