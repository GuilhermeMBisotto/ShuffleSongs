package com.guilhermembisotto.data

import org.json.JSONObject
import retrofit2.Response

const val SUCCESS_CODE = 200

suspend fun <T> request(requestBlock: suspend () -> Response<T>): RepositoryObject<T> {
    try {
        val getRequest = requestBlock()

        return if (getRequest.isSuccessful) {
            RepositoryObject(
                remote = DataSourceResponse(
                    code = getRequest.code(),
                    message = getRequest.message()
                ),
                content = getRequest.body()
            )
        } else {
            var message = ""
            getRequest.errorBody()?.string()?.run {
                val errorJson = JSONObject(this)
                message = errorJson.getString("message")
            }

            RepositoryObject(
                remote = DataSourceResponse(
                    getRequest.code(),
                    message
                ),
                content = getRequest.body()
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return RepositoryObject(
            remote = DataSourceResponse(
                code = REMOTE_ERROR,
                message = e.message ?: e.localizedMessage ?: e.toString()
            )
        )
    }
}