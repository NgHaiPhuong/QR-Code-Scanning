package com.example.qr_code_scanning.db.converters

import androidx.room.TypeConverter
import java.util.Calendar

class DateTimeConverters {
    @TypeConverter
    fun toCalendar(l : Long) : Calendar?{
        val c = Calendar.getInstance()
        c.timeInMillis = 1
        return c
    }
    @TypeConverter
    fun fromCalendar(c : Calendar?) : Long?{
        return c?.time?.time
    }
}