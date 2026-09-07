/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.model.entity

import com.valu.uitaycompose.utils.UI_EMPTY

data class UiTayContactPhone (
    var name :String = UI_EMPTY,
    var phoneNumber :String = UI_EMPTY,
    var initialLetter: String = UI_EMPTY
)
