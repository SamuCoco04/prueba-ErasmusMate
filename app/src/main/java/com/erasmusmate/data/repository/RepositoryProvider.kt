package com.erasmusmate.data.repository

import com.erasmusmate.data.datastore.SettingsDataStore
import com.erasmusmate.data.db.AppDatabase
import com.erasmusmate.data.integration.*
import com.erasmusmate.domain.repository.*

class RepositoryProvider(
    database: AppDatabase,
    settings: SettingsDataStore
) {
    val userRepository: UserRepository = UserRepositoryImpl(database, settings, UniversityAuthStub(), SocialAuthStub())
    val applicationRepository: ApplicationRepository = ApplicationRepositoryImpl(database, settings)
    val documentRepository: DocumentRepository = DocumentRepositoryImpl(database, settings)
    val deadlineRepository: DeadlineRepository = DeadlineRepositoryImpl(database, settings)
    val notificationRepository: NotificationRepository = NotificationRepositoryImpl(database, settings)
    val communicationRepository: CommunicationRepository = CommunicationRepositoryImpl(database, settings)
    val auditRepository: AuditRepository = AuditRepositoryImpl(database, settings)
    val adminRepository: AdminRepository = AdminRepositoryImpl(database, settings)

    val documentVerificationService: DocumentVerificationService = DocumentVerificationStub()
    val universityDataExchangeClient: UniversityDataExchangeClient = UniversityDataExchangeStub()
    val academicRecordsVerifier: AcademicRecordsVerifier = AcademicRecordsStub()
    val notificationGateway: NotificationGateway = NotificationGatewayStub()
    val externalCalendarGateway: ExternalCalendarGateway = ExternalCalendarStub()
    val settingsDataStore: SettingsDataStore = settings
}
