package com.example.bt.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "barometer_table")
@JvmSuppressWildcards
data class BarometerData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var time: String,
    var barometerData: String
) {
}