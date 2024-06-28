package com.example.qr_code_scanning

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.qr_code_scanning.databinding.ActivityScanQrcodeBinding
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanQRCodeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private lateinit var binding : ActivityScanQrcodeBinding
    private lateinit var scannerView : ZXingScannerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeQrScanner()
        onClicks()
    }
    private fun onClicks() {
        binding.flashToggle.setOnClickListener {
            if(it.isSelected){
                offFlashLight()
            }
            else{
                onFlashLight()
            }
        }
    }
    private fun offFlashLight() {
        binding.flashToggle.isSelected = false
        scannerView.flash = false
    }
    private fun onFlashLight() {
        binding.flashToggle.isSelected = true
        scannerView.flash = true
    }
    private fun initializeQrScanner() {
        scannerView = ZXingScannerView(this)
        scannerView.apply {
            setBackgroundColor(ContextCompat.getColor(this@ScanQRCodeActivity, R.color.colorTranslucentBlack))
            setBorderColor(ContextCompat.getColor(this@ScanQRCodeActivity, R.color.black_shade_1))
            setLaserColor(ContextCompat.getColor(this@ScanQRCodeActivity, R.color.purple_200))
            setBorderCornerRadius(5)
            setBorderStrokeWidth(10) // thiết lập độ dày
            setAutoFocus(true)
            setSquareViewFinder(true)  // thiết lập vùng quét thành hình vuông
            setResultHandler(this@ScanQRCodeActivity)
            binding.containerScanner.addView(scannerView)
            startQrCamera()
        }
    }
    private fun startQrCamera(){
        scannerView.startCamera()
    }
    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        scannerView.startCamera();
    }
    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }
    override fun handleResult(result: Result?) {
        result?.let {
            Toast.makeText(this, "QR Code scanned: ${it.text}", Toast.LENGTH_SHORT).show()
        }
        scannerView.resumeCameraPreview(this)
    }
}