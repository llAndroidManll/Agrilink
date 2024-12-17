package com.app.agrilink.domain.data


sealed class OnAuth {
    data class Success(val userData: UserData) : OnAuth()
    data class Error(val error: Exception) : OnAuth()
}
