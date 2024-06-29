package com.example.qr_code_scanning.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.qr_code_scanning.db.dao.QRResultDao
import com.example.qr_code_scanning.db.entities.QrResult

@Database(entities = [QrResult::class], version = 1, exportSchema = false)
abstract class QrResultDatabase : RoomDatabase(){
    abstract fun getQrDao() : QRResultDao

    companion object {
        @Volatile
        private var INSTANCE: QrResultDatabase? = null

        fun getInstance(context: Context): QrResultDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QrResultDatabase::class.java,
                    "my_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}