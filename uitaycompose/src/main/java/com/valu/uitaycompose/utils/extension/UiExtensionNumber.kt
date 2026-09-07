/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.utils.extension

import java.text.NumberFormat

fun uiTayDeg2rad(deg: Double): Double {
    return (deg * Math.PI / 180.0)
}

fun uiTayRad2deg(rad: Double): Double {
    return (rad * 180.0 / Math.PI)
}

fun Double.uiTayConverter(): Double {
    return try {
        (this.uiTayFormatDecimal() * 1000)
    } catch (e: Exception) {
        e.printStackTrace()
        0.0
    }
}

fun Double.uiTayFormatDecimal(decimal : Int = 3): Double {
    return try {
        val formatParse = NumberFormat.getInstance()
        formatParse.maximumFractionDigits = decimal
        formatParse.minimumFractionDigits = decimal
        formatParse.format(this).toDouble()

    } catch (e: Exception) {
        e.printStackTrace()
        0.0
    }
}

infix fun Int.uiTayPercentOf(value: Int): Int {
    return if (this == 0) 0
    else ((this.toDouble() / 100) * value).toInt()
}

fun Number.uiTayFormatDigits(digits: Int = 2): String {
    val isNegative = this.toDouble() < 0
    val rawString = this.toString().removePrefix("-")

    val formatted = if (rawString.contains(".")) {
        val parts = rawString.split(".")
        val intPart = parts[0].padStart(digits, '0')
        "$intPart.${parts[1]}"
    } else {
        rawString.padStart(digits, '0')
    }

    return if (isNegative) "-$formatted" else formatted
}

fun String.uiTayFormatDecimal(decimal : Int = 2): String {
    return try {
        val formatParse = NumberFormat.getInstance()
        formatParse.maximumFractionDigits = decimal
        formatParse.minimumFractionDigits = decimal
        formatParse.format(this.toDouble())

    } catch (e: Exception) {
        e.printStackTrace()
        "0.00"
    }
}

fun String.uiTayValidateCap():Boolean{
    val capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    var count = 0
    capitalLetters.forEach {
        this.forEach {pass->
            if (pass == it) count += 1
        }
    }
    return count >= 1
}

fun String.uiTayValidateLow():Boolean{
    val lowerCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".lowercase()
    var count = 0
    lowerCase.forEach {
        this.forEach {pass->
            if (pass == it) count += 1
        }
    }
    return count >= 1
}

fun String.uiTayValidateNumber():Boolean{
    val numbers = "0123456789"
    var count = 0
    numbers.forEach {
        this.forEach {pass->
            if (pass == it) count += 1
        }
    }
    return count >= 1
}
