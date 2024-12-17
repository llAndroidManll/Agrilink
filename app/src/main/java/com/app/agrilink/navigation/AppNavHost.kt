package com.app.agrilink.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.agrilink.presentation.screens.sign_in.AuthScreen
import com.app.agrilink.presentation.screens.welcome.WelcomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = NavigationScreens.WelcomeScreen.route) {

        composable(NavigationScreens.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }

        composable(NavigationScreens.AuthScreen.route) {
            AuthScreen(navController)
        }

        composable(NavigationScreens.HomeScreen.route) {

        }
    }
}