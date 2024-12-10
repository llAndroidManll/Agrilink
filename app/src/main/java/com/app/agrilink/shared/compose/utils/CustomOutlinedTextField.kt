package com.app.agrilink.shared.compose.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.app.agrilink.shared.compose.style.Roboto
import com.app.agrilink.shared.compose.style.dimens

@Composable
fun CustomOutlineTextField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    labelValue: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    maxLines: Int = 10,
    minLines: Int = 1,
    shape: RoundedCornerShape = RoundedCornerShape(20)
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = labelValue,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = Roboto,
                fontWeight = FontWeight.Light
            )
        },
        modifier = Modifier
            /*.padding(
                top = MaterialTheme.dimens.small2,
                start = MaterialTheme.dimens.small1,
                end = MaterialTheme.dimens.small1,
                bottom = MaterialTheme.dimens.small2,
            )*/
        ,
        textStyle = MaterialTheme.typography.bodyMedium,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        maxLines = maxLines,
        minLines = minLines,
        shape = shape,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedLabelColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            unfocusedTextColor = Color.Black,
        ),

    )
}