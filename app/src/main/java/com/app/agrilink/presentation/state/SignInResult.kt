package com.app.agrilink.presentation.state

import com.app.agrilink.domain.data.UserData

data class CustomResult<T>(
    val data: T?,
    val errorMessage: String = ""
)
