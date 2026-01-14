package com.erasmusmate

import com.erasmusmate.core.error.AppError
import com.erasmusmate.core.util.DbFailureToggle
import com.erasmusmate.data.repository.ensureDbAvailable
import com.erasmusmate.domain.model.Document
import com.erasmusmate.domain.repository.AuditRepository
import com.erasmusmate.domain.usecase.LogAuditEventUseCase
import com.erasmusmate.domain.usecase.ValidateDocumentUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UseCaseTests {
    @Test
    fun `document validation rejects unsupported format`() {
        val useCase = ValidateDocumentUseCase()
        val result = useCase.execute(
            Document(
                id = "1",
                applicationId = "app",
                type = "transcript",
                fileName = "file.exe",
                status = "NEW",
                errorMessage = null
            )
        )
        assertTrue(result.isFailure)
    }

    @Test
    fun `audit logging creates event`() = runBlocking {
        val fakeRepo = FakeAuditRepository()
        val useCase = LogAuditEventUseCase(fakeRepo)
        useCase.log("actor", "action")
        assertEquals(1, fakeRepo.eventsLogged)
    }

    @Test(expected = AppError.DbFailure::class)
    fun `db failure handling throws error`() = runBlocking {
        val toggle = FakeDbFailureToggle(true)
        toggle.ensureDbAvailable()
    }
}

private class FakeAuditRepository : AuditRepository {
    var eventsLogged = 0
    override fun events(): Flow<List<com.erasmusmate.domain.model.AuditEvent>> = MutableStateFlow(emptyList())
    override suspend fun logEvent(event: com.erasmusmate.domain.model.AuditEvent) {
        eventsLogged += 1
    }
}

private class FakeDbFailureToggle(initial: Boolean) : DbFailureToggle {
    private val flow = MutableStateFlow(initial)
    override val simulateDbFailure: Flow<Boolean> = flow
}
