package com.app.agrilink.shared.compose.utils

import android.util.Log
import com.app.agrilink.shared.util.Constants.LOG_TAG

fun validatePassword(password: String): String {
    return when {
        //Todo vercnel string resource-ic
        password.length < 8 -> "Password must be at least 8 characters long"
        !password.any { it.isUpperCase() } -> "Password must contain at least one uppercase letter"
        !password.any { it.isLowerCase() } -> "Password must contain at least one lowercase letter"
        !password.any { it.isDigit() } -> "Password must contain at least one number"
        !password.any { !it.isLetterOrDigit() } -> "Password must contain at least one special character"
        else -> ""
    }
}

fun validateForm(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    passwordRepeat: String,
): Boolean {
    return when {
        firstName.isEmpty() -> {
            Log.d(LOG_TAG, "First name is required.")
            false
        }

        lastName.isEmpty() -> {
            Log.d(LOG_TAG, "Last name is required.")
            false
        }

        email.isEmpty() -> {
            Log.d(LOG_TAG, "Email is required.")
            false
        }

        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
            Log.d(LOG_TAG, "Invalid email format.")
            false
        }

        password.isEmpty() -> {
            Log.d(LOG_TAG, "Password is required.")
            false
        }

        passwordRepeat.isEmpty() -> {
            Log.d(LOG_TAG, "Confirm password is required.")
            false
        }

        else -> true
    }
}