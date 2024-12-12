package com.app.agrilink.presentation.screens.sign_in


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.agrilink.R
import com.app.agrilink.data.entity.SignInDto
import com.app.agrilink.shared.compose.state.CustomState
import com.app.agrilink.shared.compose.style.BoldGray
import com.app.agrilink.shared.compose.style.Gray
import com.app.agrilink.shared.compose.style.Green
import com.app.agrilink.shared.compose.style.LightGray
import com.app.agrilink.shared.compose.style.Roboto
import com.app.agrilink.shared.compose.style.dimens
import com.app.agrilink.shared.compose.utils.ButtonStyle
import com.app.agrilink.shared.compose.utils.CustomTextField
import com.app.agrilink.shared.compose.utils.LoadingScreen
import com.app.agrilink.shared.compose.utils.ScreenWithBackground
import com.app.agrilink.shared.compose.utils.SpacerHeight
import com.app.agrilink.shared.util.Constants.LOG_TAG

@Composable
fun SignUpScreen(
    state: CustomState<SignInDto> = CustomState(data = SignInDto()),
    onSignInClick: () -> Unit = {},
    onLoginTextClick: () -> Unit = {}
) {

    val context = LocalContext.current

    val firstName = remember {
        mutableStateOf("")
    }

    val lastName = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    val passwordRepeat = remember {
        mutableStateOf("")
    }

    val isPasswordVisible = remember { mutableStateOf(false) }
    val isPasswordRepeatVisible = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf("") }
    val passwordMatchError = remember { mutableStateOf("") }

    LaunchedEffect(key1 = state.error) {
        state.error?.let {
            Log.d(LOG_TAG, "Sign In Screen error")
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }


    if (state.isLoading) {
        LoadingScreen()
    } else {

        ScreenWithBackground {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.dimens.small1),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                Text(
                    text = stringResource(R.string.sign_up),
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Bold,
                    color = Green
                )

                SpacerHeight(MaterialTheme.dimens.small3)

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small1),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CustomTextField(
                        value = firstName.value,
                        onValueChange = {
                            if (it.matches(Regex("^[a-zA-Z]*$"))) {
                                firstName.value = it
                            }
                        },
                        labelValue = stringResource(R.string.first_name),
                        shape = RoundedCornerShape(20),
                    )
                    CustomTextField(
                        value = lastName.value,
                        onValueChange = {
                            if (it.matches(Regex("^[a-zA-Z]*$"))) {
                                lastName.value = it
                            }
                        },
                        labelValue = stringResource(R.string.last_name),
                        shape = RoundedCornerShape(20),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                    )
                    CustomTextField(
                        value = email.value,
                        onValueChange = {
                            email.value = it
                        },
                        labelValue = stringResource(R.string.email),
                        shape = RoundedCornerShape(20),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    )

                    // Password
                    CustomTextField(
                        value = password.value,
                        onValueChange = {
                            password.value = it
                            passwordError.value = validatePassword(it)
                        },
                        labelValue = "Password",
                        shape = RoundedCornerShape(20),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                isPasswordVisible.value = !isPasswordVisible.value
                            }) {
                                Icon(
                                    imageVector = if (isPasswordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (isPasswordVisible.value) "Hide Password" else "Show Password"
                                )
                            }
                        }
                    )

                    if (passwordError.value.isNotEmpty()) {
                        Text(
                            text = passwordError.value,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    // Confirm Password
                    CustomTextField(
                        value = passwordRepeat.value,
                        onValueChange = {
                            passwordRepeat.value = it
                            passwordMatchError.value =
                                if (it != password.value) "Passwords do not match" else ""
                        },
                        labelValue = "Confirm Password",
                        shape = RoundedCornerShape(20),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (isPasswordRepeatVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                isPasswordRepeatVisible.value = !isPasswordRepeatVisible.value
                            }) {
                                Icon(
                                    imageVector = if (isPasswordRepeatVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (isPasswordRepeatVisible.value) "Hide Password" else "Show Password"
                                )
                            }
                        }
                    )

                    if (passwordMatchError.value.isNotEmpty()) {
                        Text(
                            text = passwordMatchError.value,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                SpacerHeight(MaterialTheme.dimens.small3)

                ButtonStyle(
                    text = stringResource(R.string.sign_up),
                    onClick = {
                        if (validateForm(
                                firstName = firstName.value,
                                lastName = lastName.value,
                                email = email.value,
                                password = password.value,
                                passwordRepeat = passwordRepeat.value,
                                passwordError = passwordError.value,
                                passwordMatchError = passwordMatchError.value
                            )
                        ) {
                            Log.d(LOG_TAG, "Form is valid. Proceed with sign-up.")
                        } else {
                            Toast.makeText(
                                context,
                                "Please fix the errors in the form.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    shape = RoundedCornerShape(20),
                    style = MaterialTheme.typography.bodyMedium,
                    containerColor = Green,
                    contentColor = Color.White,
                    textColor = Color.White
                )


                // ---- Or ----
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.dimens.small3),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .width(MaterialTheme.dimens.large)
                            .background(color = Gray)
                    )
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .width(MaterialTheme.dimens.small1)
                    )
                    Text(
                        text = stringResource(R.string.or),
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray,
                    )
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .width(MaterialTheme.dimens.small1)
                    )

                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .width(MaterialTheme.dimens.large)
                            .background(color = Gray)
                    )
                }

                SpacerHeight(MaterialTheme.dimens.small3)

                IconButton(
                    onClick = onSignInClick,
                    modifier = Modifier
                        .border(1.dp, Color.Transparent, RoundedCornerShape(50))
                        .size(50.dp)
                        .shadow(50.dp, RoundedCornerShape(50), clip = false),
                    colors = IconButtonColors(
                        containerColor = LightGray,
                        contentColor = LightGray,
                        disabledContainerColor = BoldGray,
                        disabledContentColor = BoldGray,
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.google),
                        contentDescription = stringResource(
                            R.string.google
                        ),
                        modifier = Modifier.size(32.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onLoginTextClick()
                        }
                        .padding(top = MaterialTheme.dimens.small2),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Already have an account? ",
                        style = MaterialTheme.typography.bodySmall,
                        color = BoldGray
                    )
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.bodySmall,
                        color = Green
                    )
                }
            }
        }
    }
}

fun validatePassword(password: String): String {
    return when {
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
    passwordError: String,
    passwordMatchError: String
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

        passwordError.isNotEmpty() -> {
            Log.d(LOG_TAG, "Password error: $passwordError")
            false
        }

        passwordRepeat.isEmpty() -> {
            Log.d(LOG_TAG, "Confirm password is required.")
            false
        }

        passwordMatchError.isNotEmpty() -> {
            Log.d(LOG_TAG, "Password match error: $passwordMatchError")
            false
        }

        else -> true
    }
}


@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignUpScreen()
}
