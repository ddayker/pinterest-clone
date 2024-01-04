package com.dayker.pexels.data.mapper

import com.dayker.pexels.data.datasource.local.entity.BookmarkImageEntity
import com.dayker.pexels.data.datasource.local.entity.CuratedImageEntity
import com.dayker.pexels.data.datasource.remote.dto.ImageDto
import com.dayker.pexels.domain.model.Image

internal fun CuratedImageEntity(
    dto: ImageDto
): CuratedImageEntity = with(dto) {
    CuratedImageEntity(
        src = src.original,
        photographer = photographer
    )
}

internal fun Image(
    entity: CuratedImageEntity
): Image = with(entity) {
    Image(
        id = id,
        src = src,
        photographer = photographer
    )
}

internal fun Image(
    entity: BookmarkImageEntity
): Image = with(entity) {
    Image(
        id = id,
        src = src,
        photographer = photographer
    )
}

internal fun BookmarkImageEntity(
    item: Image
): BookmarkImageEntity = with(item) {
    BookmarkImageEntity(
        src = src,
        photographer = photographer
    )
}

internal fun Image(
    dto: ImageDto
): Image = with(dto) {
    Image(
        id = id,
        src = src.original,
        photographer = photographer
    )
}

