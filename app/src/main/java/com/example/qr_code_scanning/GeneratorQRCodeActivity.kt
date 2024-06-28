package com.example.qr_code_scanning

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.qr_code_scanning.databinding.ActivityGeneratorQrcodeBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class GeneratorQRCodeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGeneratorQrcodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneratorQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGeneratorQR.setOnClickListener {
            val qrContext = binding.etdata.text.toString().trim()
            if(qrContext.isEmpty() || qrContext.contains(" ")){
                Toast.makeText(this,
                    "Please enter some data to genearator QR Code...",
                    Toast.LENGTH_LONG).show()
            }else{
                try {
                    val barcodeEncoder = BarcodeEncoder()
                    val bitmap : Bitmap = barcodeEncoder.encodeBitmap(qrContext, BarcodeFormat.QR_CODE, 550,550)
                    binding.imgGeneratorQR.setImageBitmap(bitmap)
                    binding.tvGeneratorQR.visibility = View.GONE
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
        }

        binding.btnScanQR.setOnClickListener {
            val intent = Intent(this, ScanQRCodeActivity::class.java)
            startActivity(intent)
        }
    }
}