package com.app.agrilink.shared.compose.state

data class CustomState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val error: String? = null,
)