package com.valu.uitaycompose.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valu.uitaycompose.R
import com.valu.uitaycompose.utils.*

data class UiEditLayoutModel (
    var uiBgColor : Color = Color.White,
    var uiBgDisableColor : Color = Color.White,
    var uiBgActiveColor : Color = Color.White,
    var uiStrokeColor : Color = tay_grey_400,
    var uiStrokeDisableColor : Color = tay_grey_400,
    var uiStrokeActiveColor : Color = tay_blue_700,
    var uiTextColor: Color = tay_grey_800,
    var uiTextActiveColor: Color = tay_blue_700,
    var uiTextDisableColor: Color = tay_grey_400,
    var uiTitleColor: Color = tay_grey_800,
    var uiTitleActiveColor: Color = tay_blue_700,
    var uiTitleDisableColor: Color = tay_grey_400,
    var uiHintColor: Color = tay_grey_800,
    var uiMessageColor: Color = tay_red_500,
    var uiIconColor: Color = tay_grey_800,
    var uiIconActiveColor: Color = tay_blue_700,
    var uiIconDisableColor: Color = tay_grey_400,
    var uiIconStart : Int = R.drawable.uic_tay_ic_back,
    var uiIconEnd : Int = R.drawable.uic_tay_ic_menu,
    var uiPaddingHorizontal : Dp = 16.dp,
    var uiPaddingVertical : Dp = 8.dp,
    var uiIconMarginStar : Dp = 8.dp,
    var uiIconMarginEnd : Dp = 8.dp,
    var uiTextFont : TextStyle = textM16,
    var uiTitleFont : TextStyle = textM14,
    var uiMessageFont : TextStyle = textS12
)