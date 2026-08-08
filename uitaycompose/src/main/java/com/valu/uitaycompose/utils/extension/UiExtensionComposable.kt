package com.valu.uitaycompose.utils.extension

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun isTablet(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.smallestScreenWidthDp >= 600
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun isKeyboardVisible(): Boolean {
    return WindowInsets.isImeVisible
}
