package com.erasmusmate.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserEntity::class,
        ApplicationEntity::class,
        ApplicationEditHistoryEntity::class,
        DocumentEntity::class,
        DeadlineEntity::class,
        NotificationPreferenceEntity::class,
        CommunicationLogEntity::class,
        AuditEventEntity::class,
        WorkflowConfigEntity::class,
        ProvisioningAuditEntity::class,
        SystemHealthCheckEntity::class,
        BackupRecordEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun applicationDao(): ApplicationDao
    abstract fun editHistoryDao(): ApplicationEditHistoryDao
    abstract fun documentDao(): DocumentDao
    abstract fun deadlineDao(): DeadlineDao
    abstract fun notificationPreferenceDao(): NotificationPreferenceDao
    abstract fun communicationDao(): CommunicationDao
    abstract fun auditDao(): AuditDao
    abstract fun workflowConfigDao(): WorkflowConfigDao
    abstract fun provisioningAuditDao(): ProvisioningAuditDao
    abstract fun healthCheckDao(): HealthCheckDao
    abstract fun backupRecordDao(): BackupRecordDao

    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "erasmusmate.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
