package com.app.agrilink.data.entity

import com.app.agrilink.domain.mapper.Transformable
import com.app.agrilink.domain.data.UserData
import com.app.agrilink.shared.util.Constants.EMPTY_STRING

data class SignInDto(
    val userId: String? = null,
    val username: String? = null,
    val profilePictureUrl: String? = null,
    val errorMessage: String? = null
) : Transformable<UserData?> {
    override fun transform(): UserData? {
        return if (userId != null) {
            UserData(
                userId = userId,
                username = username ?: EMPTY_STRING,
                profilePictureUrl = profilePictureUrl ?: EMPTY_STRING
            )
        } else {
            null
        }
    }
}