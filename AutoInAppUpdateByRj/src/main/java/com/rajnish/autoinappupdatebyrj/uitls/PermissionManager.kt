package com.rajnish.autoinappupdatebyrj.uitls



import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.net.toUri

class PermissionManager(
    private val context: Context,
    private val activity: Activity,
    private val apkUrl: String,
    private val storagePermissionLauncher: ActivityResultLauncher<Array<String>>,
    private val installPermissionLauncher: ActivityResultLauncher<Intent>,
    private val manageAllFilesPermissionLauncher: ActivityResultLauncher<Intent>
) {

    // This function will check and handle the permissions for storage and installation
    fun checkAndRequestPermissions() {
        if (!PermissionUtils.hasStoragePermission(activity)) {
            requestStoragePermission()
        } else {
            checkInstallPermission()
        }
    }

    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                data = "package:${context.packageName}".toUri()
            }
            manageAllFilesPermissionLauncher.launch(intent)
        } else {
            val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
            storagePermissionLauncher.launch(permissions)
        }
    }

    private fun checkInstallPermission() {
        if (!PermissionUtils.hasInstallPermission(activity)) {
            val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).apply {
                data = "package:${context.packageName}".toUri()
            }
            installPermissionLauncher.launch(intent)
        } else {
            showUpdateDialog()
        }
    }

    private fun showUpdateDialog() {
        DialogHelper.showUpdateDialog(
            context,
            apkUrl
        )
    }
}
