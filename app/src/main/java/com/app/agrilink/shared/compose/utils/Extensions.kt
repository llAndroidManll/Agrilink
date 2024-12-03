package com.app.agrilink.shared.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun Int.asStringResource() = stringResource(id = this)

@Composable
fun Int.asPainterResource() = painterResource(id = this)