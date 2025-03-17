package com.lina.domain.base

import okhttp3.Headers

data class Output<out T>(
    val status: Status,
    val data: T?,
    val error: ApiError?,
    val message: String?,
    val headers: Headers?
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?, headers: Headers): Output<T> {
            return Output(Status.SUCCESS, data, null, null, headers)
        }

        fun <T> error(message: String, error: ApiError?): Output<T> {
            return Output(Status.ERROR, null, error, message, null)
        }

        fun <T> loading(data: T? = null): Output<T> {
            return Output(Status.LOADING, data, null, null, null)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, data=$data, error=$error, message=$message, headers=$headers)"
    }
}