package com.erasmusmate.domain.repository

import com.erasmusmate.domain.model.*
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun authenticateUniversity(username: String, password: String): Result<User>
    suspend fun authenticateSocial(token: String): Result<User>
    fun currentUser(): Flow<User?>
    suspend fun setCurrentUser(user: User?)
}

interface ApplicationRepository {
    fun applicationsForUser(userId: String): Flow<List<Application>>
    fun allApplications(): Flow<List<Application>>
    suspend fun updateApplication(application: Application)
    suspend fun recordEditHistory(history: ApplicationEditHistory)
    fun editHistory(applicationId: String): Flow<List<ApplicationEditHistory>>
}

interface DocumentRepository {
    fun documents(applicationId: String): Flow<List<Document>>
    suspend fun saveDocument(document: Document)
}

interface DeadlineRepository {
    fun deadlines(applicationId: String): Flow<List<Deadline>>
    suspend fun upsertDeadline(deadline: Deadline)
}

interface NotificationRepository {
    fun preference(userId: String): Flow<NotificationPreference?>
    suspend fun savePreference(pref: NotificationPreference)
}

interface CommunicationRepository {
    fun logs(userId: String): Flow<List<CommunicationLog>>
    suspend fun logCommunication(log: CommunicationLog)
}

interface AuditRepository {
    fun events(): Flow<List<AuditEvent>>
    suspend fun logEvent(event: AuditEvent)
}

interface AdminRepository {
    fun workflowConfigs(program: String): Flow<List<WorkflowConfig>>
    suspend fun saveWorkflowConfig(config: WorkflowConfig)
    suspend fun setActiveWorkflow(id: String)
    fun provisioningAudits(): Flow<List<ProvisioningAudit>>
    suspend fun logProvisioningAudit(audit: ProvisioningAudit)
    fun backupRecords(): Flow<List<BackupRecord>>
    suspend fun saveBackupRecord(record: BackupRecord)
    fun healthChecks(): Flow<List<SystemHealthCheck>>
    suspend fun saveHealthCheck(check: SystemHealthCheck)
    suspend fun bulkUpdateStatus(status: String)
}
