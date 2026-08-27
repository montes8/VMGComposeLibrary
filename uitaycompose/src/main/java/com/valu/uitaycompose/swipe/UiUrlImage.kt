package com.valu.uitaycompose.swipe

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.LruCache
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.readRawBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val sharedClient = HttpClient(CIO)

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun UiTayUrlImage(
    url: String,
    modifier: Modifier = Modifier,
    drawable: Int? = null
) {
    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        val density = LocalDensity.current
        val targetWidthPx = remember(maxWidth) {
            with(density) { maxWidth.toPx().toInt() }
        }

        val cacheKey = remember(url, targetWidthPx) { "${url}_w$targetWidthPx" }
        var imageBitmap by remember(cacheKey) { mutableStateOf(TayImageCache.get(cacheKey)) }
        var isLoading by remember(cacheKey) { mutableStateOf(imageBitmap == null && url.isNotBlank()) }

        LaunchedEffect(cacheKey) {
            if (url.isNotBlank() && imageBitmap == null) {
                isLoading = true
                try {
                    val bitmap = withContext(Dispatchers.IO) {
                        val response = sharedClient.get(url)
                        val bytes = response.readRawBytes()
                        decodeSampledBitmapFromBytes(bytes, targetWidthPx)?.asImageBitmap()
                    }
                    if (bitmap != null) {
                        TayImageCache.put(cacheKey, bitmap)
                        imageBitmap = bitmap
                    }
                } catch (e: Exception) {
                    println("Error cargando imagen: ${e.message}")
                } finally {
                    isLoading = false
                }
            }
        }

        when {
            imageBitmap != null -> {
                Image(
                    bitmap = imageBitmap!!,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            isLoading -> {
                Box(Modifier.fillMaxSize().background(Color.LightGray.copy(alpha = 0.5f)))
            }
            drawable != null -> {
                Image(
                    painter = painterResource(id = drawable),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            else -> {
                Box(Modifier.fillMaxSize().background(Color.Gray))
            }
        }
    }
}

private fun decodeSampledBitmapFromBytes(bytes: ByteArray, reqWidthPx: Int): android.graphics.Bitmap? {
    if (reqWidthPx <= 0) return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
    var sampleSize = 1
    if (options.outWidth > reqWidthPx) {
        val halfWidth = options.outWidth / 2
        while (halfWidth / sampleSize >= reqWidthPx) {
            sampleSize *= 2
        }
    }
    val decodeOptions = BitmapFactory.Options().apply { inSampleSize = sampleSize }
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, decodeOptions)
}

object TayImageCache {
    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    private val cacheSize = maxMemory / 8

    private val cache = object : LruCache<String, ImageBitmap>(cacheSize) {
        override fun sizeOf(key: String, value: ImageBitmap): Int {
            return value.asAndroidBitmap().allocationByteCount / 1024
        }
    }

    @Synchronized
    fun get(key: String): ImageBitmap? = cache.get(key)

    @Synchronized
    fun put(key: String, bitmap: ImageBitmap) {
        if (get(key) == null) {
            cache.put(key, bitmap)
        }
    }
}
fun getDirectDrive(originalUrl: String): String {
    if (!originalUrl.contains("drive.google.com")) return originalUrl
    val idPattern = "/d/([^/]+)".toRegex()
    val match = idPattern.find(originalUrl)
    val id = match?.groupValues?.get(1)
    return if (id != null) {
        "https://drive.google.com/uc?export=view&id=$id"
    } else {
        originalUrl
    }
}