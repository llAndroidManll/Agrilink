package com.app.agrilink.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.app.agrilink.domain.data.UserData
import com.app.agrilink.presentation.state.CustomState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class SignInViewModel : ViewModel() {

    private val _state = MutableStateFlow(CustomState<Any>())
    val state = _state.asStateFlow()

    fun onSignInResult(result: CustomState<UserData>) {
        _state.update { it.copy(
            isSuccessful = result.data != null,
            error = result.error.let { "" }
        ) }
    }

    fun resetState() {
        _state.value = CustomState<Any>()
    }
}
