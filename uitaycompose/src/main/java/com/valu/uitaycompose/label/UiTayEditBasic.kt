package com.valu.uitaycompose.label

import android.view.ViewTreeObserver
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.valu.uitaycompose.R
import com.valu.uitaycompose.model.UiEditBasicModel
import com.valu.uitaycompose.utils.UI_EMPTY

@Suppress("UNUSED_VALUE")
@Composable
fun UiTayEditBasic(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    title: String = UI_EMPTY,
    hint: String = UI_EMPTY,
    errorMessage: String = UI_EMPTY,
    enabled: Boolean = true,
    maxLength: Int = 40,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    iconStart: Painter? = null,
    iconEnd: Painter? = null,
    onIconStartClick: (() -> Unit)? = null,
    onIconEndClick: (() -> Unit)? = null,
    isPassword: Boolean = false,
    isError: Boolean = false,
    model: UiEditBasicModel = UiEditBasicModel()
) {
    var passwordVisible by remember { mutableStateOf(!isPassword) }
    var active by remember { mutableStateOf(false) }
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

    val bgColor by remember(active, enabled) {
        derivedStateOf {
            when {
                active -> model.uiBgActiveColor
                enabled -> model.uiBgColor
                else -> model.uiBgDisableColor
            }
        }
    }

    val strokeColor by remember(active, enabled) {
        derivedStateOf {
            when {
                active -> model.uiStrokeActiveColor
                enabled -> model.uiStrokeColor
                isError -> model.uiMessageColor
                else -> model.uiStrokeDisableColor
            }
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                color = titleColor,
                style = model.uiTitleFont
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        BasicTextField(
            value = value,
            onValueChange = {
                if (it.length <= maxLength) onValueChange(it)
            }, modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    active = focusState.isFocused
                },
            enabled = enabled,
            textStyle = model.uiTextFont.copy(color = textColor),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation =
                if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = bgColor,
                            shape = RoundedCornerShape(28.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = strokeColor,
                            shape = RoundedCornerShape(28.dp)
                        )
                        .padding(
                            horizontal = model.uiPaddingHorizontal,
                            vertical = model.uiPaddingVertical
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    if (iconStart != null) {
                        Icon(
                            painter = iconStart,
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(enabled = onIconStartClick != null) {
                                    onIconStartClick?.invoke()
                                },
                            tint = iconColor
                        )
                        Spacer(modifier = Modifier.width(model.uiIconMarginStar))
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = hint, color = model.uiHintColor,
                                style = model.uiTextFont
                            )
                        }
                        innerTextField()
                    }

                    if (isPassword) {
                        val eyeIcon = if (passwordVisible)
                            painterResource(id = R.drawable.ui_tay_ic_eyes_active)
                        else painterResource(id = R.drawable.ui_tay_ic_eyes_inactive)

                        Icon(
                            painter = eyeIcon,
                            contentDescription = "Toggle Password",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { passwordVisible = !passwordVisible },
                            tint = iconColor
                        )
                    } else if (iconEnd != null) {
                        Icon(
                            painter = iconEnd,
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(enabled = onIconEndClick != null) { onIconEndClick?.invoke() },
                            tint = iconColor
                        )
                    }
                }
            }
        )

        if (isError) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = model.uiMessageColor,
                style = model.uiMessageFont
            )
        }
    }
}