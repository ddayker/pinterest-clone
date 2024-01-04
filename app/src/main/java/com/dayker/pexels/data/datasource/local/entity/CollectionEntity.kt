package com.dayker.pexels.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collections")
data class CollectionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String
)