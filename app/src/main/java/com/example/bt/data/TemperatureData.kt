package com.example.bt.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "temperature_table")
data class TemperatureData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var time: String,
    var temperatureData: String
) {
}