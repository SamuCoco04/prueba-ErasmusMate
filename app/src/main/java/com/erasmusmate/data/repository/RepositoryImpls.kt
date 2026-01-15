package com.erasmusmate.data.repository

import com.erasmusmate.core.error.AppError
import com.erasmusmate.data.datastore.SettingsDataStore
import com.erasmusmate.data.db.*
import com.erasmusmate.domain.model.*
import com.erasmusmate.domain.repository.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.time.Instant

class UserRepositoryImpl(
    private val database: AppDatabase,
    private val settings: SettingsDataStore,
    private val universityAuthProvider: UniversityAuthProvider,
    private val socialAuthProvider: SocialAuthProvider
) : UserRepository {
    private val currentUserFlow = MutableStateFlow<User?>(null)

    override suspend fun authenticateUniversity(username: String, password: String): Result<User> {
        settings.ensureDbAvailable()
        val enabled = settings.universityAuthEnabled
        if (!enabled.collectOnce()) {
            return Result.failure(IllegalStateException("university_auth_disabled"))
        }
        val userIdResult = universityAuthProvider.authenticate(username, password)
        return userIdResult.map { userId ->
            User(
                id = userId,
                name = username,
                role = UserRole.STUDENT,
                trackingCode = "TRK-${userId.take(6)}"
            ).also { user ->
                database.userDao().upsert(user.toEntity())
                currentUserFlow.update { user }
            }
        }
    }

    override suspend fun authenticateSocial(token: String): Result<User> {
        settings.ensureDbAvailable()
        val enabled = settings.socialAuthEnabled
        if (!enabled.collectOnce()) {
            return Result.failure(IllegalStateException("social_auth_disabled"))
        }
        val userIdResult = socialAuthProvider.authenticate(token)
        return userIdResult.map { userId ->
            User(
                id = userId,
                name = "Social User",
                role = UserRole.STUDENT,
                trackingCode = "TRK-${userId.take(6)}"
            ).also { user ->
                database.userDao().upsert(user.toEntity())
                currentUserFlow.update { user }
            }
        }
    }

    override fun currentUser(): Flow<User?> = currentUserFlow

    override suspend fun setCurrentUser(user: User?) {
        currentUserFlow.update { user }
    }
}

class ApplicationRepositoryImpl(
    private val database: AppDatabase,
    private val settings: SettingsDataStore
) : ApplicationRepository {
    override fun applicationsForUser(userId: String): Flow<List<Application>> {
        return database.applicationDao().applicationsForUser(userId).map { list -> list.map { it.toModel() } }
    }

    override fun allApplications(): Flow<List<Application>> {
        return database.applicationDao().allApplications().map { list -> list.map { it.toModel() } }
    }

    override suspend fun updateApplication(application: Application) {
        settings.ensureDbAvailable()
        database.applicationDao().upsert(application.toEntity())
    }

    override suspend fun recordEditHistory(history: ApplicationEditHistory) {
        settings.ensureDbAvailable()
        database.editHistoryDao().insert(history.toEntity())
    }

    override fun editHistory(applicationId: String): Flow<List<ApplicationEditHistory>> {
        return database.editHistoryDao().history(applicationId).map { list -> list.map { it.toModel() } }
    }
}

class DocumentRepositoryImpl(
    private val database: AppDatabase,
    private val settings: SettingsDataStore
) : DocumentRepository {
    override fun documents(applicationId: String): Flow<List<Document>> {
        return database.documentDao().documents(applicationId).map { list -> list.map { it.toModel() } }
    }

    override suspend fun saveDocument(document: Document) {
        settings.ensureDbAvailable()
        database.documentDao().upsert(document.toEntity())
    }
}

class DeadlineRepositoryImpl(
    private val database: AppDatabase,
    private val settings: SettingsDataStore
) : DeadlineRepository {
    override fun deadlines(applicationId: String): Flow<List<Deadline>> {
        return database.deadlineDao().deadlines(applicationId).map { list -> list.map { it.toModel() } }
    }

    override suspend fun upsertDeadline(deadline: Deadline) {
        settings.ensureDbAvailable()
        database.deadlineDao().upsert(deadline.toEntity())
    }
}

class NotificationRepositoryImpl(
    private val database: AppDatabase,
    private val settings: SettingsDataStore
) : NotificationRepository {
    override fun preference(userId: String): Flow<NotificationPreference?> {
        return database.notificationPreferenceDao().preference(userId).map { it?.toModel() }
    }

    override suspend fun savePreference(pref: NotificationPreference) {
        settings.ensureDbAvailable()
        database.notificationPreferenceDao().upsert(pref.toEntity())
    }
}

class CommunicationRepositoryImpl(
    private val database: AppDatabase,
    private val settings: SettingsDataStore
) : CommunicationRepository {
    override fun logs(userId: String): Flow<List<CommunicationLog>> {
        return database.communicationDao().logs(userId).map { list -> list.map { it.toModel() } }
    }

    override suspend fun logCommunication(log: CommunicationLog) {
        settings.ensureDbAvailable()
        database.communicationDao().insert(log.toEntity())
    }
}

class AuditRepositoryImpl(
    private val database: AppDatabase,
    private val settings: SettingsDataStore
) : AuditRepository {
    override fun events(): Flow<List<AuditEvent>> {
        return database.auditDao().events().map { list -> list.map { it.toModel() } }
    }

    override suspend fun logEvent(event: AuditEvent) {
        settings.ensureDbAvailable()
        database.auditDao().insert(event.toEntity())
    }
}

class AdminRepositoryImpl(
    private val database: AppDatabase,
    private val settings: SettingsDataStore
) : AdminRepository {
    override fun workflowConfigs(program: String): Flow<List<WorkflowConfig>> {
        return database.workflowConfigDao().configs(program).map { list -> list.map { it.toModel() } }
    }

    override suspend fun saveWorkflowConfig(config: WorkflowConfig) {
        settings.ensureDbAvailable()
        database.workflowConfigDao().upsert(config.toEntity())
    }

    override suspend fun setActiveWorkflow(id: String) {
        settings.ensureDbAvailable()
        val config = database.workflowConfigDao().configs("default").collectOnce().firstOrNull()
            ?: throw AppError.ValidationError("missing_workflow")
        database.workflowConfigDao().setActive(config.program, id)
    }

    override fun provisioningAudits(): Flow<List<ProvisioningAudit>> {
        return database.provisioningAuditDao().audits().map { list -> list.map { it.toModel() } }
    }

    override suspend fun logProvisioningAudit(audit: ProvisioningAudit) {
        settings.ensureDbAvailable()
        database.provisioningAuditDao().insert(audit.toEntity())
    }

    override fun backupRecords(): Flow<List<BackupRecord>> {
        return database.backupRecordDao().records().map { list -> list.map { it.toModel() } }
    }

    override suspend fun saveBackupRecord(record: BackupRecord) {
        settings.ensureDbAvailable()
        database.backupRecordDao().insert(record.toEntity())
    }

    override fun healthChecks(): Flow<List<SystemHealthCheck>> {
        return database.healthCheckDao().checks().map { list -> list.map { it.toModel() } }
    }

    override suspend fun saveHealthCheck(check: SystemHealthCheck) {
        settings.ensureDbAvailable()
        database.healthCheckDao().insert(check.toEntity())
    }

    override suspend fun bulkUpdateStatus(status: String) {
        settings.ensureDbAvailable()
        database.applicationDao().allApplications().collectOnce().forEach { entity ->
            database.applicationDao().upsert(entity.copy(status = status))
        }
    }
}

private suspend fun <T> Flow<T>.collectOnce(): T {
    return kotlinx.coroutines.flow.first(this)
}

private fun User.toEntity() = UserEntity(id, name, role.name, trackingCode)
private fun UserEntity.toModel() = User(id, name, UserRole.valueOf(role), trackingCode)

private fun Application.toEntity() = ApplicationEntity(id, userId, status, program, language, lastUpdated.epochSecond)
private fun ApplicationEntity.toModel() = Application(id, userId, status, program, language, Instant.ofEpochSecond(lastUpdatedEpoch))

private fun ApplicationEditHistory.toEntity() = ApplicationEditHistoryEntity(id, applicationId, timestamp.epochSecond, changeSummary)
private fun ApplicationEditHistoryEntity.toModel() = ApplicationEditHistory(id, applicationId, Instant.ofEpochSecond(timestampEpoch), changeSummary)

private fun Document.toEntity() = DocumentEntity(id, applicationId, type, fileName, status, errorMessage)
private fun DocumentEntity.toModel() = Document(id, applicationId, type, fileName, status, errorMessage)

private fun Deadline.toEntity() = DeadlineEntity(id, applicationId, title, dueAt.epochSecond)
private fun DeadlineEntity.toModel() = Deadline(id, applicationId, title, Instant.ofEpochSecond(dueAtEpoch))

private fun NotificationPreference.toEntity() = NotificationPreferenceEntity(id, userId, emailEnabled, smsEnabled, appEnabled, pushEnabled)
private fun NotificationPreferenceEntity.toModel() = NotificationPreference(id, userId, emailEnabled, smsEnabled, appEnabled, pushEnabled)

private fun CommunicationLog.toEntity() = CommunicationLogEntity(id, userId, coordinatorId, message, timestamp.epochSecond)
private fun CommunicationLogEntity.toModel() = CommunicationLog(id, userId, coordinatorId, message, Instant.ofEpochSecond(timestampEpoch))

private fun AuditEvent.toEntity() = AuditEventEntity(id, actorId, action, timestamp.epochSecond)
private fun AuditEventEntity.toModel() = AuditEvent(id, actorId, action, Instant.ofEpochSecond(timestampEpoch))

private fun WorkflowConfig.toEntity() = WorkflowConfigEntity(id, program, version, configSummary, active)
private fun WorkflowConfigEntity.toModel() = WorkflowConfig(id, program, version, configSummary, active)

private fun ProvisioningAudit.toEntity() = ProvisioningAuditEntity(id, actorId, detail, timestamp.epochSecond)
private fun ProvisioningAuditEntity.toModel() = ProvisioningAudit(id, actorId, detail, Instant.ofEpochSecond(timestampEpoch))

private fun SystemHealthCheck.toEntity() = SystemHealthCheckEntity(id, status, timestamp.epochSecond, detail)
private fun SystemHealthCheckEntity.toModel() = SystemHealthCheck(id, status, Instant.ofEpochSecond(timestampEpoch), detail)

private fun BackupRecord.toEntity() = BackupRecordEntity(id, timestamp.epochSecond, status)
private fun BackupRecordEntity.toModel() = BackupRecord(id, Instant.ofEpochSecond(timestampEpoch), status)
