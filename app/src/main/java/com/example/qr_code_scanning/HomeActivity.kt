package com.example.qr_code_scanning

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.qr_code_scanning.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScanQR.setOnClickListener {
            checkForPermission()
        }

        binding.btnGeneratorQR.setOnClickListener {
            val intent = Intent(this,  GeneratorQRCodeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkForPermission() {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            goToScanQRActivity()
        }
        else{
            requestThePermissions()
        }
    }
    private fun requestThePermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                goToScanQRActivity()
            }
            else if(isUserPermanentlyDenied()){
                showGoToAppSettingDialog()
            }
            else{
                requestThePermissions()
            }
        }
    }
    private fun showGoToAppSettingDialog() {
        AlertDialog.Builder(this)
            .setTitle("Grant Permission")
            .setMessage("We need camera permission to scan QR code. Go to App Setting and manage permission.")
            .setPositiveButton("Grant"){dialog, which ->
                goToAppSetting()
            }
            .setNegativeButton("Cancel"){dialog, which->
                Toast.makeText(this, "We need permission for Start this application", Toast.LENGTH_LONG).show()
            }.show()
    }
    private fun goToAppSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    private fun isUserPermanentlyDenied():  Boolean{
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA).not()
        }else{
            return false
        }
    }
    private fun goToScanQRActivity() {
        val intent =  Intent(this, ScanQRCodeActivity::class.java)
        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        checkForPermission()
    }
    companion object{
        private const val CAMERA_PERMISSION_REQUEST_CODE = 123
    }
}