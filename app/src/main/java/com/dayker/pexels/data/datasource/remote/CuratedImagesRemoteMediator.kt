package com.dayker.pexels.data.datasource.remote


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dayker.pexels.data.datasource.local.ImageDatabase
import com.dayker.pexels.data.datasource.local.entity.CuratedImageEntity
import com.dayker.pexels.data.mapper.CuratedImageEntity
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CuratedImagesRemoteMediator @Inject constructor(
    private val imageDb: ImageDatabase,
    private val apiService: PexelsApiService,
) : RemoteMediator<Int, CuratedImageEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CuratedImageEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        ((lastItem.id + (state.config.pageSize - state.config.initialLoadSize)) / state.config.pageSize) + 1
                    }
                }
            }
            val images = apiService.getCurated(
                page = loadKey,
                perPage = if (loadKey == 1) state.config.initialLoadSize else state.config.pageSize
            ).photos

            imageDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    imageDb.curatedDao.clearAllImages()
                    imageDb.curatedDao.resetImageAutoIncrementIndexes()
                }
                val imageEntities = images.map { CuratedImageEntity(it) }
                imageDb.curatedDao.upsertAllImages(imageEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = images.isEmpty()
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}





