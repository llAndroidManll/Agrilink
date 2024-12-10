package com.app.agrilink.shared.compose.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.app.agrilink.shared.compose.style.BoldGray
import com.app.agrilink.shared.compose.style.Green
import com.app.agrilink.shared.compose.style.Roboto

@Composable
fun CustomTextField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    labelValue: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    maxLines: Int = 10,
    minLines: Int = 1,
    shape: RoundedCornerShape = RoundedCornerShape(20),
    modifier: Modifier = Modifier,
    placeholder: String = "",
    trailingIcon: @Composable (() -> Unit) = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = Roboto,
                fontWeight = FontWeight.Light,
                color = BoldGray
            )
        },
        label = {
            Text(
                text = labelValue,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = Roboto,
                fontWeight = FontWeight.Light
            )
        },
        modifier = modifier,
        textStyle = MaterialTheme.typography.bodyMedium,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        maxLines = maxLines,
        minLines = minLines,
        shape = shape,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Green, // Set the focused underline color
            unfocusedIndicatorColor = Color.Gray, // Set the unfocused underline color
            focusedLabelColor = Green,
            unfocusedLabelColor = Color.DarkGray,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation
    )
}