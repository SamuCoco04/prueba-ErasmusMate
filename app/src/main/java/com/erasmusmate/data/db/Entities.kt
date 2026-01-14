package com.erasmusmate.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val role: String,
    val trackingCode: String
)

@Entity
data class ApplicationEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val status: String,
    val program: String,
    val language: String,
    val lastUpdatedEpoch: Long
)

@Entity
data class ApplicationEditHistoryEntity(
    @PrimaryKey val id: String,
    val applicationId: String,
    val timestampEpoch: Long,
    val changeSummary: String
)

@Entity
data class DocumentEntity(
    @PrimaryKey val id: String,
    val applicationId: String,
    val type: String,
    val fileName: String,
    val status: String,
    val errorMessage: String?
)

@Entity
data class DeadlineEntity(
    @PrimaryKey val id: String,
    val applicationId: String,
    val title: String,
    val dueAtEpoch: Long
)

@Entity
data class NotificationPreferenceEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val emailEnabled: Boolean,
    val smsEnabled: Boolean,
    val appEnabled: Boolean,
    val pushEnabled: Boolean
)

@Entity
data class CommunicationLogEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val coordinatorId: String,
    val message: String,
    val timestampEpoch: Long
)

@Entity
data class AuditEventEntity(
    @PrimaryKey val id: String,
    val actorId: String,
    val action: String,
    val timestampEpoch: Long
)

@Entity
data class WorkflowConfigEntity(
    @PrimaryKey val id: String,
    val program: String,
    val version: Int,
    val configSummary: String,
    val active: Boolean
)

@Entity
data class ProvisioningAuditEntity(
    @PrimaryKey val id: String,
    val actorId: String,
    val detail: String,
    val timestampEpoch: Long
)

@Entity
data class SystemHealthCheckEntity(
    @PrimaryKey val id: String,
    val status: String,
    val timestampEpoch: Long,
    val detail: String
)

@Entity
data class BackupRecordEntity(
    @PrimaryKey val id: String,
    val timestampEpoch: Long,
    val status: String
)
