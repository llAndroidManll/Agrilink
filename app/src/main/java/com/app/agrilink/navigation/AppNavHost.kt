package com.app.agrilink.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(
    navController: NavHostController
) {


    NavHost(navController = navController, startDestination = NavigationScreens.HomeScreen.route) {
        composable(NavigationScreens.HomeScreen.route) {

        }
    }
}