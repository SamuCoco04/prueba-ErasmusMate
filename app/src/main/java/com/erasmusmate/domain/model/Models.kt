package com.erasmusmate.domain.model

import java.time.Instant

enum class UserRole { STUDENT, COORDINATOR, ADMIN }

data class User(
    val id: String,
    val name: String,
    val role: UserRole,
    val trackingCode: String
)

data class Application(
    val id: String,
    val userId: String,
    val status: String,
    val program: String,
    val language: String,
    val lastUpdated: Instant
)

data class ApplicationEditHistory(
    val id: String,
    val applicationId: String,
    val timestamp: Instant,
    val changeSummary: String
)

data class Document(
    val id: String,
    val applicationId: String,
    val type: String,
    val fileName: String,
    val status: String,
    val errorMessage: String?
)

data class Deadline(
    val id: String,
    val applicationId: String,
    val title: String,
    val dueAt: Instant
)

data class NotificationPreference(
    val id: String,
    val userId: String,
    val emailEnabled: Boolean,
    val smsEnabled: Boolean,
    val appEnabled: Boolean,
    val pushEnabled: Boolean
)

data class CommunicationLog(
    val id: String,
    val userId: String,
    val coordinatorId: String,
    val message: String,
    val timestamp: Instant
)

data class AuditEvent(
    val id: String,
    val actorId: String,
    val action: String,
    val timestamp: Instant
)

data class WorkflowConfig(
    val id: String,
    val program: String,
    val version: Int,
    val configSummary: String,
    val active: Boolean
)

data class ProvisioningAudit(
    val id: String,
    val actorId: String,
    val detail: String,
    val timestamp: Instant
)

data class SystemHealthCheck(
    val id: String,
    val status: String,
    val timestamp: Instant,
    val detail: String
)

data class BackupRecord(
    val id: String,
    val timestamp: Instant,
    val status: String
)
