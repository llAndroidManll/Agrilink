package com.app.agrilink.navigation

sealed class NavigationScreens(
    val route: String,
) {
    data object HomeScreen : NavigationScreens("home_screen")
    data object AuthScreen : NavigationScreens("auth_screen")
    data object WelcomeScreen : NavigationScreens("welcome_screen")
}