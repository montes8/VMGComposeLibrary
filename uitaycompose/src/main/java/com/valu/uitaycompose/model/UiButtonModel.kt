package com.valu.uitaycompose.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.valu.uitaycompose.utils.tay_blue_700
import com.valu.uitaycompose.utils.tay_blue_900
import com.valu.uitaycompose.utils.tay_grey_300
import com.valu.uitaycompose.utils.tay_grey_400
import com.valu.uitaycompose.utils.tay_grey_700
import com.valu.uitaycompose.utils.tay_pink_300
import com.valu.uitaycompose.utils.tay_pink_400
import com.valu.uitaycompose.utils.textB16


data class UiTayButtonModel (
    var uTHeight : Int = 48,
    var uTBgColor : Color = tay_pink_300,
    var uTStrokeColor : Color = tay_pink_300,
    var uTBgDisableColor : Color = tay_grey_300,
    var uTStrokeDisableColor : Color = tay_grey_300,
    var uTBgSelectedColor : Color = tay_pink_400,
    var uTStrokeSelectedColor : Color = tay_pink_400,
    var uTBgSecondaryColor : Color = Color.White,
    var uTStrokeSecondaryColor : Color = tay_pink_300,
    var uTBgSecondaryDisableColor : Color = Color.White,
    var uTStrokeSecondaryDisableColor : Color = tay_grey_400,
    var uTBgSecondarySelectedColor : Color = Color.White,
    var uTStrokeSecondarySelectedColor : Color = tay_pink_400,
    var uTTextColor: Color = Color.White,
    var uTTextColorDisable: Color = Color.White,
    var uTTextColorSelected: Color = Color.White,
    var uTTextColorSecondary: Color = tay_pink_300,
    var uTTextColorDisableSecondary: Color = tay_grey_700,
    var uTTextColorSelectedSecondary: Color =  tay_pink_400,
    var uTTextFont : TextStyle = textB16,
    var uTColorIconDefault: Boolean = false,
    var uTIconStart : Int = 0,
    var uTIconEnd : Int = 0,
    var uTStrokeWith : Int = 1,
    var uTRadius : Int = 62){

    fun height(height: Int): UiTayButtonModel = apply {
        this.uTHeight = height
    }

    fun bgColor(color: Color): UiTayButtonModel = apply {
        this.uTBgColor = color
    }

    fun strokeColor(color: Color): UiTayButtonModel = apply {
        this.uTStrokeColor = color
    }

    fun bgDisableColor(color: Color): UiTayButtonModel = apply {
        this.uTBgDisableColor = color
    }

    fun strokeDisableColor(color: Color): UiTayButtonModel = apply {
        this.uTStrokeDisableColor = color
    }

    fun bgSelectedColor(color: Color): UiTayButtonModel = apply {
        this.uTBgSelectedColor = color
    }

    fun strokeSelectedColor(color: Color): UiTayButtonModel = apply {
        this.uTStrokeSelectedColor = color
    }

    fun bgSecondaryColor(color: Color): UiTayButtonModel = apply {
        this.uTBgSecondaryColor = color
    }

    fun strokeSecondaryColor(color: Color): UiTayButtonModel = apply {
        this.uTStrokeSecondaryColor = color
    }

    fun bgSecondaryDisableColor(color: Color): UiTayButtonModel = apply {
        this.uTBgSecondaryDisableColor = color
    }

    fun strokeSecondaryDisableColor(color: Color): UiTayButtonModel = apply {
        this.uTStrokeSecondaryDisableColor = color
    }

    fun bgSecondarySelectedColor(color: Color): UiTayButtonModel = apply {
        this.uTBgSecondarySelectedColor = color
    }

    fun strokeSecondarySelectedColor(color: Color): UiTayButtonModel = apply {
        this.uTStrokeSecondarySelectedColor = color
    }

    fun textColor(color: Color): UiTayButtonModel = apply {
        this.uTTextColor = color
    }

    fun textColorDisable(color: Color): UiTayButtonModel = apply {
        this.uTTextColorDisable = color
    }

    fun textColorSelected(color: Color): UiTayButtonModel = apply {
        this.uTTextColorSelected = color
    }

    fun textColorSecondary(color: Color): UiTayButtonModel = apply {
        this.uTTextColorSecondary = color
    }

    fun textColorDisableSecondary(color: Color): UiTayButtonModel = apply {
        this.uTTextColorDisableSecondary = color
    }

    fun textColorSelectedSecondary(color: Color): UiTayButtonModel = apply {
        this.uTTextColorSelectedSecondary = color
    }

    fun textFont(font: TextStyle): UiTayButtonModel = apply {
        this.uTTextFont = font
    }

    fun colorIconDefault(isDefault: Boolean): UiTayButtonModel = apply {
        this.uTColorIconDefault = isDefault
    }

    fun iconStart(icon: Int): UiTayButtonModel = apply {
        this.uTIconStart = icon
    }

    fun iconEnd(icon: Int): UiTayButtonModel = apply {
        this.uTIconEnd = icon
    }

    fun strokeWidth(width: Int): UiTayButtonModel = apply {
        this.uTStrokeWith = width
    }

    fun radius(radius: Int): UiTayButtonModel = apply {
        this.uTRadius = radius
    }
}

fun Boolean.utBtnState(selected : Boolean = false)=  if (selected) UTStateCButton.UI_BTN_SELECTED else
        if (this)UTStateCButton.UT_BTN_ENABLE else UTStateCButton.UI_BTN_DISABLE

fun UiTayButtonModel.uiTayStroke(type :UTStyleCButton,state : UTStateCButton) =
    if(type.uiCBtnPrimary())uiTayMStroke(state) else uiTayMStrokeS(state)

fun UiTayButtonModel.uiTayBackground(type :UTStyleCButton,state : UTStateCButton) =
    if(type.uiCBtnPrimary())uiTayMBackground(state) else uiTayMBackgroundS(state)


fun UiTayButtonModel.uiTayTextColor(type :UTStyleCButton,state : UTStateCButton) =
    if(type.uiCBtnPrimary())uiTayMText(state) else uiTayMTextS(state)


fun UiTayButtonModel.uiTayMStroke(state :UTStateCButton ): Color{
    return when(state){
        UTStateCButton.UT_BTN_ENABLE ->{uTStrokeColor}
       UTStateCButton.UI_BTN_DISABLE ->{uTStrokeDisableColor}
      UTStateCButton.UI_BTN_SELECTED ->{uTStrokeSelectedColor}
    }
}

fun UiTayButtonModel.uiTayMBackground(state :UTStateCButton ): Color{
    return when(state){
        UTStateCButton.UT_BTN_ENABLE ->{uTBgColor}
        UTStateCButton.UI_BTN_DISABLE ->{uTBgDisableColor}
        UTStateCButton.UI_BTN_SELECTED ->{uTBgSelectedColor}
    }
}

fun UiTayButtonModel.uiTayMStrokeS(state :UTStateCButton ): Color{
    return when(state){
        UTStateCButton.UT_BTN_ENABLE ->{uTStrokeSecondaryColor}
        UTStateCButton.UI_BTN_DISABLE ->{uTStrokeSecondaryDisableColor}
        UTStateCButton.UI_BTN_SELECTED ->{uTStrokeSecondarySelectedColor}
    }
}

fun UiTayButtonModel.uiTayMBackgroundS(state :UTStateCButton ): Color{
    return when(state){
        UTStateCButton.UT_BTN_ENABLE ->{uTBgSecondaryColor}
        UTStateCButton.UI_BTN_DISABLE ->{uTBgSecondaryDisableColor}
        UTStateCButton.UI_BTN_SELECTED ->{uTBgSecondarySelectedColor}
    }
}

fun UiTayButtonModel.uiTayMTextS(state :UTStateCButton ): Color{
    return when(state){
        UTStateCButton.UT_BTN_ENABLE ->{uTTextColorSecondary}
        UTStateCButton.UI_BTN_DISABLE ->{uTTextColorDisableSecondary}
        UTStateCButton.UI_BTN_SELECTED ->{uTTextColorSelectedSecondary}
    }
}

fun UiTayButtonModel.uiTayMText(state :UTStateCButton ): Color{
    return when(state){
        UTStateCButton.UT_BTN_ENABLE ->{uTTextColor}
        UTStateCButton.UI_BTN_DISABLE ->{uTTextColorDisable}
        UTStateCButton.UI_BTN_SELECTED ->{uTTextColorSelected}
    }
}

fun UTStyleCButton.uiCBtnPrimary()= this == UTStyleCButton.UI_TAY_PRIMARY

enum class UTStateCButton(var code: Int) {
    UT_BTN_ENABLE(0),
    UI_BTN_DISABLE(1),
    UI_BTN_SELECTED(2)
}

enum class UTStyleCButton(var code: Int) {
    UI_TAY_PRIMARY(0),
    UI_TAY_SECONDARY(1)
}

enum class UTStyleIcon(var code: Int) {
    NONE(0),
    START(1),
    END(2),
    FULL(3),
}