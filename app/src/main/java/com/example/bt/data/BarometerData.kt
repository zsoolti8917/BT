package com.example.bt.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "barometer_table")
data class BarometerData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var time: String,
    var barometerData: String
)