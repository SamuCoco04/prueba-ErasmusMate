package com.erasmusmate.core.util

import kotlinx.coroutines.flow.Flow

interface DbFailureToggle {
    val simulateDbFailure: Flow<Boolean>
}
