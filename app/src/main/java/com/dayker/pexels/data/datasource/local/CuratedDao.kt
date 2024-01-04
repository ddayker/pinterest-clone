package com.dayker.pexels.data.datasource.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dayker.pexels.data.datasource.local.entity.CollectionEntity
import com.dayker.pexels.data.datasource.local.entity.CuratedImageEntity

@Dao
interface CuratedDao {

    @Upsert
    suspend fun upsertAllImages(images: List<CuratedImageEntity>)

    @Query("SELECT * FROM curated")
    fun imagePagingSource(): PagingSource<Int, CuratedImageEntity>

    @Query("DELETE FROM curated")
    suspend fun clearAllImages()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'curated'")
    suspend fun resetImageAutoIncrementIndexes()

    @Upsert
    suspend fun upsertAllCollections(collections: List<CollectionEntity>)

    @Query("SELECT * FROM collections")
    suspend fun getCollections(): List<CollectionEntity>

    @Query("DELETE FROM collections")
    suspend fun clearAllCollections()
}
