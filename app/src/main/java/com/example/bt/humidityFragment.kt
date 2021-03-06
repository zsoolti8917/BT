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


class humidityFragment : Fragment() {

    var fetchFirstData : Boolean = false
    var viewh : View? = null
    private var job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)
    val xvalue = ArrayList<String>()
    val lineentry = ArrayList<Entry>()
 var ez : LineChart? = null
   // var linedataset: LineDataSet? = null

    var data : LineData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var helper = 1

        viewh = inflater.inflate(R.layout.fragment_humidity2, container, false)
        val repository = DataRepository(SensorDataDatabase.getDatabase(requireActivity().application).dataDao())
        repository.readAllHumidityData.observe(viewLifecycleOwner,{

            uiScope.launch {
                withContext(Dispatchers.Main){
                    if(fetchFirstData == false) {
                        it.forEach{ data ->
                            xvalue.add(data.time.toString())

                            lineentry.add(Entry(data.humidityData.subSequence(1,6).toString().toFloat(), helper))
                            helper++
                            Log.i("test", xvalue.size.toString())
                            Log.i("test", lineentry.size.toString())
                        }





                        // populateDataChart(it.get(i).id.toString(), it.get(i).time.toString(), it.get(i).barometerData.subSequence(1,7).toString())

                        fetchFirstData = true
                        setLineChartData()
                    }


//updateData(helper,it.get(it.size).humidityData.toString(), it.get(it.size).time.toString())
                //    helper++
                  //  setLineChartData()
                }

            }

           // xvalue.add(it.last().time)
          //  lineentry.add(Entry(it.last().humidityData.subSequence(1,6).toString().toFloat(),helper))
         //   helper++
//updateData()


        })
        return viewh
    }
fun updateData(help : Int, sensorData : String, sensorTime : String){
    ez = viewh?.findViewById<LineChart>(R.id.linechart2)

    xvalue.add(sensorTime.toString())
    lineentry.add(Entry(sensorData.subSequence(1,6).toString().toFloat(), help))

    val linedataset = LineDataSet(lineentry, "Humidity")
    data = LineData(xvalue, linedataset)
    ez?.data = data
    ez?.notifyDataSetChanged()
    ez?.invalidate()
}
    fun setLineChartData() {
        ez = viewh?.findViewById<LineChart>(R.id.linechart2)
        Log.i("test","problem1")
//        val xvalue = ArrayList<String>()
//        xvalue.add("11:00 AM")
//        xvalue.add("12:00 AM")
//        xvalue.add("1:00 PM")
//        xvalue.add("2:00 PM")
//        xvalue.add("3:00 PM")
//        xvalue.add("4:00 PM")
//        xvalue.add("5:00 PM")
//        xvalue.add("6:00 PM")
//
//
//        val lineentry = ArrayList<Entry>()
//        lineentry.add(Entry(18f, 1))
//        lineentry.add(Entry(22f, 2))
//        lineentry.add(Entry(21f, 3))
//        lineentry.add(Entry(23f, 4))
//        lineentry.add(Entry(27f, 5))
//        lineentry.add(Entry(26f, 6))
//        lineentry.add(Entry(30f, 7))
//        lineentry.add(Entry(32f, 8))


        val linedataset = LineDataSet(lineentry, "Humidity")
        Log.i("test","problem2")
        linedataset.color = resources.getColor(R.color.humidity_green_data)
        Log.i("test","problem3")
        linedataset.circleRadius = 5f
        Log.i("test","problem4")
        linedataset.setCircleColor(R.color.humidity_green_data)
        Log.i("test","problem5")
        linedataset.setDrawFilled(true)
        Log.i("test","problem6")
        linedataset.fillColor = resources.getColor(R.color.humidity_green_data)
        Log.i("test","problem7")
        data = LineData(xvalue, linedataset)
        Log.i("test","problem8")
        linedataset.fillAlpha = 30
        Log.i("test","problem9")

        ez?.data = data
        Log.i("test","problem10")
        ez?.setBackgroundColor(resources.getColor(R.color.graf_background))
        ez?.notifyDataSetChanged()
        ez?.invalidate()
        Log.i("test","problem11")
       // ez?.animateXY(1000, 1000)

        Log.i("test","problem12")
    }
}