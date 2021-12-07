package com.example.bt.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "uv_table")
data class UvData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var time: String,
    var uvData: String
) {
}