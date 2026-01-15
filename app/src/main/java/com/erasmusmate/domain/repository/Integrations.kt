package com.erasmusmate.domain.repository

interface UniversityAuthProvider {
    suspend fun authenticate(username: String, password: String): Result<String>
}

interface SocialAuthProvider {
    suspend fun authenticate(token: String): Result<String>
}

interface DocumentVerificationService {
    suspend fun verify(documentId: String): Result<Boolean>
}

interface UniversityDataExchangeClient {
    suspend fun fetchStudentData(userId: String): Result<String>
}

interface AcademicRecordsVerifier {
    suspend fun verifyRecords(userId: String): Result<Boolean>
}

interface NotificationGateway {
    suspend fun sendEmail(to: String, message: String): Result<Unit>
    suspend fun sendSms(to: String, message: String): Result<Unit>
    suspend fun sendPush(to: String, message: String): Result<Unit>
}

interface ExternalCalendarGateway {
    suspend fun addDeadline(title: String, epochSeconds: Long): Result<Unit>
}
