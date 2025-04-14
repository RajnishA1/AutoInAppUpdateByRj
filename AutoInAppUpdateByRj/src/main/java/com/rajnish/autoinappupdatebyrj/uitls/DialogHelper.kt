package com.rajnish.autoinappupdatebyrj.uitls

import android.content.Context
import android.view.Gravity
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog

object DialogHelper {

    fun showUpdateDialog(context: Context, downloadLink: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Update Available")
        builder.setMessage("A new version of the app is available. Do you want to update?")
        builder.setCancelable(false)

        builder.setPositiveButton("Yes") { _, _ ->
            // Start downloading the update after the user clicks "Yes"
            showDownloadingProgress(context, downloadLink)
        }

        builder.show()
    }

    // Method to show download progress and download the update
    private fun showDownloadingProgress(context: Context, downloadLink: String) {
        val progressDialog = AlertDialog.Builder(context).apply {
            setTitle("Downloading Update")
            setMessage("Downloading, please wait...")
            setCancelable(false)
        }.create()

        // Create a LinearLayout to hold the ProgressBar and TextView
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(30, 30, 30, 30)

        // Create the ProgressBar and set it to horizontal style
        val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }

        // Create the TextView to show the progress percentage
        val progressText = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            gravity = Gravity.CENTER
            text = "0%"  // Default text
        }

        // Add the ProgressBar and TextView to the layout
        layout.addView(progressBar)
        layout.addView(progressText)

        // Set the custom view to the AlertDialog
        progressDialog.setView(layout)

        progressDialog.show()

        // Start the download process
        UpdateHelper.startDownload(context, downloadLink, progressBar, progressText)
    }
}
