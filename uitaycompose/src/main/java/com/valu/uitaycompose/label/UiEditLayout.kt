package com.valu.uitaycompose.label

import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.valu.uitaycompose.R
import com.valu.uitaycompose.model.UiEditLayoutModel
import com.valu.uitaycompose.utils.UI_EMPTY

@Composable
fun UiTayEditLayout(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = UI_EMPTY,
    errorMessage: String = UI_EMPTY,
    enabled: Boolean = true,
    isPassword: Boolean = false,
    maxLength: Int = 200,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    startIcon: Int? = null,
    endIcon: Int? = null,
    onIconClick: (() -> Unit)? = null,
    isError: Boolean = false,
    model: UiEditLayoutModel = UiEditLayoutModel()
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var active by remember { mutableStateOf(false) }

    val titleColor by remember(active, enabled) {
        derivedStateOf {
            when {
                active -> model.uiTitleActiveColor
                enabled -> model.uiTitleColor
                isError -> model.uiMessageColor
                else -> model.uiTitleDisableColor
            }
        }
    }

    val textColor by remember(active, enabled) {
        derivedStateOf {
            when {
                active -> model.uiTextActiveColor
                enabled -> model.uiTextColor
                else -> model.uiTextDisableColor
            }
        }
    }

    val iconColor by remember(active, enabled) {
        derivedStateOf {
            when {
                active -> model.uiIconActiveColor
                enabled -> model.uiIconColor
                isError -> model.uiMessageColor
                else -> model.uiIconDisableColor
            }
        }
    }

    val customColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = model.uiBgActiveColor,
        unfocusedContainerColor = model.uiBgColor,
        disabledContainerColor = model.uiBgDisableColor,
        errorContainerColor = model.uiBgColor,
        focusedBorderColor = model.uiStrokeActiveColor,
        unfocusedBorderColor = model.uiStrokeColor,
        disabledBorderColor = model.uiStrokeDisableColor,
        errorBorderColor = model.uiMessageColor,
    )

    val focusManager = LocalFocusManager.current
    val view = LocalView.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = android.graphics.Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            if (keypadHeight < screenHeight * 0.15) {
                active = false
                focusManager.clearFocus()
            }
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                if (it.length <= maxLength) onValueChange(it)
            },
            // Usamos heightIn para controlar la altura mínima y hacer compacto el campo
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 50.dp, max = 56.dp)
                .onFocusChanged { focusState ->
                    active = focusState.isFocused
                },
            enabled = enabled,
            label = { Text(text = hint, style = model.uiTitleFont.copy(color = titleColor)) },
            shape = RoundedCornerShape(28.dp),
            isError = isError,
            textStyle = model.uiTextFont.copy(color = textColor),
            visualTransformation = if (isPassword && !passwordVisible)
                PasswordVisualTransformation() else VisualTransformation.None,

            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            leadingIcon = startIcon?.let {
                { Icon(painter = painterResource(id = it), contentDescription = null, tint = iconColor) }
            },
            trailingIcon = {
                if (isPassword) {
                    val icon = if (passwordVisible)
                        R.drawable.ui_tay_ic_eyes_active
                    else R.drawable.ui_tay_ic_eyes_inactive

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(painter = painterResource(id = icon), contentDescription = null, tint = iconColor)
                    }
                } else if (endIcon != null) {
                    IconButton(onClick = { onIconClick?.invoke() }) {
                        Icon(painter = painterResource(id = endIcon), contentDescription = null, tint = iconColor)
                    }
                }
            },
            colors = customColors,
            singleLine = true,
            maxLines = 1
        )

        if (isError) {
            Text(
                text = errorMessage,
                color = model.uiMessageColor,
                style = model.uiMessageFont,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}