package com.app.agrilink.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.agrilink.presentation.ui.sign_in.SignInScreen
import com.app.agrilink.presentation.viewmodel.SignInViewModel

@Composable
fun AppNavHost(
    navController: NavHostController
) {

    val signInViewModel: SignInViewModel = hiltViewModel()


    NavHost(navController = navController, startDestination = NavigationScreens.SignIn.route) {
        composable(NavigationScreens.HomeScreen.route) {

        }
        composable(NavigationScreens.SignIn.route) {

            val signInState = signInViewModel.state.collectAsState()

            SignInScreen(
                state = signInState.value,
                onSignInClick = {
                    signInViewModel.resetState()
                    navController.navigate(NavigationScreens.HomeScreen.route) {
                        popUpTo(NavigationScreens.SignIn.route) { inclusive = true }
                    }
                }
            )
        }
    }
}