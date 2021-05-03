package com.example.cardiaryclient.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.cardiaryclient.R
import com.example.cardiaryclient.databinding.ActivityImageUploadBinding
import com.example.cardiaryclient.databinding.ActivityMainBinding
import com.example.cardiaryclient.network.utils.UploadUtility

class ImageUploadActivity : AppCompatActivity() {

    val LOG_TAG = "MainActivity"

    private lateinit var binding: ActivityImageUploadBinding

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            ivPicture.setOnClickListener {
                openImageChooser()
            }

            btnUpload.setOnClickListener {
                selectedImageUri?.let { imgUri ->
                    if(askForPermissions()) {
                        // Upload file to server here
                        UploadUtility(this@ImageUploadActivity).uploadFile(imgUri)
                    }
                }
            }
        }

    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    Glide
                        .with(this)
                        .load(selectedImageUri)
                        .into(binding.ivPicture);
//                    binding.ivPicture.setImageURI(selectedImageUri)
                }
            }
        }
    }

    // Permissions processing functions
    private fun isPermissionsAllowed(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun askForPermissions(): Boolean {
        if (!isPermissionsAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this as Activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(this as Activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSIONS_CODE)
            }
            return false
        }
        return true
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton("App Settings"
            ) { _, _ ->
                // send to app settings if permission is denied permanently
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel",null)
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSIONS_CODE -> {
                // permission is denied, you can ask for permission again, if you want
                //  askForPermissions()
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Perform your operation here
                } else Toast.makeText(this, "Read files permission is denied!", Toast.LENGTH_LONG).show()
                return
            }
        }
    }

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 1
        const val REQUEST_PERMISSIONS_CODE = 2
    }
}