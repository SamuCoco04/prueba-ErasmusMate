package com.erasmusmate.data.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.erasmusmate.domain.model.AuditEvent
import com.erasmusmate.domain.repository.NotificationGateway
import com.erasmusmate.domain.repository.AuditRepository
import java.time.Instant
import java.util.UUID

class DeadlineReminderWorker(
    context: Context,
    params: WorkerParameters,
    private val notificationGateway: NotificationGateway,
    private val auditRepository: AuditRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        notificationGateway.sendPush("student", "Deadline reminder")
        auditRepository.logEvent(
            AuditEvent(UUID.randomUUID().toString(), "system", "deadline_reminder", Instant.now())
        )
        return Result.success()
    }
}

class DeadlineReminderWorkerFactory(
    private val repositoryProvider: com.erasmusmate.data.repository.RepositoryProvider
) {
    fun create(context: Context, params: WorkerParameters): DeadlineReminderWorker {
        return DeadlineReminderWorker(
            context,
            params,
            repositoryProvider.notificationGateway,
            repositoryProvider.auditRepository
        )
    }
}
