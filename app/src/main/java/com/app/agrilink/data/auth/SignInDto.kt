package com.app.agrilink.data.auth

data class SignInDto(
    val userId: String? = null,
    val username: String? = null,
    val profilePictureUrl: String? = null,
    val errorMessage: String? = null
)