package com.example.bt.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BarometerData::class, HumidityData::class, TemperatureData::class, UvData::class], version = 1, exportSchema = false)
abstract class SensorDataDatabase: RoomDatabase() {

    abstract fun dataDao(): DataDao

    companion object{
        @Volatile
        private  var INSTANCE: SensorDataDatabase? = null
        fun getDatabase(context: Context):SensorDataDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SensorDataDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE= instance
                return instance
            }
        }
    }
}