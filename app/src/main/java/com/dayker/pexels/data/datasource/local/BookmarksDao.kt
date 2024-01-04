package com.dayker.pexels.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dayker.pexels.data.datasource.local.entity.BookmarkImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarksDao {

    @Query("SELECT * FROM bookmarks")
    fun getBookmarks(): Flow<List<BookmarkImageEntity>>

    @Query("SELECT * FROM bookmarks WHERE id = :id")
    suspend fun getImageById(id: Int): BookmarkImageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImage(image: BookmarkImageEntity)

    @Query("DELETE FROM bookmarks WHERE src = :src")
    suspend fun deleteImageBySrc(src: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE src = :src LIMIT 1)")
    suspend fun checkForBookmark(src: String): Boolean

}