package com.lina.usecase.impl

import com.lina.data.repository.Repository
import com.lina.domain.base.Output
import com.lina.domain.models.News
import com.lina.usecase.UseCase
import com.lina.usecase.mapper.toNews
import javax.inject.Inject

internal class UseCaseImpl @Inject constructor(
    private val repository: Repository
) : UseCase {

    override suspend fun executeNews(): Output<List<News>> {
        val res = repository.news()
        if (res.status == Output.Status.SUCCESS) {
            res.data?.let {
                return Output(status = res.status, data = it.content.map { new ->
                    new.toNews()
                }, message = res.message, error = res.error, headers = res.headers)
            }
        }
        return Output(
            status = res.status,
            data = null,
            message = res.message,
            error = res.error,
            headers = null
        )
    }

}