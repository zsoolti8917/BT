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
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bt.data.*
import com.example.bt.singleton.ConnectionManager
import com.example.bt.singleton.DataRecieve
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import java.io.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat


class ControlActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ControlActivity","onCreateStart")
        setContentView(R.layout.control_layout)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.controlFragment, R.id.temperatureFragment, R.id.humidityFragment, R.id.uvFragment, R.id.barometerFragment))
setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

    }


}