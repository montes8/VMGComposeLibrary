/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.swipe

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.readRawBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

private val sharedClient = HttpClient(CIO)

@Composable
fun UiTayUrlImage(
    url: String,
    modifier: Modifier = Modifier
) {
    val finalUrl = remember(url) { url }
    var imageBitmap by remember(finalUrl) {
        mutableStateOf(TayImageCache.get(finalUrl))
    }
    var isLoading by remember(finalUrl) {
        mutableStateOf(imageBitmap == null)
    }
    LaunchedEffect(finalUrl) {
        if (imageBitmap == null) {
            isLoading = true
            try {
                val bitmap = withContext(Dispatchers.IO) {
                    val response = sharedClient.get(finalUrl)
                    val bytes = response.readRawBytes()
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.size)?.asImageBitmap()
                }
                if (bitmap != null) {
                    TayImageCache.put(finalUrl, bitmap)
                    imageBitmap = bitmap
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        imageBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } ?: if (isLoading) {
            Box(Modifier.fillMaxSize().background(Color.LightGray.copy(alpha = 0.5f)))
        } else {
            Box(Modifier.fillMaxSize().background(Color.Gray))
        }
    }
}

object TayImageCache {
    private val cache = ConcurrentHashMap<String, ImageBitmap>()
    fun get(url: String): ImageBitmap? = cache[url]
    fun put(url: String, bitmap: ImageBitmap) { cache[url] = bitmap }
}
