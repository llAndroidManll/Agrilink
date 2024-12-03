package com.app.agrilink.data.mappers

import com.app.agrilink.data.auth.SignInDto
import com.app.agrilink.domain.data.UserData

fun SignInDto.toUserData(): UserData? {
    return if (userId != null) {
        UserData(
            userId = userId,
            username = username,
            profilePictureUrl = profilePictureUrl
        )
    } else {
        null
    }
}