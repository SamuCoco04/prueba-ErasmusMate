package com.erasmusmate.domain.usecase

import com.erasmusmate.domain.model.AuditEvent
import com.erasmusmate.domain.model.Document
import com.erasmusmate.domain.repository.AuditRepository
import java.time.Instant
import java.util.UUID

class ValidateDocumentUseCase {
    fun execute(document: Document): Result<Document> {
        val allowed = setOf("pdf", "docx", "doc")
        val extension = document.fileName.substringAfterLast('.', "").lowercase()
        return if (extension.isBlank() || extension !in allowed) {
            Result.failure(IllegalArgumentException("invalid_format"))
        } else if (document.type.isBlank()) {
            Result.failure(IllegalArgumentException("missing_type"))
        } else {
            Result.success(document)
        }
    }
}

class LogAuditEventUseCase(private val repository: AuditRepository) {
    suspend fun log(actorId: String, action: String) {
        repository.logEvent(
            AuditEvent(
                id = UUID.randomUUID().toString(),
                actorId = actorId,
                action = action,
                timestamp = Instant.now()
            )
        )
    }
}
