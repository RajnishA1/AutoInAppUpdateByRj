package com.rajnish.autoinappupdatebyrj.uitls

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rajnish.autoinappupdatebyrj.R

object DialogHelper {

    fun showUpdateDialog(context: Context, downloadLink: String) {
        MaterialAlertDialogBuilder(context)
            .setTitle("🚀 Update Available")
            .setMessage("A new version of the app is available. Do you want to update?")
            .setCancelable(false)
            .setPositiveButton("Update") { _, _ ->
                showDownloadingProgress(context, downloadLink)
            }
            .show()
    }

    private fun showDownloadingProgress(context: Context, downloadLink: String) {
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(50, 40, 50, 40)
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            // Optional: Add rounded background
            background = GradientDrawable().apply {
                cornerRadius = 32f
                setColor(Color.WHITE)
            }
        }

        val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal).apply {
            isIndeterminate = false
            max = 100
            progress = 0
            progressDrawable = ContextCompat.getDrawable(context, R.drawable.custom_progress_drawable)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                40
            ).apply {
                topMargin = 20
            }
        }

        val progressText = TextView(context).apply {
            text = "0%"
            gravity = Gravity.CENTER
            textSize = 16f
            setTextColor(Color.BLACK)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 12
            }
        }

        layout.addView(progressBar)
        layout.addView(progressText)

        val dialog = AlertDialog.Builder(context)
            .setTitle("📦 Downloading Update")
            .setView(layout)
            .setCancelable(false)
            .create()

        dialog.show()

        // Start download
        UpdateHelper.startDownload(context, downloadLink, progressBar, progressText){
            dialog.dismiss()
        }
    }

    /**
     * Show an explanation dialog before requesting permissions.
     */
    fun showPermissionExplanationDialog(context: Context, onProceed: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle("Permission Required")
            .setMessage("This app requires certain permissions to function properly, including access to storage and the ability to install updates from unknown sources.")
            .setPositiveButton("OK") { _, _ ->
                onProceed()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
