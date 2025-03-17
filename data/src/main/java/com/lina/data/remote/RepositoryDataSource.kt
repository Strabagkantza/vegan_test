package com.lina.data.remote

import com.lina.data.base.BaseRemoteDataSource
import com.lina.data.entity.ResponseEntity
import com.lina.data.remote.service.ApiService
import com.lina.domain.base.Output
import retrofit2.Retrofit
import javax.inject.Inject

class RepositoryDataSource @Inject constructor(
    private val apiService: ApiService, retrofit: Retrofit
) : BaseRemoteDataSource(retrofit) {

    suspend fun news(): Output<ResponseEntity> {
        return getResponse(
            request = { apiService.news() },
            defaultErrorMessage = "There was an error. Please try again"
        )
    }
}