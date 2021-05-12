package com.socratesdiaz.coroutinesamples.features.apifetch.datasource

data class Result<out T>(val status: Status, val data: T?, val error: Error?, val message: String?) {
    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T> success(data: T?): Result<T> = Result(Status.SUCCESS, data, null, null)
        fun <T> error(message: String, error: Error?): Result<T> =
            Result(Status.ERROR, null, error, message)
    }
}