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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun addHumidityData(dataHum : HumidityData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUvData(dataUV : UvData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun addTemperatureData(dataTemp : TemperatureData)

   @Query("SELECT * FROM barometer_table ORDER BY id DESC LIMIT 100")
    fun readAllBarometerData(): LiveData<List<BarometerData>>

    @Query("SELECT * FROM humidity_table ORDER BY id DESC LIMIT 100")
    fun readAllHumidityData(): LiveData<List<HumidityData>>

    @Query("SELECT * FROM uv_table ORDER BY id DESC LIMIT 100")
    fun readAllUvData(): LiveData<List<UvData>>

    @Query("SELECT * FROM temperature_table ORDER BY id DESC LIMIT 100")
    fun readAllTemperatureData(): LiveData<List<TemperatureData>>



 @Query("SELECT temperatureData FROM temperature_table ORDER BY ID DESC LIMIT 1")
 fun readLastTemperature(): LiveData<String>

 @Query("SELECT humidityData FROM humidity_table ORDER BY ID DESC LIMIT 1")
 fun readLastHumidity(): LiveData<String>

 @Query("SELECT uvData FROM uv_table ORDER BY ID DESC LIMIT 1")
 fun readLastUv(): LiveData<String>

 @Query("SELECT barometerData FROM barometer_table ORDER BY ID DESC LIMIT 1")
 fun readLastBarometerData(): LiveData<String>

// @Query("SELECT temperatureData FROM temperature_table ORDER BY ID DESC LIMIT 1")
// fun readTemperatureData(): LiveData<String>

}