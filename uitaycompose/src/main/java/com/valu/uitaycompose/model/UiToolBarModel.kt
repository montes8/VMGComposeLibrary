/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valu.uitaycompose.R
import com.valu.uitaycompose.utils.textS18

data class UiToolBarModel(
    var uiHeight: Int = 56,
    var uiBgColor: Color = Color.Blue,
    var uiTextColor: Color = Color.White,
    var uiIconColor: Color = Color.White,
    var uiIconStart: Int = R.drawable.uic_tay_ic_back,
    var uiIconEnd: Int = R.drawable.uic_tay_ic_menu,
    var uiTextMarginHorizontal: Int = 0,
    var uiIconMarginStar: Int = 0,
    var uiIconMarginEnd: Int = 0,
    var uiTextFont: TextStyle = textS18,
    var uiTextPosition: TextAlign = TextAlign.Center,
    var uiTypeStart: Boolean = true,
    var uiTypeEnd: Boolean = false,
    var uiIsUrlBg: Boolean = false,
    var uiUrlBg: String = "",
    var uiUseOriginalTint: Boolean = false,
    var uiIconSize: Dp = 44.dp
) {

    fun height(height: Int) = apply { this.uiHeight = height }
    fun backgroundColor(color: Color) = apply { this.uiBgColor = color }
    fun textColor(color: Color) = apply { this.uiTextColor = color }
    fun iconColor(color: Color) = apply { this.uiIconColor = color }
    fun iconStart(resId: Int) = apply { this.uiIconStart = resId }
    fun iconEnd(resId: Int) = apply { this.uiIconEnd = resId }
    fun textMarginHorizontal(margin: Int) = apply { this.uiTextMarginHorizontal = margin }
    fun iconMarginStart(margin: Int) = apply { this.uiIconMarginStar = margin }
    fun iconMarginEnd(margin: Int) = apply { this.uiIconMarginEnd = margin }
    fun textFont(font: TextStyle) = apply { this.uiTextFont = font }
    fun textPosition(position: TextAlign) = apply { this.uiTextPosition = position }
    fun showStartIcon(show: Boolean) = apply { this.uiTypeStart = show }
    fun showEndIcon(show: Boolean) = apply { this.uiTypeEnd = show }
    fun bgService(bgService: Boolean) = apply { this.uiIsUrlBg = bgService }
    fun urlBgService(urlBgService: String) = apply { this.uiUrlBg = urlBgService }
    fun useOriginalTint(useOriginal: Boolean) = apply { this.uiUseOriginalTint = useOriginal }

    fun setIconSize(iconSize: Dp) = apply { this.uiIconSize = iconSize }
}
