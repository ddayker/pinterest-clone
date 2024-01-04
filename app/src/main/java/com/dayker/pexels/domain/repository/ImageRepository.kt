package com.dayker.pexels.domain.repository

import androidx.paging.PagingData
import com.dayker.pexels.domain.model.Collection
import com.dayker.pexels.domain.model.Image
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    suspend fun getImages(query: String = ""): Flow<PagingData<Image>>

    suspend fun getFeaturedCollections(quantity: Int): List<Collection>
}