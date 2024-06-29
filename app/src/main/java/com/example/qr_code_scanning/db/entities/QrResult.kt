package com.example.qr_code_scanning.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import androidx.room.TypeConverters
import com.example.qr_code_scanning.db.converters.DateTimeConverters
import java.util.Calendar

@Entity(tableName = "QrResult_table")
@TypeConverters(DateTimeConverters::class)
data class QrResult (
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    @ColumnInfo(name = "result")
    val result : String?,
    @ColumnInfo(name = "result_type")
    val resultType : String?,
    @ColumnInfo(name = "favourite")
    val favourite : Boolean = false,
    @ColumnInfo(name = "time")
    val calendar: Calendar
)