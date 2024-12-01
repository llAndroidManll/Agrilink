package com.app.agrilink.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.app.agrilink.navigation.AppNavHost
import com.app.agrilink.shared.compose.style.AgrilinkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AgrilinkTheme{
                AppNavHost(
                    navController = navController,
                    context = applicationContext
                )
            }
        }
    }
}