package com.app.agrilink.shared.compose.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.agrilink.R
import com.app.agrilink.shared.compose.utils.SpacerFill
import com.app.agrilink.shared.compose.utils.SpacerHeight
import com.app.agrilink.shared.compose.utils.asPainterResource
import com.app.agrilink.shared.compose.utils.asStringResource


@Composable
fun FullScreenError(onRefreshClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpacerFill()

        Image(
            modifier = Modifier.size(150.dp),
            painter = R.drawable.ic_error_load.asPainterResource(),
            contentDescription = null
        )

        SpacerHeight(height = 32.dp)

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = R.string.state_error_title.asStringResource(),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        SpacerHeight(height = 8.dp)

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = R.string.state_error_subtitle.asStringResource(),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        SpacerFill()

        Box(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(12))
                .background(color = Color.Black)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        onRefreshClick()
                    }
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Center),
                text = R.string.state_error_btn.asStringResource().uppercase(),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        SpacerHeight(height = 16.dp)
    }
}