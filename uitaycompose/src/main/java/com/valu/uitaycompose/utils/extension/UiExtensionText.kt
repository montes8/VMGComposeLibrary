package com.valu.uitaycompose.utils.extension

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import com.valu.uitaycompose.utils.textB14
import com.valu.uitaycompose.utils.textM14

@Composable
fun UiTayColouredSpanClickText(
    fullText: String,
    clickableWord: String,
    textColor: Color,
    isUnderLine: Boolean = true,
    isBold: Boolean = false,
    style: TextStyle = textM14,
    boldStyle: TextStyle = textB14,
    onClick: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        val startIndex = fullText.indexOf(clickableWord)

        if (startIndex >= 0) {
            val endIndex = startIndex + clickableWord.length
            append(fullText.substring(0, startIndex))
            val targetStyle = if (isBold) boldStyle else style
            val spanStyle = targetStyle.toSpanStyle().copy(
                color = textColor,
                textDecoration = if (isUnderLine) TextDecoration.Underline else TextDecoration.None
            )

            val linkAnnotation = LinkAnnotation.Clickable(
                tag = "CLICKABLE_WORD",
                styles = TextLinkStyles(style = spanStyle),
                linkInteractionListener = {
                    onClick()
                }
            )
            withLink(linkAnnotation) {
                append(clickableWord)
            }

            append(fullText.substring(endIndex))
        } else {
            append(fullText)
        }
    }

    Text(
        text = annotatedString,
        style = style
    )
}

@Composable
fun UiTayColouredSpanText(
    fullText: String,
    coloredWord: String,
    textColor: Color,
    style: TextStyle = TextStyle.Default
) {
    val annotatedString = buildAnnotatedString {
        val startIndex = fullText.indexOf(coloredWord)
        if (startIndex >= 0) {
            val endIndex = startIndex + coloredWord.length
            append(fullText.substring(0, startIndex))
            withStyle(
                style = style.toSpanStyle().copy(color = textColor)
            ) {
                append(coloredWord)
            }
            append(fullText.substring(endIndex))
        } else {
            append(fullText)
        }
    }

    Text(
        text = annotatedString,
        style = style
    )
}

@Composable
fun uiTayGetCustomSpanString(
    fullText: String,
    word: String,
    baseStyle: TextStyle = textM14,
    customStyle: TextStyle = textB14
): AnnotatedString {
    return buildAnnotatedString {
        val start = fullText.indexOf(word)
        withStyle(style = baseStyle.toSpanStyle()) {
            if (start >= 0) {
                val end = start + word.length
                append(fullText.substring(0, start))
                withStyle(style = customStyle.toSpanStyle()) {
                    append(word)
                }
                append(fullText.substring(end))
            } else {
                append(fullText)
            }
        }
    }
}