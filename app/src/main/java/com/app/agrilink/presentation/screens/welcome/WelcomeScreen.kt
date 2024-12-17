package com.app.agrilink.presentation.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.agrilink.R
import com.app.agrilink.presentation.base.BaseScreen
import com.app.agrilink.shared.compose.style.dimens
import com.app.agrilink.shared.compose.utils.AnimatedText
import com.app.agrilink.shared.compose.component.ScreenWithBackground
import com.app.agrilink.shared.compose.utils.SpacerHeight

private const val WelcomeText = "AGRILINK"

@Composable
fun WelcomeScreen(
    navHostController: NavHostController,
    viewModel: WelcomeViewModel = createWelcomeViewModel(navHostController)
) {
    BaseScreen(viewModel = viewModel) { _, _ ->
        ScreenWithBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.icon),
                    contentDescription = "Icon",
                    modifier = Modifier.size(MaterialTheme.dimens.logoSize)
                )
                SpacerHeight(MaterialTheme.dimens.small3)
                AnimatedText(listOf(WelcomeText))
            }
        }
    }
}

@Composable
private fun createWelcomeViewModel(navHostController: NavHostController) =
    hiltViewModel<WelcomeViewModel, WelcomeViewModel.Factory>(creationCallback = { factory ->
        factory.create(navHostController)
    })

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(navHostController = NavHostController(LocalContext.current))
}