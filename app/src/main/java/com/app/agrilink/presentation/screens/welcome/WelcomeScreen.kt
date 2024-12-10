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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.app.agrilink.R
import com.app.agrilink.shared.compose.style.dimens
import com.app.agrilink.shared.compose.utils.AnimatedText
import com.app.agrilink.shared.compose.utils.ScreenWithBackground
import com.app.agrilink.shared.compose.utils.SpacerHeight

@Composable
fun WelcomeScreen() =
ScreenWithBackground{
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
        AnimatedText(listOf("AGRILINK"))
    }
}
@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}