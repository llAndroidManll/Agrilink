package com.app.agrilink.presentation.dto

import com.app.agrilink.shared.util.Constants.EMPTY_STRING

data class SignInUiModel(
    val userId: String = EMPTY_STRING,
    val username: String = EMPTY_STRING,
    val profilePictureUrl: String = EMPTY_STRING,
)