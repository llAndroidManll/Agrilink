package com.app.agrilink.presentation.screens.sign_in

import android.content.Intent
import com.app.agrilink.presentation.base.BaseEvent

sealed class AuthEvents : BaseEvent {

    sealed class Inputs : AuthEvents() {
        data class OnFirstNameChanged(val newValue: String) : Inputs()
        data class OnLastNameChanged(val newValue: String) : Inputs()
        data class OnEmailChanged(val newValue: String) : Inputs()
        data class OnPasswordChanged(val newValue: String) : Inputs()
        data class OnRepeatPasswordChanged(val newValue: String) : Inputs()
        data object ChangePasswordVisibility : Inputs()
        data object ChangeRepeatPasswordVisibility : Inputs()
        data object OnAuthGoogleClicked : Inputs()
        data class OnAuthUserSelected(val intent: Intent) : Inputs()
    }

}