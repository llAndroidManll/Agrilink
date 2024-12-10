package com.app.agrilink.shared.compose.utils


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun ButtonStyle(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String = "",
    style: TextStyle = MaterialTheme.typography.displayLarge,
    textColor: Color = Color.Black,
    shape: RoundedCornerShape = RoundedCornerShape(10),
    borderStroke: BorderStroke? = null,
    contentColor: Color = Color.White,
    containerColor: Color = Color.Transparent
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        border = borderStroke,
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColor,
            containerColor = containerColor
        ),
    ) {
        Text(text = text, style = style, color = textColor)
    }
}