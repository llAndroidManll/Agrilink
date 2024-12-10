package com.app.agrilink.navigation

import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.agrilink.shared.compose.state.CustomState
import com.app.agrilink.presentation.screens.sign_in.SignUpScreen
import com.app.agrilink.presentation.screens.sign_in.SignInViewModel
import com.app.agrilink.presentation.screens.welcome.WelcomeScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
    navController: NavHostController,
    context: Context
) {
    val coroutineScope = rememberCoroutineScope()

    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

    NavHost(navController = navController, startDestination = NavigationScreens.Welcome.route) {

        composable(NavigationScreens.Welcome.route) {
            WelcomeScreen()
            LaunchedEffect(
                key1 = lifecycleScope
            ) {
                delay(1000)
                navController.navigate(NavigationScreens.SignIn.route)
            }
        }

        composable(NavigationScreens.SignIn.route) {

            val signInViewModel: SignInViewModel = hiltViewModel()

            val googleAuthUiClient = signInViewModel.getGoogleAuthUiClient()

            val signInState = signInViewModel.state.collectAsState()
            val state = signInState.value

            LaunchedEffect(key1 = Unit) {
                if(googleAuthUiClient.getSignedInUser() != null) {
                    navController.navigate(NavigationScreens.HomeScreen.route)
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if(result.resultCode == RESULT_OK) {
                        coroutineScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            signInViewModel.onSignInResult(
                                CustomState(
                                    data = signInResult,
                                )
                            )
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSuccessful) {
                if(state.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate(NavigationScreens.HomeScreen.route)
                    signInViewModel.resetState()
                }
            }

            SignUpScreen(
                state = state,
                onSignInClick = {
                    coroutineScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
        }

        composable(NavigationScreens.HomeScreen.route) {

        }
    }
}