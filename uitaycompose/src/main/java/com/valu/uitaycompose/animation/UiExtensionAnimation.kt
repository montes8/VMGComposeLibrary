package com.valu.uitaycompose.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

/**Appearance animation from alpha 0 to its original color*/
@Composable
fun rememberFadeInAnimation(duration: Int = 1300): Float {
    val alphaAnim = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        delay(500.milliseconds)
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = duration,
                easing = FastOutLinearInEasing
            )
        )
    }

    return alphaAnim.value
}

/**Disappearance animation from original color to alphs 0*/
@Composable
fun rememberFadeOutAnimation(
    duration: Int = 1300,
    onAnimationEnd: () -> Unit = {}
): Float {
    val alphaAnim = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        delay(500.milliseconds)
        alphaAnim.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = duration,
                easing = FastOutLinearInEasing
            )
        )
        onAnimationEnd()
    }

    return alphaAnim.value
}

/**rebound successive effect animation*/
fun Modifier.uiTayDoBounce(
    duration: Int = 250,
    distanceRebound: Dp = (-15).dp
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "BounceAnimation")

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = distanceRebound.value,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "BounceOffset"
    )

    this.graphicsLayer {
        translationY = offsetY
    }
}

@Composable
fun rememberScaleDownAnimation(
    initialHeight: Dp,
    duration: Int = 500,
    onAnimationEnd: () -> Unit = {}
): Dp {
    var targetHeight by remember { mutableStateOf(initialHeight) }
    val animatedHeight by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = tween(durationMillis = duration),
        finishedListener = {
            onAnimationEnd()
        },
        label = "ScaleDownHeight"
    )
    LaunchedEffect(Unit) {
        targetHeight = 0.dp
    }

    return animatedHeight
}

fun Modifier.uiTaySlideUp(
    duration: Int = 500,
    heightInit: Float = 850f
): Modifier = composed {
    val offsetY = remember { Animatable(heightInit) }
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch {
            offsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = duration, easing = FastOutSlowInEasing)
            )
        }
        launch {
            alphaAnim.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = duration)
            )
        }
    }

    this.graphicsLayer {
        translationY = offsetY.value
        alpha = alphaAnim.value
    }
}

fun Modifier.uiTaySlideDown(
    duration: Int = 500,
    heightInit: Float = 850f,
    onAnimationEnd: () -> Unit = {}
): Modifier = composed {
    val offsetY = remember { Animatable(0f) }
    val alphaAnim = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        launch {
            offsetY.animateTo(
                targetValue = heightInit,
                animationSpec = tween(durationMillis = duration, easing = FastOutLinearInEasing)
            )
            onAnimationEnd()
        }
        launch {
            alphaAnim.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = duration)
            )
        }
    }

    this.graphicsLayer {
        translationY = offsetY.value
        alpha = alphaAnim.value
    }
}