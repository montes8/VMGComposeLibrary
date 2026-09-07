/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.extra

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valu.uitaycompose.model.UiToolBarModel
import com.valu.uitaycompose.swipe.UiTayUrlImage
import com.valu.uitaycompose.utils.UI_TAY_TEXT_DEFAULT


@Composable
fun UiTayCToolBar(
    uiTayText: String = UI_TAY_TEXT_DEFAULT,
    uiTayModifier: UiToolBarModel = UiToolBarModel(),
    uiTayClick: (Boolean) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .height(uiTayModifier.uiHeight.dp)
            .fillMaxWidth()
            .then(
                if (!uiTayModifier.uiIsUrlBg) {
                    Modifier.background(uiTayModifier.uiBgColor)
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        if (uiTayModifier.uiIsUrlBg && uiTayModifier.uiUrlBg.isNotEmpty()) {
            UiTayUrlImage(
                url = uiTayModifier.uiUrlBg,
                modifier = Modifier.fillMaxSize()
            )
        }

        if (uiTayModifier.uiTypeStart) {
            IconButton(
                onClick = { uiTayClick(true) },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = uiTayModifier.uiIconMarginStar.dp)
            ) {
                ToolBarIconItem(
                    resId = uiTayModifier.uiIconStart,
                    useOriginalTint = uiTayModifier.uiUseOriginalTint,
                    tintColor = uiTayModifier.uiIconColor,
                    iconSize = uiTayModifier.uiIconSize
                )
            }
        }

        Text(
            text = uiTayText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = uiTayModifier.uiTextMarginHorizontal.dp,
                    end = uiTayModifier.uiTextMarginHorizontal.dp
                ),
            color = uiTayModifier.uiTextColor,
            textAlign = uiTayModifier.uiTextPosition,
            style = uiTayModifier.uiTextFont
        )

        if (uiTayModifier.uiTypeEnd) {
            IconButton(
                onClick = { uiTayClick(false) },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(start = uiTayModifier.uiIconMarginEnd.dp)
            ) {
                ToolBarIconItem(
                    resId = uiTayModifier.uiIconEnd,
                    useOriginalTint = uiTayModifier.uiUseOriginalTint,
                    tintColor = uiTayModifier.uiIconColor,
                    iconSize = uiTayModifier.uiIconSize
                )
            }
        }
    }
}

@Composable
private fun ToolBarIconItem(
    resId: Int,
    useOriginalTint: Boolean,
    tintColor: Color,
    iconSize: Dp
) {
    if (useOriginalTint) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = Modifier.size(iconSize)
        )
    } else {
        Icon(
            painter = painterResource(id = resId),
            contentDescription = null,
            tint = tintColor
        )
    }
}
