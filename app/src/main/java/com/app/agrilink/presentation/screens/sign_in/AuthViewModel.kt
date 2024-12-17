package com.app.agrilink.presentation.screens.sign_in

import androidx.navigation.NavHostController
import com.app.agrilink.data.auth.GoogleAuthUiClient
import com.app.agrilink.domain.useCases.OnAuthUseCase
import com.app.agrilink.navigation.NavigationScreens
import com.app.agrilink.presentation.base.BaseEvent
import com.app.agrilink.presentation.base.BaseViewModel
import com.app.agrilink.presentation.base.LifecycleUIEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = AuthViewModel.Factory::class)
class AuthViewModel @AssistedInject constructor(
    @Assisted private val navHostController: NavHostController,
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val onAuthUseCase: OnAuthUseCase
) : BaseViewModel<AuthState>() {

    @AssistedFactory
    interface Factory {
        fun create(navHostController: NavHostController): AuthViewModel
    }

    override fun createDefaultDataState(): AuthState {
        return AuthState()
    }

    override fun reduce(event: BaseEvent) {
        when (event) {
            is LifecycleUIEvent -> super.reduce(event)
            is AuthEvents -> reduce(event)
        }
    }

    private fun reduce(event: AuthEvents) {
        when (event) {
            AuthEvents.Inputs.ChangePasswordVisibility -> {
                updateDataWithState { state -> state.copy(isPasswordVisible = !state.isPasswordVisible) }
            }

            AuthEvents.Inputs.ChangeRepeatPasswordVisibility -> {
                updateDataWithState { state -> state.copy(isRepeatPasswordVisible = !state.isRepeatPasswordVisible) }
            }

            is AuthEvents.Inputs.OnEmailChanged -> {
                updateDataWithState { state -> state.copy(email = event.newValue) }
            }

            is AuthEvents.Inputs.OnFirstNameChanged -> {
                updateDataWithState { state -> state.copy(firstName = event.newValue) }
            }

            is AuthEvents.Inputs.OnLastNameChanged -> {
                updateDataWithState { state -> state.copy(lastName = event.newValue) }
            }

            is AuthEvents.Inputs.OnPasswordChanged -> {
                updateDataWithState { state -> state.copy(password = event.newValue) }
            }

            is AuthEvents.Inputs.OnRepeatPasswordChanged -> {
                updateDataWithState { state -> state.copy(repeatPassword = event.newValue) }
            }

            is AuthEvents.Inputs.OnAuthUserSelected -> handleOnAuthUserSelected(event)

            AuthEvents.Inputs.OnAuthGoogleClicked -> handleOnAuthGoogleClicked()
        }
    }

    private fun handleOnAuthUserSelected(event: AuthEvents.Inputs.OnAuthUserSelected) {
        createRequest {
            onAuthUseCase.invoke(event.intent)
        }.launch(onData = {
            navHostController.navigate(NavigationScreens.HomeScreen.route)
        })
    }

    private fun handleOnAuthGoogleClicked() {
        createRequest {
            googleAuthUiClient.signIn()
        }.launch {
            updateDataWithState { state ->
                state.copy(intentSender = it)
            }
        }
    }
}