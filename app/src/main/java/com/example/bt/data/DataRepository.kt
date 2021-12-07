package com.example.bt.data

import androidx.lifecycle.LiveData

class DataRepository(private val dataDao: DataDao) {

    val readAllBarometerData: LiveData<List<BarometerData>> = dataDao.readAllBarometerData()

//  val readAllTemperatureData: LiveData<List<TemperatureData>> = dataDao.readAllTemperatureData()
//
//    val readAllHumidityData: LiveData<List<HumidityData>> = dataDao.readAllHumidityData()
//
//    val readAllUvData: LiveData<List<UvData>> = dataDao.readAllUvData()
//
//
//
    suspend fun addBarometerData(barometerData: BarometerData){
        dataDao.addBarometerData(barometerData)
    }
//
//    suspend fun  addTemperatureData(temperatureData: TemperatureData){
//        dataDao.addTemperatureData(temperatureData)
//    }
//
//    suspend fun  addHumidityData(humidityData: HumidityData){
//        dataDao.addHumidityData(humidityData)
//    }
//
//    suspend fun  addUvData(uvData: UvData){
//        dataDao.addUvData(uvData)
//    }
}