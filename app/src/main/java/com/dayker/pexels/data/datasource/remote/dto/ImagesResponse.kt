package com.dayker.pexels.data.datasource.remote.dto


import com.google.gson.annotations.SerializedName

data class ImagesResponse(
    @SerializedName("next_page")
    val nextPage: String,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val photos: List<ImageDto>
)