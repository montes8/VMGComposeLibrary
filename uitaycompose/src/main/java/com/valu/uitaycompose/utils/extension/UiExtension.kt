/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.utils.extension

import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.toColorInt
import com.valu.uitaycompose.utils.UI_TAY_ERROR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds
import android.graphics.Color as AndroidColor
import androidx.compose.ui.graphics.Color as ComposeColor

@Composable
fun Modifier.uiTayClickDelay(
    delayMillis: Long = 1000L,
    onAction: () -> Unit
): Modifier = composed {
    val scope = rememberCoroutineScope()

    this.clickable {
        scope.launch {
            delay(delayMillis.milliseconds)
            onAction()
        }
    }
}

fun CoroutineScope.uiTayHandler(
    time: Long = 200,
    func: () -> Unit
) = launch {
    delay(time.milliseconds)
    func()
}


fun uiTayTryCatch(catch: ((String) -> Unit)? = null, func: (() -> Unit)? = null){
    try {  func?.invoke() }catch (e: Exception){
        e.printStackTrace()
        catch?.invoke(e.message?: UI_TAY_ERROR)
    }
}

fun ComponentActivity.uiTaySetTopBarColor(color: Any) {
    val bgColor = when (color) {
        is Int -> ContextCompat.getColor(this, color)
        is String -> color.toColorInt()
        is ComposeColor -> {
            AndroidColor.argb(
                (color.alpha * 255).toInt(),
                (color.red * 255).toInt(),
                (color.green * 255).toInt(),
                (color.blue * 255).toInt()
            )
        }
        else -> return
    }

    enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.auto(
            lightScrim = bgColor,
            darkScrim = bgColor
        )
    )

    this.uiTaySetTopBarTextColor(bgColor)
}

@Suppress("DEPRECATION")
fun ComponentActivity.uiTaySetTopBarTextColor(color : Int){
    val window = window
    val decorView = window.decorView
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        decorView.windowInsetsController?.let {
            val wic: WindowInsetsController = it
            if (ColorUtils.calculateLuminance(color)> 0.5){
                wic.setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS)
            }
        }

    } else  this.window.decorView.systemUiVisibility =
        if (ColorUtils.calculateLuminance(color)> 0.5) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else 0
}
