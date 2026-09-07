/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.valu.uitaycompose.R
import com.valu.uitaycompose.swipe.UiTayGif
import com.valu.uitaycompose.utils.tay_grey_200

fun Modifier.uiTayShowProgress(
    show: Boolean
): Modifier = composed {
    if (show) {
        UiTayProgress()
    }
    this
}

@Composable
fun UiTayProgress(
    duration: Int = 600,
    line: Dp = 4.dp,
    size: Dp = 40.dp,
    colorProgress: Color = Color.Magenta,
    bgProgress: Color = tay_grey_200,
    backgroundColor: Color = Color.Black.copy(alpha = 0.5f)
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            UiTayDualColorFastProgress(
                speedMillis = duration,
                color1 = colorProgress,
                color2 = bgProgress,
                strokeWidth = line,
                size = size
            )
        }
    }
}


@Composable
fun UiTayDualColorFastProgress(
    speedMillis: Int,
    color1: Color ,
    color2: Color ,
    strokeWidth: Dp,
    size: Dp
) {
    val transition = rememberInfiniteTransition(label = "rotation")

    val rotateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(speedMillis, easing = LinearEasing)
        ),
        label = "angle"
    )

    Canvas(
        modifier = Modifier
            .size(size)
            .rotate(rotateAnim)
    ) {
        val stroke = strokeWidth.toPx()

        drawArc(
            color = color1,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(width = stroke, cap = StrokeCap.Round)
        )
        drawArc(
            color = color2,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(width = stroke, cap = StrokeCap.Round)
        )
    }
}

@Composable
fun UiTayAnimationProgress(
    idGif: Int = R.drawable.gif_splash,
    backgroundColor: Color = Color.Black.copy(alpha = 0.5f)
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            UiTayGif(
                resId = idGif,
                width = 250.dp,
                height = 250.dp
            )
        }
    }
}
