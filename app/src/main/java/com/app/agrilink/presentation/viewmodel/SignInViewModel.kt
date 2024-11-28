package com.app.agrilink.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.agrilink.data.auth.GoogleAuthUiClient
import com.app.agrilink.data.mappers.toUserData
import com.app.agrilink.domain.data.UserData
import com.app.agrilink.presentation.state.CustomState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*@HiltViewModel
class SignInViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {*/
class SignInViewModel constructor(
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    private val _state = MutableStateFlow(CustomState<UserData>())
    val state = _state.asStateFlow()

    fun signIn(intent: android.content.Intent) {
        _state.value = CustomState(isLoading = true)
        viewModelScope.launch {
            try {
                val result = googleAuthUiClient.signInWithIntent(intent)
                val userData = result.toUserData()
                if (userData != null) {
                    _state.value = CustomState(data = userData)
                } else {
                    _state.value = CustomState(error = "Sign-in failed.")
                }
            } catch (e: Exception) {
                _state.value = CustomState(error = e.message ?: "Unknown error")
            }
        }
    }

    fun resetState() {
        _state.value = CustomState()
    }
}
