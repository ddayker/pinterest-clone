package com.dayker.pexels.data.downloader

interface Downloader {
    fun downloadFile(url: String): Long
}