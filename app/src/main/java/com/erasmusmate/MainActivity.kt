package com.erasmusmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.erasmusmate.presentation.navigation.ErasmusMateNavHost
import com.erasmusmate.presentation.ui.ErasmusMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repositoryProvider = (application as ErasmusMateApp).repositoryProvider
        setContent {
            ErasmusMateTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ErasmusMateNavHost(repositoryProvider = repositoryProvider)
                }
            }
        }
    }
}
