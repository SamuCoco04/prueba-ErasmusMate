package com.erasmusmate.data.integration

import com.erasmusmate.domain.repository.*

class UniversityAuthStub : UniversityAuthProvider {
    override suspend fun authenticate(username: String, password: String): Result<String> {
        return if (username.isNotBlank() && password.isNotBlank()) {
            Result.success("user-$username")
        } else {
            Result.failure(IllegalArgumentException("invalid_credentials"))
        }
    }
}

class SocialAuthStub : SocialAuthProvider {
    override suspend fun authenticate(token: String): Result<String> {
        return if (token.isNotBlank()) {
            Result.success("social-$token")
        } else {
            Result.failure(IllegalArgumentException("invalid_token"))
        }
    }
}

class DocumentVerificationStub : DocumentVerificationService {
    override suspend fun verify(documentId: String): Result<Boolean> {
        return Result.success(true)
    }
}

class UniversityDataExchangeStub : UniversityDataExchangeClient {
    override suspend fun fetchStudentData(userId: String): Result<String> {
        return Result.success("stub-data-for-$userId")
    }
}

class AcademicRecordsStub : AcademicRecordsVerifier {
    override suspend fun verifyRecords(userId: String): Result<Boolean> {
        return Result.success(true)
    }
}

class NotificationGatewayStub : NotificationGateway {
    override suspend fun sendEmail(to: String, message: String): Result<Unit> = Result.success(Unit)
    override suspend fun sendSms(to: String, message: String): Result<Unit> = Result.success(Unit)
    override suspend fun sendPush(to: String, message: String): Result<Unit> = Result.success(Unit)
}

class ExternalCalendarStub : ExternalCalendarGateway {
    override suspend fun addDeadline(title: String, epochSeconds: Long): Result<Unit> {
        return Result.success(Unit)
    }
}
