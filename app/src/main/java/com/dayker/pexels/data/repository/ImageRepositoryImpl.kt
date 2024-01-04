package com.dayker.pexels.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.dayker.pexels.core.util.Resource
import com.dayker.pexels.data.datasource.local.ImageDatabase
import com.dayker.pexels.data.datasource.remote.CuratedImagesRemoteMediator
import com.dayker.pexels.data.datasource.remote.ImagePagingSource
import com.dayker.pexels.data.datasource.remote.PexelsApiService
import com.dayker.pexels.data.mapper.BookmarkImageEntity
import com.dayker.pexels.data.mapper.Collection
import com.dayker.pexels.data.mapper.CollectionEntity
import com.dayker.pexels.data.mapper.Image
import com.dayker.pexels.domain.model.Collection
import com.dayker.pexels.domain.model.Image
import com.dayker.pexels.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
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
                pagingData.map(::Image)
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
                    db.curatedDao.imagePagingSource()
                })
            return pager.flow.map { pagingData ->
                pagingData.map(::Image)
            }
        }
    }

    override suspend fun getFeaturedCollections(quantity: Int): List<Collection> {
        try {
            val response = apiService.getFeaturedCollections(perPage = quantity)
            return if (response.isSuccessful) {
                val collections = response.body()?.collections?.map(::Collection)
                if (!collections.isNullOrEmpty()) {
                    db.withTransaction {
                        db.curatedDao.clearAllCollections()
                        db.curatedDao.upsertAllCollections(collections = collections.map(::CollectionEntity))
                    }
                    collections
                } else {
                    db.curatedDao.getCollections().map(::Collection)
                }
            } else {
                db.curatedDao.getCollections().map(::Collection)
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
            return db.curatedDao.getCollections().map(::Collection)
        }
    }

    override suspend fun getImageDetails(
        id: Int,
        isImageCurated: Boolean,
        isImageBookmark: Boolean
    ): Resource<Image> {

        try {
            if (isImageBookmark) {
                val image = db.bookmarksDao.getImageById(id)
                return if (image != null) {
                    Resource.Success(Image(image))
                } else {
                    Resource.Error()
                }
            } else if (isImageCurated) {
                val image = db.curatedDao.getImageById(id)
                return if (image != null) {
                    Resource.Success(Image(image))
                } else {
                    (Resource.Error())
                }
            } else {
                val response = apiService.getImageDetails(id = id)
                return if (response.isSuccessful) {
                    val imageDto = response.body()
                    if (imageDto != null) {
                        Resource.Success(data = Image(imageDto))
                    } else {
                        Resource.Error()
                    }
                } else {
                    Resource.Error()
                }
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
            return Resource.Error()
        }
    }

    override suspend fun getBookmarks(): Flow<Resource<List<Image>>> = flow {
        emit(Resource.Loading())
        try {
            db.bookmarksDao.getBookmarks().map { entities ->
                entities.map(::Image)
            }.onEach {
                emit(Resource.Success(data = it))
            }.collect()
        } catch (e: Exception) {
            println(e.printStackTrace())
            emit(Resource.Error())
        }

    }

    override suspend fun checkForBookmark(src: String): Boolean =
        db.bookmarksDao.checkForBookmark(src)

    override suspend fun addBookmark(image: Image) {
        db.bookmarksDao.addImage(BookmarkImageEntity(image))
    }

    override suspend fun deleteBookmark(src: String) {
        db.bookmarksDao.deleteImageBySrc(src)
    }
}
