package com.lina.data.entity

data class ResponseEntity(
    val status: Boolean,
    val lang: String,
    val content: List<NewsEntity>
) : java.io.Serializable