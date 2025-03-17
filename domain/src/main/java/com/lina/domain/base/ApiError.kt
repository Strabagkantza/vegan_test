package com.lina.domain.base

data class ApiError(
    val statusCode: Int = 0,
    val statusMessage: String? = null
)