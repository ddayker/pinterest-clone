package com.dayker.pexels.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.dayker.pexels.data.datasource.local.ImageDatabase
import com.dayker.pexels.data.datasource.remote.CuratedImagesRemoteMediator
import com.dayker.pexels.data.datasource.remote.ImagePagingSource
import com.dayker.pexels.data.datasource.remote.PexelsApiService
import com.dayker.pexels.data.mapper.Collection
import com.dayker.pexels.data.mapper.CollectionEntity
import com.dayker.pexels.data.mapper.Image
import com.dayker.pexels.domain.model.Collection
import com.dayker.pexels.domain.model.Image
import com.dayker.pexels.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val db: ImageDatabase,
    private val apiService: PexelsApiService,
    private val curatedImagesRemoteMediator: CuratedImagesRemoteMediator,
    private val pagingSourceFactory: ImagePagingSource.ImagePagingSourceFactory
) : ImageRepository {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getImages(query: String): Flow<PagingData<Image>> {

        if (query.isNotEmpty()) {
            val pager = Pager(
                config = PagingConfig(
                    pageSize = PexelsApiService.PAGE_SIZE,
                    prefetchDistance = PexelsApiService.PREFETCH_DISTANCE,
                    initialLoadSize = PexelsApiService.INITIAL_LOAD
                ),
                pagingSourceFactory = {
                    pagingSourceFactory.create(query)
                }
            )
            return pager.flow.map { pagingData ->
                pagingData.map { Image(it) }
            }
        } else {
            val pager = Pager(
                config = PagingConfig(
                    pageSize = PexelsApiService.PAGE_SIZE,
                    prefetchDistance = PexelsApiService.PREFETCH_DISTANCE,
                    initialLoadSize = PexelsApiService.INITIAL_LOAD
                ),
                remoteMediator = curatedImagesRemoteMediator,
                pagingSourceFactory = {
                    db.dao.imagePagingSource()
                })
            return pager.flow.map { pagingData ->
                pagingData.map { Image(it) }
            }
        }
    }

    override suspend fun getFeaturedCollections(quantity: Int): List<Collection> {
        try {
            val response = apiService.getFeaturedCollections(perPage = quantity)
            return if (response.isSuccessful) {
                val collections = response.body()?.collections?.map { Collection(it) }
                if (!collections.isNullOrEmpty()) {
                    db.withTransaction {
                        db.dao.clearAllCollections()
                        db.dao.upsertAllCollections(collections = collections.map {
                            CollectionEntity(
                                it
                            )
                        })

                    }
                    collections
                } else {
                    db.dao.getCollections().map { Collection(it) }
                }
            } else {
                db.dao.getCollections().map { Collection(it) }
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
            return db.dao.getCollections().map { Collection(it) }
        }
    }
}
