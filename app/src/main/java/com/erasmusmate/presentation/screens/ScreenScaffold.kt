package com.erasmusmate.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.erasmusmate.R

@Composable
fun ScreenScaffold(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(topBar = { TopAppBar(title = { Text(title) }) }) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            content()
        }
    }
}

@Composable
fun DbFailureBanner(show: Boolean) {
    if (show) {
        Surface(color = MaterialTheme.colorScheme.errorContainer) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = stringResource(id = R.string.db_failure_title))
                Text(text = stringResource(id = R.string.db_failure_message))
            }
        }
    }
}

@Composable
fun SimpleActionButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text(text)
    }
}

@Composable
fun EmptyState(text: String) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
        Text(text)
    }
}
