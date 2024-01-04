package com.dayker.pexels.data.datasource.remote.dto


import com.google.gson.annotations.SerializedName

data class CollectionDto(
    val description: String,
    val id: String,
    @SerializedName("media_count")
    val mediaCount: Int,
    @SerializedName("photos_count")
    val photosCount: Int,
    val `private`: Boolean,
    val title: String,
    @SerializedName("videos_count")
    val videosCount: Int
)