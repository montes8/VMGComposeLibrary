package com.valu.uitaycompose.extra

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.valu.uitaycompose.model.UiSwitchModel
import com.valu.uitaycompose.utils.extension.uiTayNoRippleClickable

@Composable
fun UiTaySwitch(state : Boolean = false,
                uTModel : UiSwitchModel = UiSwitchModel(),
                uiTayCheckedChange: (Boolean) -> Unit = {}){
    var isChecked by remember { mutableStateOf(state) }
    Row(
        modifier = Modifier
            .height(uTModel.uTHeight.dp)
            .width(uTModel.uTWidth.dp)
            .clip(RoundedCornerShape(uTModel.uTRadiusCtn.dp))

            .border(uTModel.uTSizeStrokeCtn.dp,
                if(isChecked) uTModel.uTBgStrokeSelectedCtn else uTModel.uTBgStrokeUnSelectedCtn
                ,
                CircleShape)
            .background(if(isChecked) uTModel.uTBgSelectedCtn else uTModel.uTBgUnSelectedCtn)
            .uiTayNoRippleClickable{
                isChecked =  !isChecked
                uiTayCheckedChange.invoke(isChecked)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement= if(isChecked) Arrangement.End else Arrangement.Start

    ) {
        Box(
            modifier = Modifier
                .size((uTModel.uTHeight - uTModel.uTPadding).dp).padding(uTModel.uTPadding.dp)
                .clip(CircleShape)
                .border(uTModel.uTSizeStroke.dp,
                  if(isChecked) uTModel.uTBgStrokeSelected else uTModel.uTBgStrokeUnSelected
                    , CircleShape)
                .background( if(isChecked) uTModel.uTBgSelected else uTModel.uTBgUnSelected)
        )
    }
}