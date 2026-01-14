package com.erasmusmate

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import com.erasmusmate.data.db.AppDatabase
import com.erasmusmate.data.datastore.SettingsDataStore
import com.erasmusmate.data.repository.RepositoryProvider
import com.erasmusmate.data.work.DeadlineReminderWorkerFactory
import com.erasmusmate.data.work.SystemHealthWorkerFactory
import com.erasmusmate.data.work.DeadlineReminderWorker
import com.erasmusmate.data.work.SystemHealthCheckWorker
import java.util.concurrent.TimeUnit

class ErasmusMateApp : Application(), Configuration.Provider {
    lateinit var database: AppDatabase
        private set
    lateinit var settings: SettingsDataStore
        private set
    lateinit var repositoryProvider: RepositoryProvider
        private set

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.create(this)
        settings = SettingsDataStore(this)
        repositoryProvider = RepositoryProvider(database, settings)
        WorkManager.initialize(
            this,
            Configuration.Builder()
                .setWorkerFactory(
                    RepositoryProviderWorkerFactory(repositoryProvider)
                )
                .build()
        )
        scheduleBackgroundWork()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().build()

    private fun scheduleBackgroundWork() {
        val deadlineRequest = PeriodicWorkRequestBuilder<DeadlineReminderWorker>(1, TimeUnit.DAYS).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "deadline_reminders",
            ExistingPeriodicWorkPolicy.UPDATE,
            deadlineRequest
        )
        val healthRequest = PeriodicWorkRequestBuilder<SystemHealthCheckWorker>(12, TimeUnit.HOURS).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "system_health_checks",
            ExistingPeriodicWorkPolicy.UPDATE,
            healthRequest
        )
    }
}

class RepositoryProviderWorkerFactory(
    private val repositoryProvider: RepositoryProvider
) : androidx.work.WorkerFactory() {
    override fun createWorker(
        appContext: android.content.Context,
        workerClassName: String,
        workerParameters: androidx.work.WorkerParameters
    ): androidx.work.ListenableWorker? {
        return when (workerClassName) {
            com.erasmusmate.data.work.DeadlineReminderWorker::class.java.name ->
                DeadlineReminderWorkerFactory(repositoryProvider)
                    .create(appContext, workerParameters)
            com.erasmusmate.data.work.SystemHealthCheckWorker::class.java.name ->
                SystemHealthWorkerFactory(repositoryProvider)
                    .create(appContext, workerParameters)
            else -> null
        }
    }
}
