package com.lina.data.entity

data class NewsEntity(
    val mediaId: Int,
    val mediaUrl: String,
    val mediaUrlBig: String?,
    val mediaType: String,
    val mediaDate: MediaDateEntity,
    val mediaTitleCustom: String
) : java.io.Serializable