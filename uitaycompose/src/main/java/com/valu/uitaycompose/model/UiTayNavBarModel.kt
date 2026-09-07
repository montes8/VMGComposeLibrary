/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valu.uitaycompose.utils.tay_deep_orange_400
import com.valu.uitaycompose.utils.tay_grey_400
import com.valu.uitaycompose.utils.textS12
import com.valu.uitaycompose.utils.textSe10

class UiTayNavBarModel (
    var uiBgColor: Color = Color.White,
    var uiRadiusTop: Dp = 24.dp,
    var uiColorSelected: Color = tay_deep_orange_400,
    var uiUnColorSelected: Color = tay_grey_400,
    var uiTextColorSelected: Color = tay_deep_orange_400,
    var uiTextUnColorSelected: Color = tay_grey_400,
    var uiSelectedFont : TextStyle  = textSe10,
    var uiUnSelectedFont : TextStyle  = textSe10
){
    fun uiBgColor(uiBgColor: Color) = apply { this.uiBgColor = uiBgColor }
    fun uiRadiusTop(uiRadiusTop: Dp) = apply { this.uiRadiusTop = uiRadiusTop }
    fun uiColorSelected(uiColorSelected: Color) = apply { this.uiColorSelected = uiColorSelected }
    fun uiUnColorSelected(uiUnColorSelected: Color) = apply { this.uiUnColorSelected = uiUnColorSelected }
    fun uiTextColorSelected(uiTextColorSelected: Color) = apply { this.uiTextColorSelected = uiTextColorSelected }
    fun uiTextUnColorSelected(uiTextUnColorSelected: Color) = apply { this.uiTextUnColorSelected = uiTextUnColorSelected }
    fun uiSelectedFont(uiSelectedFont: TextStyle) = apply { this.uiSelectedFont = uiSelectedFont }
    fun uiUnSelectedFont(uiUnSelectedFont: TextStyle) = apply { this.uiUnSelectedFont = uiUnSelectedFont }
}
