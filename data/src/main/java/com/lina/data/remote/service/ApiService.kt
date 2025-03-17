package com.lina.data.remote.service

import com.lina.data.entity.ResponseEntity
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("test.json")
    suspend fun news(): Response<ResponseEntity>

}