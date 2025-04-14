package com.rajnish.autoinappupdatebyrj.uitls



import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import java.io.File

object UpdateHelper {

    private const val APK_NAME = "update.apk"
    internal var downloadId: Long = -1L

    fun startDownload(
        context: Context,
        apkUrl: String,
        progressBar: ProgressBar? = null,
        progressText: TextView? = null
    ) {
        // Delete old APK if exists
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), APK_NAME)
        if (file.exists()) file.delete()

        val request = DownloadManager.Request(apkUrl.toUri()).apply {
            setTitle("Downloading update")
            setDescription("Downloading APK...")
            setMimeType("application/vnd.android.package-archive")
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, APK_NAME)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setAllowedOverMetered(true)
            setAllowedOverRoaming(true)
        }

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadId = downloadManager.enqueue(request)

        progressBar?.isIndeterminate = true
        progressBar?.isVisible = true
        progressText?.text = "Download started..."

        DownloadTracker.trackDownloadProgress(context, progressBar, progressText,APK_NAME)
    }
}
