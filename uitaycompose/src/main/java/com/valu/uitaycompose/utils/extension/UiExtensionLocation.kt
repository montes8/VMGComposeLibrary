/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.utils.extension

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.valu.uitaycompose.R

@SuppressLint("QueryPermissionsNeeded", "UseKtx")
fun Context.uiTayRouteMapIntent(latitude: Double, longitude: Double) {
    val packageManager = this.packageManager
    val geoMaps = Uri.parse("google.navigation:q=${latitude},${longitude}")
    val geoWaz = Uri.parse("waze://?ll=${latitude},${longitude}&navigate=yes")

    val intentWaz = Intent(Intent.ACTION_VIEW, geoWaz).apply {
        setPackage(this@uiTayRouteMapIntent.getString(R.string.ui_tay_waze_package))
    }

    val intentMap = Intent(Intent.ACTION_VIEW, geoMaps).apply {
        setPackage(this@uiTayRouteMapIntent.getString(R.string.ui_tay_maps_package))
    }

    try {
        if (intentMap.resolveActivity(packageManager) != null) {
            val chooserIntent = Intent.createChooser(
                intentMap,
                this.getString(R.string.ui_tay_start_navigation)
            )

            if (intentWaz.resolveActivity(packageManager) != null) {
                val arr = arrayOf<Intent>(intentWaz)
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arr)
            }

            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(chooserIntent)

        } else {
            this.uiTaySendUriMap(
                R.string.ui_tay_no_maps,
                Uri.parse(this.getString(R.string.ui_tay_maps_store))
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
        this.uiTaySendUriMap(
            R.string.ui_tay_no_maps,
            Uri.parse(this.getString(R.string.ui_tay_maps_store))
        )
    }
}

@SuppressLint("UseKtx")
fun Context.uiTayLocationMapIntent(
    latitude: String,
    longitude: String
) {
    uiTayTryCatch(catch = { uiTayShowToast(this.getString(R.string.tay_ui_error_app_maps)) }) {
        val uri = "http://maps.google.com/maps?q=loc:$latitude,$longitude"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri)).apply {
            setPackage("com.google.android.apps.maps")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        this.startActivity(intent)
    }
}

private fun Context.uiTaySendUriMap(messageToast: Int, uri: Uri) {
    this.uiTayShowToast(messageToast)
    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    this.startActivity(intent)
}
