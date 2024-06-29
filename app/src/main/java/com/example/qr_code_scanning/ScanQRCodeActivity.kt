package com.example.qr_code_scanning

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
    private var resultReceived = false
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
        startQRCodeScan()
    }
    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }
    private fun startQRCodeScan() {
        resultReceived = false

        // Schedule a task to check if no result has been received after 10 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            if (!resultReceived) {
                Toast.makeText(this, "The QR code was faulty. Please scan again", Toast.LENGTH_SHORT).show()
                scannerView.resumeCameraPreview(this)
            }
        }, 5000)
    }
    override fun handleResult(result: Result?) {
        resultReceived = true

        result?.let {
            val scannerText = it.text
            if(scannerText == null){
                Toast.makeText(this, "No QR Code detected. Please try again.", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "QR Code scanned: ${scannerText}", Toast.LENGTH_SHORT).show()
                if (scannerText.startsWith("http://") || scannerText.startsWith("https://")) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(scannerText))
                    startActivity(browserIntent)
                }
            }
        }
        scannerView.resumeCameraPreview(this)
    }

}