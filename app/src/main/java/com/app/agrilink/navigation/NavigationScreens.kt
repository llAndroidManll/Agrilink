package com.app.agrilink.navigation

sealed class NavigationScreens(
    val route: String,
) {
    data object HomeScreen : NavigationScreens("home_screen")
}