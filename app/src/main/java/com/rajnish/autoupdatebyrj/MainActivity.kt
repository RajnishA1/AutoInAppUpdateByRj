package com.rajnish.autoupdatebyrj

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.rajnish.autoinappupdatebyrj.uitls.PermissionManager

class MainActivity : AppCompatActivity() {

    private lateinit var storagePermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var installPermissionLauncher: ActivityResultLauncher<Intent>
    private lateinit var manageAllFilesPermissionLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerPermissionLaunchers()

        permissionManager = PermissionManager(
            context = this,
            activity = this,
            apkUrl = "https://github.com/RajnishA1/updated-apk-url/raw/refs/heads/main/photo.apk",
            storagePermissionLauncher = storagePermissionLauncher,
            installPermissionLauncher = installPermissionLauncher,
            manageAllFilesPermissionLauncher = manageAllFilesPermissionLauncher
        )

        // Check and request permissions
        permissionManager.checkAndRequestPermissions()
    }

    /**
     * Register activity result launchers for permissions.
     */
    private fun registerPermissionLaunchers() {
        storagePermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val granted = permissions.values.all { it }
            if (granted) {
                permissionManager.checkAndRequestPermissions()
            } else {
                // Optional: Inform user about why the permission is needed
            }
        }

        installPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            permissionManager.checkAndRequestPermissions()
        }

        manageAllFilesPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            permissionManager.checkAndRequestPermissions()
        }
    }

}
