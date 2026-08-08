package com.valu.uitaycompose.utils.extension

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valu.uitaycompose.utils.tay_pink_300
import com.valu.uitaycompose.utils.tay_pink_400
import com.valu.uitaycompose.utils.tay_pink_600

fun Modifier.uiTayBgBorder(
    color: Color = Color.Magenta,
    radius: Dp = 8.dp
): Modifier = this.background(
    color = color,
    shape = RoundedCornerShape(radius)
)

fun Modifier.uiTayBgBorderCircle(
    color: Color = Color.Magenta,
    colorStroke: Color = Color.Unspecified,
    strokeWidth: Dp = 2.dp
): Modifier = this
    .then(
        if (colorStroke.isSpecified && colorStroke != Color.Transparent && strokeWidth > 0.dp) {
            Modifier.border(width = strokeWidth, color = colorStroke, shape = CircleShape)
        } else {
            Modifier
        }
    )
    .background(color = color, shape = CircleShape)

fun Modifier.uiTayBgCircleGradient(
    colorTop: Color = tay_pink_300,
    colorBottom: Color = tay_pink_600
): Modifier = this.background(
    brush = Brush.verticalGradient(
        colors = listOf(colorTop, colorBottom)
    ),
    shape = CircleShape
)

fun Modifier.uiTayBgStrokeTop(
    colorSolid: Color = Color.White,
    colorStroke: Color = tay_pink_600,
    strokeWidth: Dp = 2.dp,
    topRadius: Dp = 24.dp
): Modifier {
    val shape = RoundedCornerShape(
        topStart = topRadius,
        topEnd = topRadius,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )

    return this
        .then(
            if (colorStroke.isSpecified && colorStroke != Color.Transparent && strokeWidth > 0.dp) {
                Modifier.border(width = strokeWidth, color = colorStroke, shape = shape)
            } else {
                Modifier
            }
        )
        .background(color = colorSolid, shape = shape)
}

fun Modifier.uiTayBgStroke(
    colorSolid: Color = Color.White,
    colorStroke: Color = tay_pink_600,
    radius: Dp = 0.dp,
    strokeWidth: Dp = 2.dp
): Modifier {
    val shape = RoundedCornerShape(radius)

    return this
        .then(
            if (colorStroke.isSpecified && colorStroke != Color.Transparent && strokeWidth > 0.dp) {
                Modifier.border(width = strokeWidth, color = colorStroke, shape = shape)
            } else {
                Modifier
            }
        )
        .background(color = colorSolid, shape = shape)
}
