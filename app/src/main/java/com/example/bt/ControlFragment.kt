package com.example.bt

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.bt.data.DataRepository
import com.example.bt.data.SensorDataDatabase
import com.example.bt.data.TemperatureData
import com.example.bt.singleton.DataRecieve
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ControlFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ControlFragment : Fragment() {

    var viewh: View? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository =
            DataRepository(SensorDataDatabase.getDatabase(requireActivity().application).dataDao())
        val tempvw = viewh?.findViewById<TextView>(R.id.temperature_text_mutable)

        repository.readLastTemperature.observe(this, {
            var hm = it.toString()
            showData("$hm")
        })

        repository.readLastBarometerData.observe(this, {
            var hm = it.toString()
            showData("$hm")
        })

        repository.readLastHumidity.observe(this, {
            var hm = it.toString()
            showData("$hm")
        })

        repository.readLastUv.observe(this, {
            var hm = it.toString()
            showData("$hm")
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewh = inflater.inflate(R.layout.fragment_control, container, false)

        return viewh
    }

    fun showData(str: String) {
        val dataTemperatureView = viewh?.findViewById<TextView>(R.id.temperature_text_mutable)
        val dataHumidityView = viewh?.findViewById<TextView>(R.id.humidity_text_mutable)
        val dataUvView = viewh?.findViewById<ProgressBar>(R.id.progressBar)
        dataUvView?.max = 1200
        //dataUvView?.min = 0
        val dataBarometerView = viewh?.findViewById<TextView>(R.id.barometer_text_mutable)

        // dataTempView?.setText(str)
        var help = str.subSequence(0, 1)


        when (help) {
            "<" -> {
                dataTemperatureView?.setText(str.subSequence(1, 6))

            }
            "^" -> {
                dataBarometerView?.setText(str.subSequence(1, 7))
            }
            "?" -> {
                dataHumidityView?.setText(
                    str.subSequence(
                        1, 6
                    )
                )

            }
            "%" -> {
                if (str.isNotBlank()) {
                    var data = (str.subSequence(1, 5).toString().toFloat() * 100).toInt()
                    Log.i("bar", "aval data" + data.toString())

                    Log.i("bar", "aval mas" + data)
                    dataUvView?.setProgress(data, false)
                    Log.i("bar", "set progress")
                }

                // dataUvView?.setProgress(500)
            }
            else -> { // Note the block
                print("x is neither 1 nor 2")
            }
        }
    }
}
