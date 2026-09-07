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
import com.valu.uitaycompose.R
import com.valu.uitaycompose.utils.tay_red_700
import com.valu.uitaycompose.utils.textM14
import com.valu.uitaycompose.utils.textS14
import com.valu.uitaycompose.utils.textS18

data class UiTayDialogModelCustom(
    val imageClose: Int = R.drawable.uic_tay_close,
    val contentRadius: Dp = 20.dp,
    val uiTayPaddingContentHorizontal: Dp = 48.dp,
    val contentColorStroke: Color = Color.White,
    val contentColorSolid: Color = Color.White,
    val titleColor: Color = Color.Black,
    val subTitleColor: Color = Color.Black,
    val buttonTextAcceptColor: Color = Color.White,
    val buttonCancelTextColor: Color = tay_red_700,
    val btnAcceptStrokeColor: Color = tay_red_700,
    val btnAcceptSolidColor: Color = tay_red_700,
    val btnCancelStrokeColor: Color = tay_red_700,
    val btnCancelSolidColor: Color = Color.White,
    val btnRadius: Dp = 28.dp,
    val sizeIcon: Dp = 24.dp,
    val sizeLogo: Dp = 48.dp,
    val titleFont: TextStyle = textS18,
    val subTitleFont: TextStyle = textM14,
    val buttonAcceptFond: TextStyle = textS14,
    val buttonCancelFond: TextStyle = textS14,
    val sizeHeightButton: Dp = 40.dp,
    val paddingTopTitle: Dp = 8.dp,
    val paddingTopSubTitle: Dp = 8.dp,
    val marginTopBtnAccept: Dp = 20.dp,
    val marginTopBtnCancel: Dp = 16.dp,
    val paddingContentDataHorizontal: Dp = 16.dp,
    val paddingContentDataVertical: Dp = 20.dp
)
