package com.lina.data.impl

import com.lina.data.entity.ResponseEntity
import com.lina.data.remote.RepositoryDataSource
import com.lina.data.repository.Repository
import com.lina.domain.base.Output
import javax.inject.Inject

internal class RepositoryImpl @Inject constructor(
    private val repositoryDataSource: RepositoryDataSource
) : Repository {
    override suspend fun news(): Output<ResponseEntity> = repositoryDataSource.news()
}