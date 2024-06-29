package com.example.qr_code_scanning.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.qr_code_scanning.db.entities.QrResult

@Dao
interface QRResultDao {
    @Query("SELECT * FROM QrResult_table ORDER BY time DESC")
    fun getAllScannedResults() : List<QrResult>
    @Query("SELECT * FROM QRRESULT_TABLE WHERE favourite = 1")
    fun getAllFavoriteResults() : List<QrResult>
    @Query("DELETE FROM QrResult_table")
    fun deleteAllScannedResults()
    @Query("DELETE FROM QrResult_table WHERE favourite = 1")
    fun deleteAllFavouriteResults()
    @Insert
    fun insertQrResult(qrResult : QrResult) : Long
    @Query("SELECT * FROM QrResult_table WHERE id = :id")
    fun getQrResult(id : Int) : QrResult
    @Query("UPDATE QrResult_table SET favourite = 1 WHERE id = :id")
    fun addToFavorite(id: Int)
    @Query("UPDATE QrResult_table SET favourite = 0 WHERE id = :id")
    fun removeFromFavorite(id : Int)
}





