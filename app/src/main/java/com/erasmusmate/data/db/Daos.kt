package com.erasmusmate.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity WHERE id = :id")
    suspend fun getUser(id: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: UserEntity)
}

@Dao
interface ApplicationDao {
    @Query("SELECT * FROM ApplicationEntity WHERE userId = :userId")
    fun applicationsForUser(userId: String): Flow<List<ApplicationEntity>>

    @Query("SELECT * FROM ApplicationEntity")
    fun allApplications(): Flow<List<ApplicationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(application: ApplicationEntity)
}

@Dao
interface ApplicationEditHistoryDao {
    @Query("SELECT * FROM ApplicationEditHistoryEntity WHERE applicationId = :applicationId")
    fun history(applicationId: String): Flow<List<ApplicationEditHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: ApplicationEditHistoryEntity)
}

@Dao
interface DocumentDao {
    @Query("SELECT * FROM DocumentEntity WHERE applicationId = :applicationId")
    fun documents(applicationId: String): Flow<List<DocumentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(document: DocumentEntity)
}

@Dao
interface DeadlineDao {
    @Query("SELECT * FROM DeadlineEntity WHERE applicationId = :applicationId")
    fun deadlines(applicationId: String): Flow<List<DeadlineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(deadline: DeadlineEntity)
}

@Dao
interface NotificationPreferenceDao {
    @Query("SELECT * FROM NotificationPreferenceEntity WHERE userId = :userId")
    fun preference(userId: String): Flow<NotificationPreferenceEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(pref: NotificationPreferenceEntity)
}

@Dao
interface CommunicationDao {
    @Query("SELECT * FROM CommunicationLogEntity WHERE userId = :userId")
    fun logs(userId: String): Flow<List<CommunicationLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: CommunicationLogEntity)
}

@Dao
interface AuditDao {
    @Query("SELECT * FROM AuditEventEntity ORDER BY timestampEpoch DESC")
    fun events(): Flow<List<AuditEventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: AuditEventEntity)
}

@Dao
interface WorkflowConfigDao {
    @Query("SELECT * FROM WorkflowConfigEntity WHERE program = :program")
    fun configs(program: String): Flow<List<WorkflowConfigEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(config: WorkflowConfigEntity)

    @Query("UPDATE WorkflowConfigEntity SET active = CASE WHEN id = :id THEN 1 ELSE 0 END WHERE program = :program")
    suspend fun setActive(program: String, id: String)
}

@Dao
interface ProvisioningAuditDao {
    @Query("SELECT * FROM ProvisioningAuditEntity ORDER BY timestampEpoch DESC")
    fun audits(): Flow<List<ProvisioningAuditEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(audit: ProvisioningAuditEntity)
}

@Dao
interface HealthCheckDao {
    @Query("SELECT * FROM SystemHealthCheckEntity ORDER BY timestampEpoch DESC")
    fun checks(): Flow<List<SystemHealthCheckEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(check: SystemHealthCheckEntity)
}

@Dao
interface BackupRecordDao {
    @Query("SELECT * FROM BackupRecordEntity ORDER BY timestampEpoch DESC")
    fun records(): Flow<List<BackupRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: BackupRecordEntity)
}
