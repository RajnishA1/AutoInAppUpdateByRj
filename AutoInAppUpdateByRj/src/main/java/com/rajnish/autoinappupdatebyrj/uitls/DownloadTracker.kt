package com.rajnish.autoinappupdatebyrj.uitls

import android.app.DownloadManager
import android.content.Context
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

object DownloadTracker {

    fun trackDownloadProgress(
        context: Context,
        progressBar: ProgressBar?,
        progressText: TextView?,
        fileName: String,
        onDownloadComplete: () -> Unit
    ) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        Thread {
            var isRunning = true

            while (isRunning) {
                val query = DownloadManager.Query().setFilterById(UpdateHelper.downloadId)
                val cursor = downloadManager.query(query)

                if (cursor != null && cursor.moveToFirst()) {
                    val status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
                    val downloaded = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                    val total = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        (context as? AppCompatActivity)?.runOnUiThread {
                            progressBar?.progress = 100
                            progressText?.text = "Completed"
                            progressBar?.isVisible = false
                            onDownloadComplete()
                            ApkInstaller.installApk(context,fileName)
                        }
                        isRunning = false
                    } else if (status == DownloadManager.STATUS_FAILED) {
                        isRunning = false
                    } else if (total > 0) {
                        val progress = (downloaded * 100 / total).toInt()
                        (context as? AppCompatActivity)?.runOnUiThread {
                            progressBar?.isIndeterminate = false
                            progressBar?.max = 100
                            progressBar?.progress = progress
                            progressText?.text = "$progress%"
                        }
                    }

                    cursor.close()
                }

                Thread.sleep(700)
            }
        }.start()
    }
}
