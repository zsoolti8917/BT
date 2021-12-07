package com.example.bt

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception
import java.nio.charset.Charset
import java.util.*
import android.system.Os.socket
import android.system.Os.socket
import android.content.Intent
import android.os.Looper
import android.text.TextUtils
import androidx.lifecycle.LiveData
import com.example.bt.data.*
import kotlinx.coroutines.*
import java.io.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat


class ControlActivity : AppCompatActivity() {

    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        lateinit var m_adress: String
        lateinit var graf1: String
        lateinit var graf2: String
        lateinit var graf3: String

        lateinit var temperature: TextView
        lateinit var humidity: TextView
        lateinit var barometer: TextView
        lateinit var uv: TextView

    }

    private val repository: DataRepository



    init {
        val dataDao = SensorDataDatabase.getDatabase(this).dataDao()
        repository = DataRepository(dataDao)
    }

    private var job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.control_layout)
        m_adress = intent.getStringExtra(MainActivity.EXTRA_ADRESS).toString()

        //ConnectToDevice(this).execute()

        uiScope.launch {
            connect()
        }

        findViewById<Button>(R.id.control_led_disconnect).setOnClickListener(View.OnClickListener { disconnect() })
        temperature = findViewById<TextView>(R.id.temperature_text_mutable)
        humidity = findViewById<TextView>(R.id.humidity_text_mutable)
        barometer = findViewById<TextView>(R.id.barometer_text_mutable)
        uv = findViewById<TextView>(R.id.uv_text_mutable)
        findViewById<Button>(R.id.temperature_humidity).setOnClickListener(View.OnClickListener { temperatureActivity() })
        findViewById<Button>(R.id.barometer).setOnClickListener(View.OnClickListener { barometerActivity() })
        findViewById<Button>(R.id.uv).setOnClickListener(View.OnClickListener { uvActivity() })

//        val temperatureFile = "temperatureText.txt"
//        Log.i("problem", "temperature file open")
//        val myfile = File(temperatureFile)
//        Log.i("problem", "create an instance of temp file")
//
//        myfile.printWriter().use { out ->
//
//            out.println("line.toString().subSequence(1,6)")
//            Log.i("problem", "TEMP WRITTEN TO TXT!")
//        }

      //  Log.i("helper",getFilesDir().toString())
    }

    suspend fun connect() {

        withContext(Dispatchers.IO) {
            var connectSuccess = true

            withContext(Dispatchers.Main) {

                m_progress =
                    ProgressDialog.show(this@ControlActivity, "Connecting...", "please wait")
                Log.i("Progress dialog", "connecting dialog")
            }


            try {
                Log.i("trying", "try catch first line")
                if (m_bluetoothSocket == null || !m_isConnected) {
                    Log.i("checking bt socket", "m_bluetoothSocket == null || !m_isConnected")
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    Log.i("mbtadapter", "getting an instance of BT adapter")

                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_adress)
                    Log.i("deviceslist", "cycling throu device lists $device")

                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    Log.i("Our Socket", "creating socket for our device")

                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    Log.i("getting adapter", "getting adapter and canceling discovery")

                    m_bluetoothSocket!!.connect()
                    Log.i("CONNECTION", "Trying to connect to our device")


                }
            } catch (e: IOException) {
                connectSuccess = false
                e.printStackTrace()
                Log.i("CATCH", "TRY CATCH PROBLEM")

            }
//            launch(UI) {
//                val images = getImages(galleryPath)
//                imageListAdapter.setItems(images)
//            }

            if (!connectSuccess) {
                Log.i("data", "couldnt connect")
            } else {
                withContext(Dispatchers.Main) {

                    m_progress.dismiss()
                    Log.i("dialog dismiss", "dismissing progress dialog")

                }
                m_isConnected = true
                readData()

//temperature.text = "Updated from other Thread"
                Log.i("succesfull connection", "connection established succesfully")

            }

        }
    }

    suspend fun readData() {
        Log.i("problem", "problem1")
        try {
//var i = 0;
//            val temperatureFile = "temperatureText.txt"
//            Log.i("problem", "temperature file open")
//            val myfile = File(temperatureFile)
//            Log.i("problem", "create an instance of temp file")

            val socketInputStream: InputStream = m_bluetoothSocket!!.inputStream
            Log.i("problem", "dismissing progress dialog1")
            val read = InputStreamReader(socketInputStream)
            Log.i("problem", "dismissing progress dialog2")

            Log.i( "problem", "dismissing progress dialog3")
            val scan = Scanner(socketInputStream)
            Log.i("problem", "dismissing progress dialog4")

            Log.i("problem", "dismissing progress dialog5")
            while (true) {
                try {
                    while (scan.hasNextLine()) {
                        Log.i("problem", "dismissing progress dialog8")
                        var line = scan.nextLine()
                        var help = line.subSequence(0, 1)
                        Log.i("problem", "dismissing progress dialog8")

                        when (help) {
                            "<" -> this@ControlActivity.runOnUiThread(java.lang.Runnable {
                                if (!line.toString().isBlank()) {
                                    temperature.setText(line.toString().subSequence(1, 6))

                                    insertDatatoDatabase(line.toString())
                                    Log.i("problem", "settext!")
                                    Log.i("problem", "text = ${line.toString()}")

//
//                                    File(applicationContext.filesDir, "test.txt").printWriter().use { out ->
//                                        out.println("${i}, ${line.toString()}")
//                                       Log.i("problem", "TEMP WRITTEN TO TXT!")
//                                    }

                                }
                            })
                            "^" -> this@ControlActivity.runOnUiThread(java.lang.Runnable {
                                if (!line.toString().isBlank()) {
                                    barometer.setText(line.toString().subSequence(1, 8))
                                    insertDatatoDatabase(line.toString())
                                    Log.i("problem", "settext!")
                                    Log.i("problem", "text = ${line.toString()}")
                                }
                            })
                            "?" -> this@ControlActivity.runOnUiThread(java.lang.Runnable {
                                if (!line.toString().isBlank()) {
                                    humidity.setText(line.toString().subSequence(1, 6))
                                    insertDatatoDatabase(line.toString())
                                    Log.i("problem", "settext!")
                                    Log.i("problem", "text = ${line.toString()}")
                                }
                            })
                            "%" -> this@ControlActivity.runOnUiThread(java.lang.Runnable {
                                if (!line.toString().isBlank()) {
                                    uv.setText(line.toString().subSequence(1, 5))
                                    insertDatatoDatabase(line.toString())
                                    Log.i("problem", "settext!")
                                    Log.i("problem", "text = ${line.toString()}")
                                }
                            })
                            else -> { // Note the block
                                print("x is neither 1 nor 2")
                            }
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.i("problem", "dismissing progress dialog9")
                    break
                }
            }
        } catch (e: IOException) {
            Log.i("problem", "dismissing progress dialog10")
            e.printStackTrace()
        }
//        fun WriteToFile(str : String){
//            try {
//                var fo = FileWriter("TemperatureFile",true)
//                Log.i("problem","OPEN FILEWRITER")
//                fo.write(str+"\n")
//                Log.i("problem","WRITE DATA")
//            }catch (ex:Exception){
//                Log.i("problem","WRITE TO FILE CATCH PROBLEM")
//            }
//        }
    }
    fun temperatureActivity() {
        val myIntent: Intent = Intent(this@ControlActivity, TemperatureActivity::class.java)
        myIntent.putExtra(MainActivity.EXTRA_ADRESS_GRAF1, m_adress)
        this@ControlActivity.startActivity(myIntent)
    }
    fun barometerActivity() {
        val myIntent: Intent = Intent(this@ControlActivity, BarometerActivity::class.java)
        myIntent.putExtra(MainActivity.EXTRA_ADRESS_GRAF2, m_adress)
        this@ControlActivity.startActivity(myIntent)
    }
    fun uvActivity() {
        val myIntent: Intent = Intent(this@ControlActivity, UVActivity::class.java)
        myIntent.putExtra(MainActivity.EXTRA_ADRESS_GRAF3, m_adress)
        this@ControlActivity.startActivity(myIntent)
    }
    private fun sendCommand(input: String) {
        if (m_bluetoothSocket != null)
            try {
                m_bluetoothSocket!!.outputStream.write(input.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
    }
    fun InputStream.readTextAndClose(charset: Charset = Charsets.UTF_8): String {
        return this.bufferedReader(charset).use { it.readText() }
    }
    private fun disconnect() {
        if (m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.close()
                m_bluetoothSocket = null
                m_isConnected = false
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        finish()
    }

    private fun insertDatatoDatabase(dataType : String){
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        var data = dataType
        var help = data.subSequence(0, 1)

        

        if(inputCheck(currentDate,data)){

            when (help) {
//                "<" -> {
//                    val temperatureDatas = TemperatureData(0,currentDate.toString(),data.toString())
//                    Log.i("problem1", "temperature data added to database")
//                    addTemperatureData(temperatureDatas)
//                }


                        
                "^" -> {
                    val barometerDatas = BarometerData(0,currentDate.toString(),data.toString())
                    Log.i("problem1", "barometer data added to database")
                    addBarometerDatat(barometerDatas)
                }


//                "?" -> {
//                    val humidityDatas = HumidityData(0,currentDate.toString(),data.toString())
//                    Log.i("problem1", "humidity  data added to database")
//                    addHumidityData(humidityDatas)
//                }


//                "%" -> {
//                    val uvDatas = UvData(0,currentDate.toString(),data.toString())
//                    Log.i("problem1", " uv data added to database")
//                    addUvData(uvDatas)
//                }


                else -> { // Note the block
                    print("x is neither 1 nor 2")
                }
            }


        }
    }

//    fun addTemperatureData(temperatureData: TemperatureData){
//        uiScope.launch(Dispatchers.IO) {
//            Log.i("problem1", "addTempData")
//            repository.addTemperatureData(temperatureData)
//        }
//    }

//    fun addHumidityData(humidityData: HumidityData){
//        uiScope.launch(Dispatchers.IO) {
//            Log.i("problem1", "daddHumData")
//            repository.addHumidityData(humidityData)
//        }
//    }

    fun addBarometerDatat(barometerData: BarometerData){
        uiScope.launch(Dispatchers.IO) {
            Log.i("problem1", "add baro data")
            repository.addBarometerData(barometerData)
        }
    }

//    fun addUvData(uvData: UvData){
//        uiScope.launch(Dispatchers.IO) {
//            Log.i("problem1", "add uv data")
//            repository.addUvData(uvData)
//        }
//    }
    private fun inputCheck(date: String, sensorData: String): Boolean{
        return !(TextUtils.isEmpty(date.toString()) && TextUtils.isEmpty(sensorData.toString()))
    }

    public class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {
        private var connectSuccess: Boolean = true
        private val context: Context
        init {
            this.context = c
        }
        override fun onPreExecute() {
            super.onPreExecute()
            m_progress = ProgressDialog.show(context, "Connecting...", "please wait")
        }

        override fun doInBackground(vararg p0: Void?): String {
            try {
                if (m_bluetoothSocket == null || !m_isConnected) {
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_adress)
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect()

                }
            } catch (e: IOException) {
                connectSuccess = false
                e.printStackTrace()
            }
            return null.toString()
        }

        public fun readData() {
            val t: Thread = object : Thread() {
                override fun run() {
                    try {

                        val socketInputStream: InputStream = m_bluetoothSocket!!.inputStream
                        val read = InputStreamReader(socketInputStream)
                        var data = read.read()
                        var scan = Scanner(socketInputStream)
                        while (true) {
                            try {
                                while (scan.hasNextLine()) {
                                    var line = scan.nextLine()
                                    println(line)
                                    temperature.setText(line.toString())
                                    //ez az egyetlen ami eddig mukodik
                                    // runOnUiThread { temperature.setText(line.toString()) }
                                }
                            } catch (e: IOException) {
                                e.printStackTrace()
                                break

                            }
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()

                    }
                }
            }
            t.start()

            //Loop to listen for received bluetooth messages

        }

        fun convertStreamToString(`is`: InputStream): String? {
            val reader = BufferedReader(InputStreamReader(`is`))
            val sb = StringBuilder()
            var line: String? = null
            try {
                while (reader.readLine().also { line = it } != null) {
                    sb.append(
                        """
                    $line
                    
                    """.trimIndent()
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    `is`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return sb.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            if (!connectSuccess) {
                Log.i("data", "couldnt connect")
            } else {
                m_isConnected = true
                readData()

            }
            m_progress.dismiss()

        }

    }
}