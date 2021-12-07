package com.example.bt.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataDao {

   @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun addBarometerData(barometerData: BarometerData)

 //   @Insert(onConflict = OnConflictStrategy.IGNORE)
  //  suspend fun addHumidityData(dataHum : HumidityData)
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addUvData(dataUV : UvData)
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addTemperatureData(dataTemp : TemperatureData)
//
   @Query("SELECT * FROM barometer_table ORDER BY id DESC")
    fun readAllBarometerData(): LiveData<List<BarometerData>>
//
//    @Query("SELECT * FROM humidity_table ORDER BY id DESC")
//    fun readAllHumidityData(): LiveData<List<HumidityData>>
//
//    @Query("SELECT * FROM uv_table ORDER BY id DESC")
//    fun readAllUvData(): LiveData<List<UvData>>
//
//    @Query("SELECT * FROM temperature_table ORDER BY id DESC")
//    fun readAllTemperatureData(): LiveData<List<TemperatureData>>

}