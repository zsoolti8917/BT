package com.example.bt.singleton

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import com.example.bt.ControlActivity
import com.example.bt.data.*
import kotlinx.coroutines.*
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

object ConnectionManager {


    var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
    var m_bluetoothSocket: BluetoothSocket? = null
    lateinit var m_progress: ProgressDialog
    lateinit var m_bluetoothAdapter: BluetoothAdapter
    var m_isConnected: Boolean = false
    lateinit var m_adress: String
    lateinit var repository: DataRepository
    private var job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    lateinit var appContext: Context


    init {
        uiScope.launch {
            connectToBT()
        }
    }

    suspend fun connectToBT() {
//if(!appContext.toString().isEmpty()){
        withContext(Dispatchers.IO) {
            var connectSuccess = true
//        withContext(Dispatchers.Main) {
//
//            m_progress =
//                ProgressDialog.show(appContext, "Connecting...", "please wait")
//            Log.i("Progress dialog", "connecting dialog")
//        }
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
                    DataRecieve.passSocket(m_bluetoothSocket!!)
                    DataRecieve.setRecieveString("good")
                }
            } catch (e: IOException) {
                connectSuccess = false
                e.printStackTrace()
                Log.i("CATCH", "TRY CATCH PROBLEM")

            }

            if (!connectSuccess) {
                Log.i("data", "couldnt connect")
            } else {
//            withContext(Dispatchers.Main) {
//
//                m_progress.dismiss()
//                Log.i("dialog dismiss", "dismissing progress dialog")
//
//            }
                m_isConnected = true
                // repository = DataRepository(SensorDataDatabase.getDatabase(appContext).dataDao())
                //   readData()
                Log.i("succesfull connection", "connection established succesfully")
                DataRecieve
            }

            // }
        }

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

    }


    fun sendBTadresstoSingleton(adress: String) {
        m_adress = adress
    }


}