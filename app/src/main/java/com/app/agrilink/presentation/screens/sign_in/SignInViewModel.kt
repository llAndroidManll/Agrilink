package com.app.agrilink.presentation.screens.sign_in

import androidx.lifecycle.ViewModel
import com.app.agrilink.data.auth.GoogleAuthUiClient
import com.app.agrilink.data.entity.SignInDto
import com.app.agrilink.shared.compose.state.CustomState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    private val _state = MutableStateFlow(CustomState<SignInDto>())
    val state = _state.asStateFlow()

    fun onSignInResult(result: CustomState<SignInDto>) {
        _state.update {
            it.copy(
                isSuccessful = result.data != null,
                error = result.error.let { "" }
            )
        }
    }

    fun getGoogleAuthUiClient(): GoogleAuthUiClient = googleAuthUiClient

    fun resetState() {
        _state.value = CustomState<SignInDto>()
    }
}