/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.modal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.valu.uitaycompose.model.UiTayDialogModel
import com.valu.uitaycompose.utils.tay_dialog_transparent

@Composable
fun UiTayDialog(
    model: UiTayDialogModel = UiTayDialogModel(),
    onDismissRequest: (Boolean) -> Unit
) {
    val style = model.styleCustom

    Dialog(
        onDismissRequest = {
            if (model.isCancel) {
                onDismissRequest(false)
            }
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = model.isCancel,
            dismissOnClickOutside = model.isCancel
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(tay_dialog_transparent)
                .padding(
                    horizontal =  style.uiTayPaddingContentHorizontal
                )
                .clickable(
                    enabled = model.isCancel,
                    onClick = { onDismissRequest(false) }
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape( style.contentRadius))
                    .background(style.contentColorSolid)
                    .border(
                        width = 1.dp,
                        color = style.contentColorStroke,
                        shape = RoundedCornerShape( style.contentRadius)
                    )
                    .clickable(enabled = false) {}
                    .padding(
                        horizontal =  style.paddingContentDataHorizontal,
                        vertical =  style.paddingContentDataVertical
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (model.isCancel) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                            onClick = { onDismissRequest(false) },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size( style.sizeIcon)
                        ) {
                            Icon(
                                painter = painterResource(id = style.imageClose),
                                contentDescription = "Cerrar"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    painter = painterResource(id = model.image),
                    contentDescription = "Logo",
                    modifier = Modifier.size(style.sizeLogo)
                )

                Spacer(modifier = Modifier.height( style.paddingTopTitle))

                Text(
                    text = model.title,
                    style = model.styleCustom.titleFont,
                    color = style.titleColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height( style.paddingTopSubTitle))

                Text(
                    text = model.subTitle,
                    style = model.styleCustom.subTitleFont,
                    color =  style.subTitleColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height( style.marginTopBtnAccept))

                Button(
                    onClick = { onDismissRequest(true) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height( style.sizeHeightButton),
                    shape = RoundedCornerShape( style.btnRadius),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  style.btnAcceptSolidColor
                    )
                ) {
                    Text(
                        text = model.buttonText,
                        style = model.styleCustom.buttonAcceptFond,
                        color = style.buttonTextAcceptColor
                    )
                }

                if (model.btnCancel) {
                    Spacer(modifier = Modifier.height(style.marginTopBtnCancel))

                    OutlinedButton(
                        onClick = { onDismissRequest(false) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height( style.sizeHeightButton),
                        shape = RoundedCornerShape(style.btnRadius),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor =  style.btnCancelSolidColor
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color =  style.btnCancelStrokeColor
                        )
                    ) {
                        Text(
                            text = model.buttonCancelText,
                            style = model.styleCustom.buttonCancelFond,
                            color =  style.buttonCancelTextColor
                        )
                    }
                }
            }
        }
    }
}
