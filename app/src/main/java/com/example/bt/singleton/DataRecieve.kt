package com.example.bt.singleton

import android.app.Application
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.example.bt.ControlActivity
import com.example.bt.data.*
import kotlinx.coroutines.*
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

object DataRecieve {

    lateinit var repository: DataRepository
    private var job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var application: Context
    private lateinit var socketB: BluetoothSocket
var helper : String = ""

    fun init(context: Context) {

        this.application = context
        Log.i("problem2", "connection established succesfully1")
        repository = DataRepository(SensorDataDatabase.getDatabase(application).dataDao())

        uiScope.launch {
            //     Log.i("asd", ConnectionManager.m_bluetoothAdapter.state.toString())

checkIfCanRun()
//readData()


        }

    }
    suspend fun checkIfCanRun(){
        withContext(Dispatchers.IO){
            while(true){
                if(helper.equals("good")){
                    Log.i("problem2", "connection established succesfully2")
                    readData()
                }
            }

        }
    }

    fun passSocket(socket : BluetoothSocket){
        Log.i("problem2", "connection established succesfully3")
        socketB = socket
    }

    fun setRecieveString(str : String){

        helper = str
        Log.i("problem2", "recieve string $helper")
    }

        suspend fun readData() {
            withContext(Dispatchers.IO) {
                Log.i("problem2", "problem1")
                try {
                    Log.i("problem2", "connection established succesfully")
                    val socketInputStream: InputStream =
                        ConnectionManager.m_bluetoothSocket!!.inputStream
                    Log.i("problem2", "dismissing progress dialog1")
                    val read = InputStreamReader(socketInputStream)
                    Log.i("problem2", "dismissing progress dialog2")

                    Log.i("problem2", "dismissing progress dialog3")
                    val scan = Scanner(socketInputStream)
                    Log.i("problem2", "dismissing progress dialog4")

                    Log.i("problem2", "dismissing progress dialog5")
                    while (true) {
                        try {
                            while (scan.hasNextLine()) {

                                var line = scan.nextLine()

                                var help = line.subSequence(0, 1)


                                when (help) {
                                    "<" -> {
                                        if (!line.toString().isBlank()) {
                                            insertDatatoDatabase(line.toString())
                                            //ControlActivity.temperature.setText(line.toString().subSequence(1, 8))
                                            Log.i("problem2", "Temperature data recieved $line")
                                        }
                                    }
                                    "^" -> {
                                        if (!line.toString().isBlank()) {
                                            insertDatatoDatabase(line.toString())
                                            //  ControlActivity.barometer.setText(line.toString().subSequence(1, 8))
                                            Log.i("problem2", "Baro Data Recieved $line")
                                        }
                                    }
                                    "?" -> {
                                        if (!line.toString().isBlank()) {
                                            insertDatatoDatabase(line.toString())
                                            //   ControlActivity.humidity.setText(line.toString().subSequence(1, 8))
                                            Log.i("problem2", "Humidity Data Recieved $line")
                                        }
                                    }
                                    "%" -> {
                                        if (!line.toString().isBlank()) {
                                            insertDatatoDatabase(line.toString())
                                            //   ControlActivity.uv.setText(line.toString().subSequence(1, 8))
                                            Log.i("problem2", " Uv data recieved $line")
                                        }
                                    }
                                    else -> { // Note the block
                                        print("x is neither 1 nor 2")
                                    }
                                }
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.i("problem2", "dismissing progress dialog9")
                            break
                        }
                    }
                } catch (e: IOException) {
                    Log.i("problem2", "dismissing progress dialog10")
                    e.printStackTrace()
                }
            }

        }


        private fun insertDatatoDatabase(dataType: String) {
            Log.i("problem2", "insertDatabase fun opened")
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            var data = dataType
            var help = data.subSequence(0, 1)
            if (inputCheck(currentDate, data)) {

                when (help) {
                    "<" -> {
                        val temperatureDatas = TemperatureData(0, currentDate.toString(), data.toString())
                        Log.i("problem2", "temperature data added to database")
                        addTemperatureData(temperatureDatas)
                    }


                    "^" -> {
                        val barometerDatas = BarometerData(0, currentDate.toString(), data.toString())
                        Log.i("problem2", "barometer data added to database")
                        addBarometerDatat(barometerDatas)
                    }


                    "?" -> {
                        val humidityDatas = HumidityData(0, currentDate.toString(), data.toString())
                        Log.i("problem2", "humidity  data added to database")
                        addHumidityData(humidityDatas)
                    }


                    "%" -> {
                        val uvDatas = UvData(0, currentDate.toString(), data.toString())
                        Log.i("problem2", " uv data added to database")
                        addUvData(uvDatas)
                    }


                    else -> { // Note the block
                        print("x is neither 1 nor 2")
                    }
                }


            }
        }

        fun addTemperatureData(temperatureData: TemperatureData) {
            uiScope.launch(Dispatchers.IO) {
                Log.i("problem1", "addTempData")
                repository.addTemperatureData(temperatureData)
            }
        }

        fun addHumidityData(humidityData: HumidityData) {
            uiScope.launch(Dispatchers.IO) {
                Log.i("problem1", "daddHumData")
                repository.addHumidityData(humidityData)
            }
        }

        fun addBarometerDatat(barometerData: BarometerData) {
            uiScope.launch {
                withContext(Dispatchers.IO) {
                    Log.i("problem1", "add baro data")
                    repository.addBarometerData(barometerData)

                }
            }
        }

        fun addUvData(uvData: UvData) {
            uiScope.launch(Dispatchers.IO) {
                Log.i("problem1", "add uv data")
                repository.addUvData(uvData)
            }
        }

        private fun inputCheck(date: String, sensorData: String): Boolean {
            return !(TextUtils.isEmpty(date.toString()) && TextUtils.isEmpty(sensorData.toString()))
        }


}