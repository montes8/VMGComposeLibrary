/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.utils.extension

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.google.gson.Gson
import com.valu.uitaycompose.utils.UI_EMPTY
import kotlinx.serialization.json.Json
import java.net.NetworkInterface
import java.util.Collections

val uiTayJson = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    encodeDefaults = true
}

fun uiTayGetMobilIPAddress(): String {
    try {
        val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
        for (intf in interfaces) {
            val addrs = Collections.list(intf.inetAddresses)
            for (addr in addrs) {
                if (!addr.isLoopbackAddress) {
                    val sAddr = addr.hostAddress
                    val isIPv4 = (sAddr?.indexOf(':') ?: -1) < 0
                    if (isIPv4) return sAddr ?: UI_EMPTY
                }
            }
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return UI_EMPTY
}

fun uiTayDriveUrl(originalUrl: String): String {
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

fun Context.uiTayNameSplashCustom(): String {
    return when (this.uiTayCountryNetwork()) {
        "AR" -> {
            "SplashAR"
        }
        "MX" -> {
            "SplashMX"
        }
        else -> {
            "Splash"
        }
    }
}

fun Context.uiTayNameToolbarCustom(): String {
    return when (this.uiTayCountryNetwork()) {
        "AR" -> {
            "ToolbarAR"
        }
        "MX" -> {
            "ToolbarMX"
        }
        else -> {
            "Toolbar"
        }
    }
}

fun Context.uiTayNameBackgroundCustom(): String {
    return when (this.uiTayCountryNetwork()) {
        "AR" -> {
            "BackgroundAR"
        }
        "MX" -> {
            "BackgroundMX"
        }
        else -> {
            "Background"
        }
    }
}

fun Context.uiTayUrlFacebook(idProfile: String) {
    if (idProfile.isNotEmpty()) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, "fb://profile/$idProfile".toUri())
            startActivity(intent)
        } catch (e: java.lang.Exception) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    "http://www.facebook.com/$idProfile".toUri()
                )
            )
        }
    } else {
        uiTayShowToast("Aun no esta configurado")
    }

}

fun Context.uiTayOpenUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    this.startActivity(intent)
}

inline fun <reified T> uiTayDataJson(context: Context, fileName: String): T {
    val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
    return uiTayJson.decodeFromString(json)
}

inline fun <reified T> uiTayJsonToObjet(json: String): T {
    return uiTayJson.decodeFromString(json)
}

fun <T> T.uiTayObjetToJson(): String {
    val jsonData = Gson()
    return jsonData.toJson(this)
}
