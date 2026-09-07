package com.valu.uitaycompose.extra

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.valu.uitaycompose.R
import com.valu.uitaycompose.utils.extension.uiTayNoRippleClickable

@Composable
fun UiTaySwitchCustom(isState : Boolean = false,
                      uTHeight : Int = 30,
                      uTWidth : Int = 50,
                      uTPadding : Int = 2,
                      uTBgSelected : Int = R.drawable.uic_tay_ic_bg_round,
                      uTBgUnSelected : Int = R.drawable.uic_tay_ic_bg_round,
                      uTImgSelected : Int = R.drawable.uic_tay_bg_circle,
                      uTImgUnSelected : Int = R.drawable.uic_tay_bg_circle,
                      uTBgFull : Boolean = false,
                      uiTayCheckedChange: (Boolean) -> Unit = {}){
    var isChecked by remember { mutableStateOf(isState) }
    Row(
        modifier = Modifier
            .height(uTHeight.dp)
            .width(uTWidth.dp)
            .paint(
                painterResource(id = if(isChecked)uTBgSelected else uTBgUnSelected ),
                contentScale = ContentScale.FillBounds) .uiTayNoRippleClickable{
                isChecked = !isChecked
                uiTayCheckedChange.invoke(!isChecked)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement= if(isChecked) Arrangement.End else Arrangement.Start

    ) {
        if (!uTBgFull){
            Image(
                painter = painterResource(if(isChecked)uTImgSelected else uTImgUnSelected ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size((uTHeight - uTPadding).dp).padding(uTPadding.dp)
                    .clip(CircleShape),
                contentDescription = "uiTaySwitchCustom",
                alignment = Alignment.Center
            )
        }
    }
}