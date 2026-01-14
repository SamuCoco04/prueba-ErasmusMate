package com.erasmusmate.data.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.erasmusmate.domain.model.SystemHealthCheck
import com.erasmusmate.domain.repository.AdminRepository
import java.time.Instant
import java.util.UUID

class SystemHealthCheckWorker(
    context: Context,
    params: WorkerParameters,
    private val adminRepository: AdminRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        adminRepository.saveHealthCheck(
            SystemHealthCheck(
                id = UUID.randomUUID().toString(),
                status = "OK",
                timestamp = Instant.now(),
                detail = "scheduled_check"
            )
        )
        return Result.success()
    }
}

class SystemHealthWorkerFactory(
    private val repositoryProvider: com.erasmusmate.data.repository.RepositoryProvider
) {
    fun create(context: Context, params: WorkerParameters): SystemHealthCheckWorker {
        return SystemHealthCheckWorker(context, params, repositoryProvider.adminRepository)
    }
}
