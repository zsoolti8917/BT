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
import kotlinx.coroutines.*


class temperatureFragment : Fragment() {
    var fetchFirstData : Boolean = false
    var viewh : View? = null
    private var job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)
    val xvalue = ArrayList<String>()
    val lineentry = ArrayList<Entry>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewh = inflater.inflate(R.layout.fragment_temperature2, container, false)

        val repository = DataRepository(SensorDataDatabase.getDatabase(requireActivity().application).dataDao())
        repository.readAllTemperatureData.observe(viewLifecycleOwner,{
            var helper = 1
            uiScope.launch {
                withContext(Dispatchers.Main){
                    if(fetchFirstData == false) {
                        it.forEach{ data ->
                            xvalue.add(data.time.toString())

                            lineentry.add(Entry(data.temperatureData.subSequence(1,6).toString().toFloat(), helper))
                            helper++
                            Log.i("test", xvalue.size.toString())
                            Log.i("test", lineentry.size.toString())
                        }





                        // populateDataChart(it.get(i).id.toString(), it.get(i).time.toString(), it.get(i).barometerData.subSequence(1,7).toString())

                        fetchFirstData = true
                        setLineChartData()
                    }



                }

            }





        })
        return viewh

    }

    fun setLineChartData() {
        val ez = viewh?.findViewById<LineChart>(R.id.linechart1)



        val linedataset = LineDataSet(lineentry, "Temperature")
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