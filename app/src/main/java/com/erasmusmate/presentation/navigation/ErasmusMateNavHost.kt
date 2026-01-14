package com.erasmusmate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.erasmusmate.data.repository.RepositoryProvider
import com.erasmusmate.presentation.screens.*
import com.erasmusmate.presentation.viewmodel.AppViewModel

@Composable
fun ErasmusMateNavHost(repositoryProvider: RepositoryProvider) {
    val navController = rememberNavController()
    val viewModel = remember { AppViewModel(repositoryProvider) }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(viewModel = viewModel) { navController.navigate("home") }
        }
        composable("home") {
            HomeScreen(viewModel = viewModel) { route -> navController.navigate(route) }
        }
        composable("language") {
            LanguageScreen(onLanguageSelected = { /* TODO: apply locale change */ })
        }
        composable("help") { HelpScreen() }
        composable("faq") { FaqScreen() }
        composable("feedback") { FeedbackScreen() }
        composable("student_dashboard/{trackingCode}", arguments = listOf(navArgument("trackingCode") { type = NavType.StringType })) {
            val code = it.arguments?.getString("trackingCode") ?: "TRK-demo"
            StudentDashboardScreen(trackingCode = code)
        }
        composable("application_edit") { ApplicationEditScreen() }
        composable("documents") { DocumentsScreen() }
        composable("deadlines") { DeadlinesScreen() }
        composable("notifications") { NotificationPreferencesScreen() }
        composable("submission_history") { SubmissionHistoryScreen() }
        composable("messaging") { MessagingScreen() }
        composable("appeals") { AppealsScreen() }
        composable("gdpr") { GdprScreen() }
        composable("coordinator_queue") { CoordinatorQueueScreen() }
        composable("coordinator_review") { CoordinatorReviewScreen() }
        composable("coordinator_deadlines") { CoordinatorDeadlinesScreen() }
        composable("coordinator_reports") { CoordinatorReportsScreen() }
        composable("admin_dashboard") { AdminDashboardScreen(viewModel) }
        composable("admin_workflow") { AdminWorkflowScreen() }
        composable("admin_bulk_update") { AdminBulkUpdateScreen() }
        composable("admin_stats") { AdminStatsScreen() }
        composable("admin_provisioning_audit") { AdminProvisioningAuditScreen() }
        composable("admin_backup") { AdminBackupScreen() }
        composable("admin_health") { AdminHealthScreen() }
    }
}
