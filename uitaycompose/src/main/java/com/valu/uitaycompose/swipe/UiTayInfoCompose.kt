/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.swipe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.valu.uitaycompose.R
import com.valu.uitaycompose.utils.tay_pink_50
import com.valu.uitaycompose.utils.tay_pink_600
import com.valu.uitaycompose.utils.tay_red_50
import com.valu.uitaycompose.utils.tay_red_600
import com.valu.uitaycompose.utils.textM12

enum class UITayStyleInfoCompose {
    UI_TAY_INFO, UI_TAY_ERROR
}

@Composable
fun UiTayInfoCompose(
    modifier: Modifier = Modifier,
    text: String = "",
    annotatedText: AnnotatedString? = null,
    icon: Painter = painterResource(id = R.drawable.ui_tay_ic_info),
    style: UITayStyleInfoCompose = UITayStyleInfoCompose.UI_TAY_INFO,
    isStyleEnabled: Boolean = true,
    primaryColor: Color? = null,
    secondaryColor: Color? = null,
    customTextColor: Color? = null,
    customBgColor: Color? = null,
    customStrokeColor: Color? = null
) {
    val isInfo = style == UITayStyleInfoCompose.UI_TAY_INFO
    val defaultStrokeColor = if (isInfo) tay_pink_600 else tay_red_600
    val defaultBgColor = if (isInfo) tay_pink_50 else tay_red_50
    val defaultIconColor = if (isInfo) tay_pink_600 else tay_red_600
    val defaultTextColor = if (isInfo) tay_pink_600 else tay_red_600

    val finalStrokeColor = secondaryColor ?: if (!isStyleEnabled && customStrokeColor != null) customStrokeColor else defaultStrokeColor
    val finalBgColor = secondaryColor ?: if (!isStyleEnabled && customBgColor != null) customBgColor else defaultBgColor
    val finalIconColor = primaryColor ?: defaultIconColor
    val finalTextColor = primaryColor ?: if (!isStyleEnabled && customTextColor != null) customTextColor else defaultTextColor

    val shape = RoundedCornerShape(24.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = finalBgColor, shape = shape)
            .border(width = 1.dp, color = finalStrokeColor, shape = shape)
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            painter = icon,
            contentDescription = "Info Icon",
            tint = finalIconColor,
            modifier = Modifier.wrapContentSize()
        )

        Spacer(modifier = Modifier.width(8.dp))

        if (annotatedText != null) {
            Text(
                text = annotatedText,
                style = textM12,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(
                text = text,
                style = textM12,
                color = finalTextColor,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
