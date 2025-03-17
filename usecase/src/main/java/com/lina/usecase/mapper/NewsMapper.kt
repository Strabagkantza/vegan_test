package com.lina.usecase.mapper

import com.lina.data.entity.NewsEntity
import com.lina.domain.models.News

fun NewsEntity.toNews() = News(
     mediaId = mediaId,
     mediaUrl = mediaUrl,
     mediaType = mediaType,
     year =  mediaDate.year ?: "",
     mediaTitleCustom = mediaTitleCustom
)