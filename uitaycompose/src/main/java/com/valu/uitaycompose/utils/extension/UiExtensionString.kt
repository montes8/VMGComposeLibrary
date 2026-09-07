/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.utils.extension


fun String.uiTayFilterSpaces(): String = this.filterNot { it.isWhitespace() }

fun String.uiTayFilterOnlyLetter(): String = this.filter { it.isLetter() }

fun String.uiTayFilterOnlyLetterAndNumber(): String = this.filter { it.isLetterOrDigit() }

fun String.uiTayFilterOnlyNumbers(): String = this.filter { it.isDigit() }

fun String.uiTayRemoveEmojisAndSpecial(allowedChars: String = "@.,-_?!#$ "): String {
    return this.filter {
        it.isLetterOrDigit() || it.isWhitespace() || it in allowedChars
    }
}
