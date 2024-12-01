package com.app.agrilink.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.agrilink.data.auth.GoogleAuthUiClient
import com.app.agrilink.data.mappers.toUserData
import com.app.agrilink.domain.data.UserData
import com.app.agrilink.presentation.state.CustomResult
import com.app.agrilink.presentation.state.CustomState
import com.app.agrilink.presentation.state.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/*@HiltViewModel
class SignInViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {*/
class SignInViewModel : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: CustomResult<UserData>) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.value = SignInState()
    }
}
