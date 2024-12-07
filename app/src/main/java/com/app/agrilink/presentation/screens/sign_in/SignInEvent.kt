package com.app.agrilink.presentation.screens.sign_in

import com.app.agrilink.data.entity.SignInDto
import com.app.agrilink.presentation.base.BaseEvent
import com.app.agrilink.shared.compose.state.CustomState

sealed class SignInEvent : BaseEvent {
    data object OnSignInClicked : SignInEvent()
    data class OnSignInResult(val result: CustomState<SignInDto>) : SignInEvent()
}