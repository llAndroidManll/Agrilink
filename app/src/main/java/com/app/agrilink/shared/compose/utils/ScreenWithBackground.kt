package com.app.agrilink.shared.compose.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.app.agrilink.R

@Composable
fun ScreenWithBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = modifier.then(Modifier.fillMaxSize())
        )

        content()
    }
}