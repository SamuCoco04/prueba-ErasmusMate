package com.erasmusmate.data.repository

import com.erasmusmate.core.error.AppError
import com.erasmusmate.core.util.DbFailureToggle
import kotlinx.coroutines.flow.first

suspend fun DbFailureToggle.ensureDbAvailable() {
    if (simulateDbFailure.first()) {
        throw AppError.DbFailure()
    }
}
