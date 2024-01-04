package com.dayker.pexels.data.mapper

import com.dayker.pexels.data.datasource.local.entity.CollectionEntity
import com.dayker.pexels.data.datasource.remote.dto.CollectionDto
import com.dayker.pexels.domain.model.Collection

internal fun Collection(
    dto: CollectionDto
): Collection = with(dto) {
    Collection(
        id = id,
        title = title
    )
}

internal fun CollectionEntity(
    item: Collection
): CollectionEntity = with(item) {
    CollectionEntity(
        title = title
    )
}

internal fun Collection(
    entity: CollectionEntity
): Collection = with(entity) {
    Collection(
        id = id.toString(),
        title = title
    )
}