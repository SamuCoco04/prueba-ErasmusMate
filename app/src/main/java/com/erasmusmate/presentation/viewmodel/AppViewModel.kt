package com.erasmusmate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erasmusmate.core.error.AppError
import com.erasmusmate.data.repository.RepositoryProvider
import com.erasmusmate.domain.model.User
import com.erasmusmate.domain.model.UserRole
import com.erasmusmate.domain.usecase.LogAuditEventUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AppViewModel(private val repositoryProvider: RepositoryProvider) : ViewModel() {
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    private val logAuditEvent = LogAuditEventUseCase(repositoryProvider.auditRepository)

    val currentUser: StateFlow<User?> = repositoryProvider.userRepository.currentUser()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val role: StateFlow<UserRole?> = currentUser.map { it?.role }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val simulateDbFailure: StateFlow<Boolean> = repositoryProvider.settingsDataStore.simulateDbFailure
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    val universityAuthEnabled: StateFlow<Boolean> = repositoryProvider.settingsDataStore.universityAuthEnabled
        .stateIn(viewModelScope, SharingStarted.Lazily, true)
    val socialAuthEnabled: StateFlow<Boolean> = repositoryProvider.settingsDataStore.socialAuthEnabled
        .stateIn(viewModelScope, SharingStarted.Lazily, false)
    val documentVerificationEnabled: StateFlow<Boolean> = repositoryProvider.settingsDataStore.documentVerificationEnabled
        .stateIn(viewModelScope, SharingStarted.Lazily, false)
    val universityDataExchangeEnabled: StateFlow<Boolean> = repositoryProvider.settingsDataStore.universityDataExchangeEnabled
        .stateIn(viewModelScope, SharingStarted.Lazily, false)
    val academicRecordsEnabled: StateFlow<Boolean> = repositoryProvider.settingsDataStore.academicRecordsEnabled
        .stateIn(viewModelScope, SharingStarted.Lazily, false)
    val notificationGatewayEnabled: StateFlow<Boolean> = repositoryProvider.settingsDataStore.notificationGatewayEnabled
        .stateIn(viewModelScope, SharingStarted.Lazily, false)
    val externalCalendarEnabled: StateFlow<Boolean> = repositoryProvider.settingsDataStore.externalCalendarEnabled
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun loginUniversity(username: String, password: String) {
        viewModelScope.launch {
            val result = repositoryProvider.userRepository.authenticateUniversity(username, password)
            if (result.isFailure) {
                _error.value = result.exceptionOrNull()?.message
            } else {
                logAuditEvent.log(result.getOrNull()?.id ?: \"unknown\", \"login_university\")
            }
        }
    }

    fun loginSocial(token: String) {
        viewModelScope.launch {
            val result = repositoryProvider.userRepository.authenticateSocial(token)
            if (result.isFailure) {
                _error.value = result.exceptionOrNull()?.message
            } else {
                logAuditEvent.log(result.getOrNull()?.id ?: \"unknown\", \"login_social\")
            }
        }
    }

    fun setRole(role: UserRole) {
        viewModelScope.launch {
            val user = currentUser.value ?: User("demo", "Demo", role, "TRK-demo")
            repositoryProvider.userRepository.setCurrentUser(user.copy(role = role))
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun setSimulateDbFailure(enabled: Boolean) {
        viewModelScope.launch {
            repositoryProvider.settingsDataStore.setSimulateDbFailure(enabled)
            _error.value = if (enabled) {
                "db_failure"
            } else {
                null
            }
        }
    }

    fun setUniversityAuthEnabled(enabled: Boolean) = viewModelScope.launch {
        repositoryProvider.settingsDataStore.setUniversityAuthEnabled(enabled)
    }

    fun setSocialAuthEnabled(enabled: Boolean) = viewModelScope.launch {
        repositoryProvider.settingsDataStore.setSocialAuthEnabled(enabled)
    }

    fun setDocumentVerificationEnabled(enabled: Boolean) = viewModelScope.launch {
        repositoryProvider.settingsDataStore.setDocumentVerificationEnabled(enabled)
    }

    fun setUniversityDataExchangeEnabled(enabled: Boolean) = viewModelScope.launch {
        repositoryProvider.settingsDataStore.setUniversityDataExchangeEnabled(enabled)
    }

    fun setAcademicRecordsEnabled(enabled: Boolean) = viewModelScope.launch {
        repositoryProvider.settingsDataStore.setAcademicRecordsEnabled(enabled)
    }

    fun setNotificationGatewayEnabled(enabled: Boolean) = viewModelScope.launch {
        repositoryProvider.settingsDataStore.setNotificationGatewayEnabled(enabled)
    }

    fun setExternalCalendarEnabled(enabled: Boolean) = viewModelScope.launch {
        repositoryProvider.settingsDataStore.setExternalCalendarEnabled(enabled)
    }

    fun errorMessageForDbFailure(): String? {
        return if (simulateDbFailure.value) {
            AppError.DbFailure().message
        } else null
    }
}
