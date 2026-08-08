package com.valu.uitaycompose.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.valu.uitaycompose.model.UiTayNavBarItem
import com.valu.uitaycompose.model.UiTayNavBarModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun UiTayBottomBar(
    items: List<UiTayNavBarItem>,
    currentActionId: Int,
    uiTayModifier : UiTayNavBarModel = UiTayNavBarModel(),
    onItemClick: (UiTayNavBarItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawIntoCanvas { canvas ->
                    val paint = Paint()
                    val frameworkPaint = paint.asFrameworkPaint()
                    frameworkPaint.color = android.graphics.Color.WHITE
                    frameworkPaint.setShadowLayer(
                        25f,
                        0f,
                        -4f,
                        android.graphics.Color.argb(50, 0, 0, 0)
                    )
                    canvas.drawRoundRect(
                        0f, 0f, size.width, size.height,
                        60f, 60f,
                        paint
                    )
                }
            }
            .background(uiTayModifier.uiBgColor, RoundedCornerShape(topStart = uiTayModifier.uiRadiusTop, topEnd = uiTayModifier.uiRadiusTop))
        ,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .background(uiTayModifier.uiBgColor, RoundedCornerShape(topStart = uiTayModifier.uiRadiusTop, topEnd = uiTayModifier.uiRadiusTop)
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = item.action == currentActionId
                CustomTabItem(
                    item = item,
                    isSelected = isSelected,
                    model = uiTayModifier,
                    onClick = {
                        onItemClick.invoke(item)
                    }
                )
            }
        }
    }
}

@Composable
fun CustomTabItem(
    item: UiTayNavBarItem,
    isSelected: Boolean,
    model : UiTayNavBarModel,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.pointerInput(Unit) {
        detectTapGestures(
            onTap = { onClick() }
        )
    },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = item.iconId),
            contentDescription = "itemNav${stringResource(item.titleId)}",
            tint = if (isSelected) model.uiColorSelected else model.uiUnColorSelected
        )
        Text(
            text = stringResource(item.titleId),
            color = if (isSelected) model.uiTextColorSelected else model.uiTextUnColorSelected,
            style = if (isSelected) model.uiSelectedFont else model.uiUnSelectedFont
        )
    }
}