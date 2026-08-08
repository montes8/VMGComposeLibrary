package com.valu.uitaycompose.utils.extension

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.uiTayShimmer(
    isLoading: Boolean,
    cornerRadius: Dp = 4.dp
): Modifier = composed {
    if (!isLoading) {
        return@composed this
    }

    val infiniteTransition = rememberInfiniteTransition(label = "shimmer_transition")
    val phase by infiniteTransition.animateFloat(
        initialValue = -1.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_phase"
    )

    this
        .clip(RoundedCornerShape(cornerRadius))
        .drawWithContent {
            val width = size.width
            val height = size.height
            drawRect(color = Color(0xFFE5E5E5))

            val offsetX = (width + 8.dp.toPx()) * phase
            val brush = Brush.linearGradient(
                colors = listOf(
                    Color.Transparent,
                    Color.White.copy(alpha = 0.6f),
                    Color.Transparent
                ),
                start = Offset(x = offsetX - width, y = 0f),
                end = Offset(x = offsetX + width, y = 0f)
            )

            drawRect(brush = brush)
        }
}


@Composable
fun Modifier.uiTayNoRippleClickable(
    onClick: () -> Unit
) = this.then(
    Modifier.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
)

fun Modifier.uiTayInvisible(isInvisible: Boolean = true): Modifier = this.then(
    if (isInvisible) Modifier.alpha(0f) else Modifier
)

fun Modifier.uiTayInVisibility(visible: Boolean): Modifier = this.then(
    Modifier.alpha(if (visible) 1f else 0f)
)

fun Modifier.uiTayGone(isGone: Boolean = true): Modifier = if (isGone) {
    this.layout { _, _ ->
        layout(0, 0) {}
    }
} else this

fun Modifier.uiTayVisibility(visible: Boolean): Modifier = uiTayGone(!visible)