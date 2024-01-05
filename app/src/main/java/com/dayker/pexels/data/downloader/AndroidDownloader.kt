package com.dayker.pexels.data.downloader

import android.app.DownloadManager
import android.os.Environment
import androidx.core.net.toUri
import javax.inject.Inject

class AndroidDownloader @Inject constructor(
    private val downloadManager: DownloadManager
) : Downloader {

    override fun downloadFile(url: String): Long {
        val currentTimeMillis = System.currentTimeMillis()
        val fileName = "image_${currentTimeMillis}.jpg"
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/jpeg")
            .setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI
            )
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(fileName)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        return downloadManager.enqueue(request)
    }
}