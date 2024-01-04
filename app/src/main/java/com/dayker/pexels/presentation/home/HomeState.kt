package com.dayker.pexels.presentation.home

import androidx.paging.PagingData
import com.dayker.pexels.domain.model.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeState(

    val query: String = "",

    val featuredCollections: List<String> = emptyList(),

    val images: Flow<PagingData<Image>> = emptyFlow(),

    val isNoResults: Boolean = false,

    val isNoNetwork: Boolean = false
)