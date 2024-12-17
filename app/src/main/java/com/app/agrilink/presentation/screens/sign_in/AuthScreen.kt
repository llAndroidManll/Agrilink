package com.app.agrilink.presentation.screens.sign_in


import android.app.Activity.RESULT_OK
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.agrilink.R
import com.app.agrilink.presentation.base.BaseScreen
import com.app.agrilink.shared.compose.style.BoldGray
import com.app.agrilink.shared.compose.style.Gray
import com.app.agrilink.shared.compose.style.Green
import com.app.agrilink.shared.compose.style.LightGray
import com.app.agrilink.shared.compose.style.Roboto
import com.app.agrilink.shared.compose.style.dimens
import com.app.agrilink.shared.compose.utils.ButtonStyle
import com.app.agrilink.shared.compose.utils.CustomTextField
import com.app.agrilink.shared.compose.utils.LoadingScreen
import com.app.agrilink.shared.compose.component.ScreenWithBackground
import com.app.agrilink.shared.compose.utils.SpacerHeight
import com.app.agrilink.shared.compose.utils.validateForm
import com.app.agrilink.shared.util.Constants.LOG_TAG

@Composable
fun AuthScreen(
    navHostController: NavHostController,
    viewModel: AuthViewModel = createAuthViewModel(navHostController),
    onLoginTextClick: () -> Unit = {}
) {
    BaseScreen(
        viewModel = viewModel,
        error = null
    ) { state, loading ->
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { result ->
                val data = result.data
                if (result.resultCode == RESULT_OK && data != null) {
                    viewModel.onUIEvent(AuthEvents.Inputs.OnAuthUserSelected(data))
                }
            }
        )

        LaunchedEffect(key1 = state.intentSender) {
            launcher.launch(
                IntentSenderRequest.Builder(
                    state.intentSender ?: return@LaunchedEffect
                ).build()
            )
        }

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
                        value = state.firstName,
                        onValueChange = {
                            if (it.matches(Regex("^[a-zA-Z]*$"))) {
                                viewModel.onUIEvent(AuthEvents.Inputs.OnFirstNameChanged(it))
                            }
                        },
                        labelValue = stringResource(R.string.first_name),
                        shape = RoundedCornerShape(20),
                    )

                    CustomTextField(
                        value = state.lastName,
                        onValueChange = {
                            if (it.matches(Regex("^[a-zA-Z]*$"))) {
                                viewModel.onUIEvent(AuthEvents.Inputs.OnLastNameChanged(it))
                            }
                        },
                        labelValue = stringResource(R.string.last_name),
                        shape = RoundedCornerShape(20),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                    )

                    CustomTextField(
                        value = state.email,
                        onValueChange = {
                            viewModel.onUIEvent(AuthEvents.Inputs.OnEmailChanged(it))
                        },
                        labelValue = stringResource(R.string.email),
                        shape = RoundedCornerShape(20),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    )

                    // Password
                    CustomTextField(
                        value = state.password,
                        onValueChange = {
                            viewModel.onUIEvent(AuthEvents.Inputs.OnPasswordChanged(it))
                            //Todo teghapoxi clickic heto kam viewModel
                            //passwordError.value = validatePassword(it)
                        },
                        labelValue = "Password",
                        shape = RoundedCornerShape(20),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                viewModel.onUIEvent(AuthEvents.Inputs.ChangePasswordVisibility)
                            }) {
                                Icon(
                                    imageVector = if (state.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (state.isPasswordVisible) "Hide Password" else "Show Password"
                                )
                            }
                        }
                    )

                    if (state.passwordInputError.isNotEmpty()) {
                        Text(
                            text = state.passwordInputError,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    // Confirm Password
                    CustomTextField(
                        value = state.repeatPassword,
                        onValueChange = {
                            viewModel.onUIEvent(AuthEvents.Inputs.OnRepeatPasswordChanged(it))

                            //Todo teghapoxi click-ic heto, kam el viewModel-um stugi
                            /*passwordMatchError.value =
                                if (it != password.value) "Passwords do not match" else ""*/
                        },
                        labelValue = "Confirm Password",
                        shape = RoundedCornerShape(20),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (state.isRepeatPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                viewModel.onUIEvent(AuthEvents.Inputs.ChangeRepeatPasswordVisibility)
                            }) {
                                Icon(
                                    imageVector = if (state.isRepeatPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (state.isRepeatPasswordVisible) "Hide Password" else "Show Password"
                                )
                            }
                        }
                    )

                    if (state.passwordMatchError.isNotEmpty()) {
                        Text(
                            text = state.passwordMatchError,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                SpacerHeight(MaterialTheme.dimens.small3)

                ButtonStyle(
                    text = stringResource(R.string.sign_up),
                    onClick = {
                        if (
                            validateForm(
                                firstName = state.firstName,
                                lastName = state.lastName,
                                email = state.email,
                                password = state.password,
                                passwordRepeat = state.repeatPassword,
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
                    onClick = {
                        viewModel.onUIEvent(AuthEvents.Inputs.OnAuthGoogleClicked)
                    },
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

            if (loading) LoadingScreen()

        }
    }
}

@Composable
private fun createAuthViewModel(navHostController: NavHostController) =
    hiltViewModel<AuthViewModel, AuthViewModel.Factory>(creationCallback = { factory ->
        factory.create(navHostController)
    })

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    AuthScreen(navHostController = NavHostController(LocalContext.current))
}
