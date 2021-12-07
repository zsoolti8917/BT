package com.example.bt.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "humidity_table")
data class HumidityData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var time: String,
    var humidityData: String
)