/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.utils.tag

import android.util.Log
import com.valu.uitaycompose.utils.UI_TAY_TAG

fun String.uiTayPrint(tag : String = UI_TAY_TAG){
    println("-------------------------------\n$tag: $this -------------------------------\n")
}

fun String.uiTayLog(tag : String = UI_TAY_TAG){
    Log.d(tag,"-------------------------------\n $this -------------------------------\n")
}

fun String.uiTayLogE(tag : String = UI_TAY_TAG){
    Log.e(tag,"-------------------------------\n $this -------------------------------\n")
}
