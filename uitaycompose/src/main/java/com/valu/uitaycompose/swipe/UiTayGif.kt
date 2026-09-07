package com.valu.uitaycompose.swipe

import android.annotation.SuppressLint
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun UiTayGif(
    resId: Int,
    width: Dp? = null,
    height: Dp? = null,
    backgroundColor: Color = Color.Transparent,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var drawable by remember { mutableStateOf<Drawable?>(null) }

    LaunchedEffect(resId) {
        withContext(Dispatchers.IO) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val source = ImageDecoder.createSource(context.resources, resId)
                    drawable = ImageDecoder.decodeDrawable(source)
                } else {
                    val staticDrawable = androidx.core.content.ContextCompat.getDrawable(context, resId)
                    drawable = staticDrawable
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val sizeModifier = if (width != null && height != null) {
        modifier.size(width, height)
    } else {
        modifier.fillMaxSize()
    }

    Box(
        modifier = modifier.background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        drawable?.let { gifDrawable ->
            AndroidView(
                modifier = sizeModifier,
                factory = { ctx ->
                    ImageView(ctx).apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageDrawable(gifDrawable)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            if (gifDrawable is AnimatedImageDrawable) {
                                gifDrawable.start()
                            }
                        }
                    }
                },
                update = { imageView ->
                    imageView.setImageDrawable(gifDrawable)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        if (gifDrawable is AnimatedImageDrawable) {
                            gifDrawable.start()
                        }
                    }
                }
            )
        }
    }
}