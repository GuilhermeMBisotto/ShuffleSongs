package com.guilhermembisotto.data

const val MESSAGE_SUCCESS = "success"
const val MESSAGE_FAILURE = "failure"
const val MESSAGE_ERROR = "error"

const val REMOTE_LOCAL_ERROR = 1000
const val REMOTE_ERROR = 2000
const val LOCAL_ERROR = 3000

data class DataSourceResponse(
    val code: Int,
    val message: String
)

class RepositoryObject<T>(
    val local: DataSourceResponse? = null,
    val remote: DataSourceResponse? = null,
    var content: T? = null
)