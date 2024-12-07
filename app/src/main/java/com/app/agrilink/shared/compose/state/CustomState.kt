package com.app.agrilink.shared.compose.state

import com.app.agrilink.presentation.base.BaseDataState

data class CustomState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val error: String? = null,
) : BaseDataState