package com.erasmusmate.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) : com.erasmusmate.core.util.DbFailureToggle {
    private val roleKey = stringPreferencesKey("role")
    private val languageKey = stringPreferencesKey("language")
    private val simulateDbFailureKey = booleanPreferencesKey("simulate_db_failure")
    private val universityAuthEnabledKey = booleanPreferencesKey("university_auth_enabled")
    private val socialAuthEnabledKey = booleanPreferencesKey("social_auth_enabled")
    private val documentVerificationEnabledKey = booleanPreferencesKey("document_verification_enabled")
    private val universityDataExchangeEnabledKey = booleanPreferencesKey("university_data_exchange_enabled")
    private val academicRecordsEnabledKey = booleanPreferencesKey("academic_records_enabled")
    private val notificationGatewayEnabledKey = booleanPreferencesKey("notification_gateway_enabled")
    private val externalCalendarEnabledKey = booleanPreferencesKey("external_calendar_enabled")

    val role: Flow<String?> = context.dataStore.data.map { it[roleKey] }
    val language: Flow<String?> = context.dataStore.data.map { it[languageKey] }
    override val simulateDbFailure: Flow<Boolean> = context.dataStore.data.map { it[simulateDbFailureKey] ?: false }
    val universityAuthEnabled: Flow<Boolean> = context.dataStore.data.map { it[universityAuthEnabledKey] ?: true }
    val socialAuthEnabled: Flow<Boolean> = context.dataStore.data.map { it[socialAuthEnabledKey] ?: false }
    val documentVerificationEnabled: Flow<Boolean> = context.dataStore.data.map { it[documentVerificationEnabledKey] ?: false }
    val universityDataExchangeEnabled: Flow<Boolean> = context.dataStore.data.map { it[universityDataExchangeEnabledKey] ?: false }
    val academicRecordsEnabled: Flow<Boolean> = context.dataStore.data.map { it[academicRecordsEnabledKey] ?: false }
    val notificationGatewayEnabled: Flow<Boolean> = context.dataStore.data.map { it[notificationGatewayEnabledKey] ?: false }
    val externalCalendarEnabled: Flow<Boolean> = context.dataStore.data.map { it[externalCalendarEnabledKey] ?: false }

    suspend fun updateRole(role: String?) {
        updateValue(roleKey, role)
    }

    suspend fun updateLanguage(language: String) {
        updateValue(languageKey, language)
    }

    suspend fun setSimulateDbFailure(enabled: Boolean) {
        updateValue(simulateDbFailureKey, enabled)
    }

    suspend fun setUniversityAuthEnabled(enabled: Boolean) {
        updateValue(universityAuthEnabledKey, enabled)
    }

    suspend fun setSocialAuthEnabled(enabled: Boolean) {
        updateValue(socialAuthEnabledKey, enabled)
    }

    suspend fun setDocumentVerificationEnabled(enabled: Boolean) {
        updateValue(documentVerificationEnabledKey, enabled)
    }

    suspend fun setUniversityDataExchangeEnabled(enabled: Boolean) {
        updateValue(universityDataExchangeEnabledKey, enabled)
    }

    suspend fun setAcademicRecordsEnabled(enabled: Boolean) {
        updateValue(academicRecordsEnabledKey, enabled)
    }

    suspend fun setNotificationGatewayEnabled(enabled: Boolean) {
        updateValue(notificationGatewayEnabledKey, enabled)
    }

    suspend fun setExternalCalendarEnabled(enabled: Boolean) {
        updateValue(externalCalendarEnabledKey, enabled)
    }

    private suspend fun <T> updateValue(key: Preferences.Key<T>, value: T?) {
        context.dataStore.edit { preferences ->
            if (value == null) {
                preferences.remove(key)
            } else {
                preferences[key] = value
            }
        }
    }
}
