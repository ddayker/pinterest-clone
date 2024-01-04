package com.dayker.pexels.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dayker.pexels.data.datasource.local.entity.CollectionEntity
import com.dayker.pexels.data.datasource.local.entity.CuratedImageEntity


@Database(
    entities = [CuratedImageEntity::class, CollectionEntity::class],
    version = 1
)
abstract class ImageDatabase : RoomDatabase() {

    abstract val dao: CuratedDao
}