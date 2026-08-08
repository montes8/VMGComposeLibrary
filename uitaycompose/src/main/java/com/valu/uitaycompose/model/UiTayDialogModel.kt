package com.valu.uitaycompose.model

import com.valu.uitaycompose.R
import com.valu.uitaycompose.utils.UI_TAY_DIALOG_ACCEPT
import com.valu.uitaycompose.utils.UI_TAY_DIALOG_CANCEL
import com.valu.uitaycompose.utils.UI_TAY_DIALOG_SUB_TITLE
import com.valu.uitaycompose.utils.UI_TAY_DIALOG_TITLE

data class UiTayDialogModel(
    val image: Int = R.drawable.ui_tay_ic_info,
    val title: String = UI_TAY_DIALOG_TITLE,
    val subTitle: String = UI_TAY_DIALOG_SUB_TITLE,
    val buttonText: String = UI_TAY_DIALOG_ACCEPT,
    val buttonCancelText: String = UI_TAY_DIALOG_CANCEL,
    val btnCancel: Boolean = false,
    val isCancel: Boolean = true,
    val styleCustom: UiTayDialogModelCustom = UiTayDialogModelCustom()
)