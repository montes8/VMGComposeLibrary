/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.utils.extension

import java.util.regex.Pattern

fun String.uiTayValidateEmail() : Boolean{
    val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    val pattern = Pattern.compile(emailRegex)
    val matcher = pattern.matcher(this)
    return matcher.find()
}

fun String.uiTayValidatePhoneFormat() : Boolean{
    return if (this.isNotEmpty()){
        val numberStart = this[0]
        if (numberStart == '9') {
            if (this.contains('*')) {
                false
            } else {
                val phoneRegex = "(?=.[0-9]).{9}"
                val pattern = Pattern.compile(phoneRegex)
                pattern.matcher(this).matches()
            }
        } else
            false
    }else{
        false
    }
}
