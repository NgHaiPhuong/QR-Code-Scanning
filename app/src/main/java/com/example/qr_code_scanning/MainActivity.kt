 package com.example.qr_code_scanning

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.qr_code_scanning.db.database.QrResultDatabase
import com.example.qr_code_scanning.db.entities.QrResult
import java.util.Calendar

 @Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            //cung cấp các tham số cấu hình cho các cửa sổ trong ứng dụng của bạn.
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        val qrResult = QrResult(0, "NgHaiPhuong", "Text", false, Calendar.getInstance())
        QrResultDatabase.getInstance(this).getQrDao().insertQrResult(qrResult)

        Handler().postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}