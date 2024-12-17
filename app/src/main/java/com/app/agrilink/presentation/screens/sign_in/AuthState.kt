package com.app.agrilink.presentation.screens.sign_in

import android.content.IntentSender
import com.app.agrilink.presentation.base.BaseDataState
import com.app.agrilink.shared.util.Constants.EMPTY_STRING

data class AuthState(
    val firstName: String = EMPTY_STRING,
    val lastName: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val password: String = EMPTY_STRING,
    val repeatPassword: String = EMPTY_STRING,
    val isPasswordVisible: Boolean = false,
    val isRepeatPasswordVisible: Boolean = false,
    val passwordInputError: String = EMPTY_STRING,
    val passwordMatchError: String = EMPTY_STRING,
    val intentSender: IntentSender? = null
) : BaseDataState


/*
*
* val firstName = remember {
        mutableStateOf("")
    }

    val lastName = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    val passwordRepeat = remember {
        mutableStateOf("")
    }

    val isPasswordVisible = remember { mutableStateOf(false) }
    val isPasswordRepeatVisible = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf("") }
    val passwordMatchError = remember { mutableStateOf("") }*/