package com.app.agrilink.presentation.ui.sign_in


import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.app.agrilink.domain.data.UserData
import com.app.agrilink.presentation.state.CustomState

@Composable
fun SignInScreen(
    state: CustomState<UserData>,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current

    // Show error message if any
    LaunchedEffect(key1 = state.error) {
        state.error?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            Text("Signing in...")
        } else {
            Button(onClick = onSignInClick) {
                Text(text = "Sign in")
            }
        }
    }
}
