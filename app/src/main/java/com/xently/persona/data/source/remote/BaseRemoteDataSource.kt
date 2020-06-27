package com.xently.persona.data.source.remote

import com.xently.persona.data.TaskResult
import com.xently.persona.data.model.ServerError
import com.xently.persona.utils.JSON_CONVERTER
import com.xently.persona.utils.Log
import com.xently.persona.utils.Log.Type.ERROR
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseRemoteDataSource internal constructor(private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {
    @Suppress("UNCHECKED_CAST")
    protected suspend fun <T> sendRequest(request: suspend () -> Response<T>): TaskResult<T> {
        return try {
            val response = request.invoke() // Initiate actual network request call
            val (statusCode, body, errorBody) = Triple(
                response.code(),
                response.body(),
                response.errorBody()
            )
            if (response.isSuccessful) {
                val alt = Any() as T
                TaskResult.Success(if (statusCode == 204) alt else body ?: alt)
            } else {
                withContext<TaskResult<T>>(ioDispatcher) {
                    val error = JSON_CONVERTER.fromJson<ServerError>(
                        errorBody?.string(),
                        ServerError::class.java
                    )

                    Log.show(this@BaseRemoteDataSource::class.java.simpleName, error) // TODO
                    TaskResult.Error(Exception(error.message))
                }
            }
        } catch (ex: Exception) {
            Log.show(this@BaseRemoteDataSource::class.java.simpleName, ex.message, ex, ERROR)
            TaskResult.Error(ex)
        }
    }
}