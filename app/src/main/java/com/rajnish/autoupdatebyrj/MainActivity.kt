package com.rajnish.autoupdatebyrj

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rajnish.autoinappupdatebyrj.uitls.DialogHelper
import com.rajnish.autoinappupdatebyrj.uitls.PermissionUtils

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if storage permission is granted
        if (!PermissionUtils.hasStoragePermission(this)) {
            PermissionUtils.requestStoragePermission(this)
        } else if (!PermissionUtils.hasInstallPermission(this)) {
            PermissionUtils.requestInstallPermission(this)
        } else {
            // If both permissions are granted, show the update dialog
            showUpdateDialog()
        }
    }




    override fun onResume() {
        super.onResume()
        if (!PermissionUtils.hasStoragePermission(this)) {
            PermissionUtils.requestStoragePermission(this)
        } else if (!PermissionUtils.hasInstallPermission(this)) {
            PermissionUtils.requestInstallPermission(this)
        } else {
            // If both permissions are granted, show the update dialog
            showUpdateDialog()
        }
    }

    // Function to show update dialog
    private fun showUpdateDialog() {
        DialogHelper.showUpdateDialog(this, "https://github.com/RajnishA1/updated-apk-url/raw/refs/heads/main/photo.apk")
    }


}
