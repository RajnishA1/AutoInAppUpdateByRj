package com.rajnish.autoinappupdatebyrj.uitls

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.Manifest
import android.content.Intent
import android.os.Environment
import android.provider.Settings
import androidx.core.net.toUri

object PermissionUtils {

    // Check for storage permission
    fun hasStoragePermission(activity: Activity): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val read = activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            val write = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            read == PackageManager.PERMISSION_GRANTED && write == PackageManager.PERMISSION_GRANTED
        }
    }

    // Request storage permission
    fun requestStoragePermission(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                data = "package:${activity.packageName}".toUri()
            }
            activity.startActivity(intent) // OR launch via ActivityResultLauncher
        } else {
            activity.requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 101
            )
        }
    }

    // Check for install unknown apps permission (needed for APK installation)
    fun hasInstallPermission(activity: Activity): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.packageManager.canRequestPackageInstalls()
        } else {
            true // No need to request on versions before Android 8.0
        }
    }

    // Request permission for installing from unknown sources (Android 8.0 and above)
    fun requestInstallPermission(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
            activity.startActivityForResult(intent, 102)
        }
    }
}
