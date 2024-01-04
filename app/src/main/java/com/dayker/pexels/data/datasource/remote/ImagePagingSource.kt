package com.dayker.pexels.data.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dayker.pexels.data.datasource.remote.dto.ImageDto
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class ImagePagingSource @AssistedInject constructor(
    private val apiService: PexelsApiService,
    @Assisted private val query: String
) : PagingSource<Int, ImageDto>() {
    override fun getRefreshKey(state: PagingState<Int, ImageDto>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageDto> {
        try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val pageSize =
                params.loadSize.coerceAtMost(maximumValue = PexelsApiService.MAX_PAGE_SIZE)
            val response = apiService.getImages(
                query = query,
                page = pageNumber,
                perPage = pageSize
            )
            return if (response.isSuccessful) {
                val images = response.body()!!.photos
                val nextPageNumber = if (images.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > INITIAL_PAGE_NUMBER) pageNumber - 1 else null
                LoadResult.Page(
                    data = images,
                    prevKey = prevPageNumber,
                    nextKey = nextPageNumber
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    @AssistedFactory
    interface ImagePagingSourceFactory {
        fun create(query: String): ImagePagingSource
    }

    companion object {

        const val INITIAL_PAGE_NUMBER = 1
    }
}