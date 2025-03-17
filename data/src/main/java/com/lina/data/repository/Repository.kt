package com.lina.data.repository

import com.lina.data.entity.ResponseEntity
import com.lina.domain.base.Output

interface Repository {
    suspend fun news(): Output<ResponseEntity> = news()
}