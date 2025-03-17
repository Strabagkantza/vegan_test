package com.lina.usecase

import com.lina.domain.base.Output
import com.lina.domain.models.News

interface UseCase {
    suspend fun executeNews(): Output<List<News>>
}