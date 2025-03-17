package com.lina.domain.models

data class News(
    val mediaId: Int,
    val mediaUrl: String,
    val mediaType: String,
    val year: String,
    val mediaTitleCustom: String
)