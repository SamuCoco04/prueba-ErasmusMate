package com.erasmusmate.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.erasmusmate.R
import com.erasmusmate.domain.model.UserRole
import com.erasmusmate.presentation.viewmodel.AppViewModel

@Composable
fun LoginScreen(viewModel: AppViewModel, onNavigateHome: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var socialToken by remember { mutableStateOf("") }
    val error by viewModel.error.collectAsState()
    val dbFailure by viewModel.simulateDbFailure.collectAsState()

    ScreenScaffold(title = stringResource(id = R.string.login_title)) {
        DbFailureBanner(show = dbFailure)
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text(stringResource(id = R.string.username)) })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text(stringResource(id = R.string.password)) })
        SimpleActionButton(text = stringResource(id = R.string.login_university)) {
            viewModel.loginUniversity(username, password)
            onNavigateHome()
        }
        OutlinedTextField(value = socialToken, onValueChange = { socialToken = it }, label = { Text(stringResource(id = R.string.social_token)) })
        SimpleActionButton(text = stringResource(id = R.string.login_social)) {
            viewModel.loginSocial(socialToken)
            onNavigateHome()
        }
        if (error != null) {
            Text(text = stringResource(id = R.string.error_generic))
        }
    }
}

@Composable
fun LanguageScreen(onLanguageSelected: (String) -> Unit) {
    ScreenScaffold(title = stringResource(id = R.string.language_title)) {
        SimpleActionButton(text = stringResource(id = R.string.language_en)) { onLanguageSelected("en") }
        SimpleActionButton(text = stringResource(id = R.string.language_es)) { onLanguageSelected("es") }
    }
}

@Composable
fun HelpScreen() {
    ScreenScaffold(title = stringResource(id = R.string.help_title)) {
        Text(text = stringResource(id = R.string.help_body))
    }
}

@Composable
fun FaqScreen() {
    ScreenScaffold(title = stringResource(id = R.string.faq_title)) {
        Text(text = stringResource(id = R.string.faq_body))
        Text(text = stringResource(id = R.string.support_resources))
    }
}

@Composable
fun FeedbackScreen() {
    var feedback by remember { mutableStateOf("") }
    ScreenScaffold(title = stringResource(id = R.string.feedback_title)) {
        OutlinedTextField(value = feedback, onValueChange = { feedback = it }, label = { Text(stringResource(id = R.string.feedback_hint)) })
        SimpleActionButton(text = stringResource(id = R.string.submit_feedback)) {
            // TODO: persist feedback to local storage if required by future requirements.
        }
    }
}

@Composable
fun StudentDashboardScreen(trackingCode: String) {
    ScreenScaffold(title = stringResource(id = R.string.student_dashboard_title)) {
        Text(text = stringResource(id = R.string.tracking_code, trackingCode))
        Text(text = stringResource(id = R.string.progress_status))
    }
}

@Composable
fun ApplicationEditScreen() {
    ScreenScaffold(title = stringResource(id = R.string.application_edit_title)) {
        Text(text = stringResource(id = R.string.application_edit_body))
        SimpleActionButton(text = stringResource(id = R.string.save_changes)) {
            // TODO: implement application save as per requirements.
        }
    }
}

@Composable
fun DocumentsScreen() {
    ScreenScaffold(title = stringResource(id = R.string.documents_title)) {
        Text(text = stringResource(id = R.string.documents_body))
        SimpleActionButton(text = stringResource(id = R.string.upload_document)) {
            // TODO: implement secure file picker and encryption storage.
        }
    }
}

@Composable
fun DeadlinesScreen() {
    ScreenScaffold(title = stringResource(id = R.string.deadlines_title)) {
        Text(text = stringResource(id = R.string.deadlines_body))
        SimpleActionButton(text = stringResource(id = R.string.add_to_calendar)) {
            // TODO: invoke ExternalCalendarGateway stub when enabled.
        }
    }
}

@Composable
fun NotificationPreferencesScreen() {
    ScreenScaffold(title = stringResource(id = R.string.notification_preferences_title)) {
        Text(text = stringResource(id = R.string.notification_preferences_body))
    }
}

@Composable
fun SubmissionHistoryScreen() {
    ScreenScaffold(title = stringResource(id = R.string.submission_history_title)) {
        Text(text = stringResource(id = R.string.submission_history_body))
    }
}

@Composable
fun MessagingScreen() {
    var message by remember { mutableStateOf("") }
    ScreenScaffold(title = stringResource(id = R.string.messaging_title)) {
        OutlinedTextField(value = message, onValueChange = { message = it }, label = { Text(stringResource(id = R.string.message_hint)) })
        SimpleActionButton(text = stringResource(id = R.string.send_message)) {
            // TODO: log communication in repository.
        }
    }
}

@Composable
fun AppealsScreen() {
    ScreenScaffold(title = stringResource(id = R.string.appeals_title)) {
        Text(text = stringResource(id = R.string.appeals_body))
        SimpleActionButton(text = stringResource(id = R.string.submit_appeal)) {
            // TODO: persist appeal locally and log audit.
        }
    }
}

@Composable
fun GdprScreen() {
    ScreenScaffold(title = stringResource(id = R.string.gdpr_title)) {
        Text(text = stringResource(id = R.string.gdpr_body))
        SimpleActionButton(text = stringResource(id = R.string.export_data)) {
            // TODO: implement local export.
        }
        SimpleActionButton(text = stringResource(id = R.string.delete_data)) {
            // TODO: implement local delete.
        }
        SimpleActionButton(text = stringResource(id = R.string.anonymize_data)) {
            // TODO: implement local anonymize.
        }
    }
}

@Composable
fun CoordinatorQueueScreen() {
    ScreenScaffold(title = stringResource(id = R.string.coordinator_queue_title)) {
        Text(text = stringResource(id = R.string.coordinator_queue_body))
    }
}

@Composable
fun CoordinatorReviewScreen() {
    ScreenScaffold(title = stringResource(id = R.string.coordinator_review_title)) {
        Text(text = stringResource(id = R.string.coordinator_review_body))
        SimpleActionButton(text = stringResource(id = R.string.approve_application)) {
            // TODO: update application status and log audit.
        }
        SimpleActionButton(text = stringResource(id = R.string.reject_application)) {
            // TODO: update application status and log audit.
        }
    }
}

@Composable
fun CoordinatorDeadlinesScreen() {
    ScreenScaffold(title = stringResource(id = R.string.coordinator_deadlines_title)) {
        Text(text = stringResource(id = R.string.coordinator_deadlines_body))
    }
}

@Composable
fun CoordinatorReportsScreen() {
    ScreenScaffold(title = stringResource(id = R.string.coordinator_reports_title)) {
        Text(text = stringResource(id = R.string.coordinator_reports_body))
    }
}

@Composable
fun AdminDashboardScreen(viewModel: AppViewModel) {
    val simulateDbFailure by viewModel.simulateDbFailure.collectAsState()
    val universityAuthEnabled by viewModel.universityAuthEnabled.collectAsState()
    val socialAuthEnabled by viewModel.socialAuthEnabled.collectAsState()
    val documentVerificationEnabled by viewModel.documentVerificationEnabled.collectAsState()
    val universityDataExchangeEnabled by viewModel.universityDataExchangeEnabled.collectAsState()
    val academicRecordsEnabled by viewModel.academicRecordsEnabled.collectAsState()
    val notificationGatewayEnabled by viewModel.notificationGatewayEnabled.collectAsState()
    val externalCalendarEnabled by viewModel.externalCalendarEnabled.collectAsState()

    ScreenScaffold(title = stringResource(id = R.string.admin_dashboard_title)) {
        Text(text = stringResource(id = R.string.admin_dashboard_body))
        Text(text = stringResource(id = R.string.integration_toggles_title))
        ToggleRow(
            label = stringResource(id = R.string.toggle_university_auth),
            checked = universityAuthEnabled,
            onToggle = viewModel::setUniversityAuthEnabled
        )
        ToggleRow(
            label = stringResource(id = R.string.toggle_social_auth),
            checked = socialAuthEnabled,
            onToggle = viewModel::setSocialAuthEnabled
        )
        ToggleRow(
            label = stringResource(id = R.string.toggle_document_verification),
            checked = documentVerificationEnabled,
            onToggle = viewModel::setDocumentVerificationEnabled
        )
        ToggleRow(
            label = stringResource(id = R.string.toggle_university_exchange),
            checked = universityDataExchangeEnabled,
            onToggle = viewModel::setUniversityDataExchangeEnabled
        )
        ToggleRow(
            label = stringResource(id = R.string.toggle_academic_records),
            checked = academicRecordsEnabled,
            onToggle = viewModel::setAcademicRecordsEnabled
        )
        ToggleRow(
            label = stringResource(id = R.string.toggle_notification_gateway),
            checked = notificationGatewayEnabled,
            onToggle = viewModel::setNotificationGatewayEnabled
        )
        ToggleRow(
            label = stringResource(id = R.string.toggle_external_calendar),
            checked = externalCalendarEnabled,
            onToggle = viewModel::setExternalCalendarEnabled
        )
        ToggleRow(
            label = stringResource(id = R.string.toggle_simulate_db_failure),
            checked = simulateDbFailure,
            onToggle = viewModel::setSimulateDbFailure
        )
    }
}

@Composable
private fun ToggleRow(label: String, checked: Boolean, onToggle: (Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onToggle)
    }
}

@Composable
fun AdminWorkflowScreen() {
    ScreenScaffold(title = stringResource(id = R.string.admin_workflow_title)) {
        Text(text = stringResource(id = R.string.admin_workflow_body))
    }
}

@Composable
fun AdminBulkUpdateScreen() {
    ScreenScaffold(title = stringResource(id = R.string.admin_bulk_update_title)) {
        Text(text = stringResource(id = R.string.admin_bulk_update_body))
    }
}

@Composable
fun AdminStatsScreen() {
    ScreenScaffold(title = stringResource(id = R.string.admin_stats_title)) {
        Text(text = stringResource(id = R.string.admin_stats_body))
    }
}

@Composable
fun AdminProvisioningAuditScreen() {
    ScreenScaffold(title = stringResource(id = R.string.admin_provisioning_audit_title)) {
        Text(text = stringResource(id = R.string.admin_provisioning_audit_body))
    }
}

@Composable
fun AdminBackupScreen() {
    ScreenScaffold(title = stringResource(id = R.string.admin_backup_title)) {
        Text(text = stringResource(id = R.string.admin_backup_body))
    }
}

@Composable
fun AdminHealthScreen() {
    ScreenScaffold(title = stringResource(id = R.string.admin_health_title)) {
        Text(text = stringResource(id = R.string.admin_health_body))
    }
}

@Composable
fun HomeScreen(viewModel: AppViewModel, onNavigate: (String) -> Unit) {
    val role by viewModel.role.collectAsState()
    val trackingCode = viewModel.currentUser.collectAsState().value?.trackingCode ?: "TRK-demo"
    val simulateDbFailure by viewModel.simulateDbFailure.collectAsState()
    ScreenScaffold(title = stringResource(id = R.string.home_title)) {
        DbFailureBanner(show = simulateDbFailure)
        SimpleActionButton(text = stringResource(id = R.string.switch_student)) { viewModel.setRole(UserRole.STUDENT) }
        SimpleActionButton(text = stringResource(id = R.string.switch_coordinator)) { viewModel.setRole(UserRole.COORDINATOR) }
        SimpleActionButton(text = stringResource(id = R.string.switch_admin)) { viewModel.setRole(UserRole.ADMIN) }
        val roleLabel = when (role) {
            UserRole.STUDENT -> stringResource(id = R.string.role_student)
            UserRole.COORDINATOR -> stringResource(id = R.string.role_coordinator)
            UserRole.ADMIN -> stringResource(id = R.string.role_admin)
            null -> stringResource(id = R.string.role_unknown)
        }
        Text(text = stringResource(id = R.string.current_role, roleLabel))
        when (role) {
            UserRole.STUDENT -> {
                SimpleActionButton(text = stringResource(id = R.string.student_dashboard_title)) { onNavigate("student_dashboard/$trackingCode") }
                SimpleActionButton(text = stringResource(id = R.string.application_edit_title)) { onNavigate("application_edit") }
                SimpleActionButton(text = stringResource(id = R.string.documents_title)) { onNavigate("documents") }
                SimpleActionButton(text = stringResource(id = R.string.deadlines_title)) { onNavigate("deadlines") }
                SimpleActionButton(text = stringResource(id = R.string.notification_preferences_title)) { onNavigate("notifications") }
                SimpleActionButton(text = stringResource(id = R.string.submission_history_title)) { onNavigate("submission_history") }
                SimpleActionButton(text = stringResource(id = R.string.messaging_title)) { onNavigate("messaging") }
                SimpleActionButton(text = stringResource(id = R.string.appeals_title)) { onNavigate("appeals") }
                SimpleActionButton(text = stringResource(id = R.string.gdpr_title)) { onNavigate("gdpr") }
            }
            UserRole.COORDINATOR -> {
                SimpleActionButton(text = stringResource(id = R.string.coordinator_queue_title)) { onNavigate("coordinator_queue") }
                SimpleActionButton(text = stringResource(id = R.string.coordinator_review_title)) { onNavigate("coordinator_review") }
                SimpleActionButton(text = stringResource(id = R.string.coordinator_deadlines_title)) { onNavigate("coordinator_deadlines") }
                SimpleActionButton(text = stringResource(id = R.string.coordinator_reports_title)) { onNavigate("coordinator_reports") }
            }
            UserRole.ADMIN -> {
                SimpleActionButton(text = stringResource(id = R.string.admin_dashboard_title)) { onNavigate("admin_dashboard") }
                SimpleActionButton(text = stringResource(id = R.string.admin_workflow_title)) { onNavigate("admin_workflow") }
                SimpleActionButton(text = stringResource(id = R.string.admin_bulk_update_title)) { onNavigate("admin_bulk_update") }
                SimpleActionButton(text = stringResource(id = R.string.admin_stats_title)) { onNavigate("admin_stats") }
                SimpleActionButton(text = stringResource(id = R.string.admin_provisioning_audit_title)) { onNavigate("admin_provisioning_audit") }
                SimpleActionButton(text = stringResource(id = R.string.admin_backup_title)) { onNavigate("admin_backup") }
                SimpleActionButton(text = stringResource(id = R.string.admin_health_title)) { onNavigate("admin_health") }
            }
            null -> {
                Text(text = stringResource(id = R.string.role_unknown))
            }
        }
        Divider()
        SimpleActionButton(text = stringResource(id = R.string.language_title)) { onNavigate("language") }
        SimpleActionButton(text = stringResource(id = R.string.help_title)) { onNavigate("help") }
        SimpleActionButton(text = stringResource(id = R.string.faq_title)) { onNavigate("faq") }
        SimpleActionButton(text = stringResource(id = R.string.feedback_title)) { onNavigate("feedback") }
    }
}
