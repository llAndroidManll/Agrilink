package com.app.agrilink.data.entity

data class SignInDto(
    val userId: String?,
    val username: String?,
    val profilePictureUrl: String?,
    val errorMessage: String?
)