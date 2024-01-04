package com.dayker.pexels.data.datasource.remote.dto


import com.google.gson.annotations.SerializedName

data class CollectionsResponse(
    val collections: List<CollectionDto>,
    @SerializedName("next_page")
    val nextPage: String,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total_results")
    val totalResults: Int
)