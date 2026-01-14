package com.erasmusmate.core.error

sealed class AppError(message: String) : Throwable(message) {
    class DbFailure : AppError("db_failure")
    class ValidationError(val reason: String) : AppError(reason)
}
