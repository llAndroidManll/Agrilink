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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.agrilink.data.auth.GoogleAuthUiClient
import com.app.agrilink.data.mappers.toUserData
import com.app.agrilink.domain.data.UserData
import com.app.agrilink.presentation.state.CustomState
import com.app.agrilink.presentation.ui.sign_in.SignInScreen
import com.app.agrilink.presentation.viewmodel.SignInViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
    navController: NavHostController,
    context: Context
) {
    val coroutineScope = rememberCoroutineScope()


    NavHost(navController = navController, startDestination = NavigationScreens.SignIn.route) {

        composable(NavigationScreens.SignIn.route) {
            val signInViewModel: SignInViewModel = viewModel()

            val googleAuthUiClient by lazy {
                GoogleAuthUiClient(
                    context = context,
                    oneTapClient = Identity.getSignInClient(context)
                )
            }

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
                                CustomState<UserData>(
                                    data = signInResult.toUserData(),
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

            SignInScreen(
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