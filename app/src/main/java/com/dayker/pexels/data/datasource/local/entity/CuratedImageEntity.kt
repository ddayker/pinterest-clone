package com.dayker.pexels.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "curated")
data class CuratedImageEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val src: String,
    val photographer: String
)