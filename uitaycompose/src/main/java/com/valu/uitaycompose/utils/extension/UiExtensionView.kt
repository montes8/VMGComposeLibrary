package com.valu.uitaycompose.utils.extension

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import com.valu.uitaycompose.R
import com.valu.uitaycompose.modal.UiTayDialog
import com.valu.uitaycompose.modal.UiTayDialogZoomDetail
import com.valu.uitaycompose.model.UiTayDialogModel
import kotlin.math.pow
import kotlin.math.sqrt

fun Modifier.uiTayDialogZoom(
    showDialog: Boolean,
    imageUrl: String,
    enableAdvancedZoom: Boolean = true,
    onShowDialogChange: (Boolean) -> Unit,
): Modifier = composed {
    if (showDialog) {
        UiTayDialogZoomDetail(
            imageUrl = imageUrl,
            enableAdvancedZoom = enableAdvancedZoom,
            onDismiss = {

                onShowDialogChange(false)
            }
        )
    }
    this
}

fun Modifier.uiTayShowDialog(
    showDialog: Boolean,
    model: UiTayDialogModel = UiTayDialogModel(),
    onDismissRequest: (Boolean) -> Unit
): Modifier = composed {
    if (showDialog) {
        UiTayDialog(
            model = model,
        onDismissRequest = {
             onDismissRequest(false)
          }
        )
    }
    this
}

fun Context.uiTayShowToast(value : String){
    Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
}

fun Context.uiTayShowToast(value : Int){
    Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
}

fun Context.uiTayCopy(text : String, message:String = "Copiado en portapapeles"){
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
    this.uiTayShowToast(message)
}

fun Context.uiTayCopy(text : String, message:Int){
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
    this.uiTayShowToast(message)
}


fun Activity.uiTayCheckIsTablet(): Boolean {
    val metrics = DisplayMetrics()
    if (SDK_INT >= android.os.Build.VERSION_CODES.R) {
        val display = this.display
        display?.getRealMetrics(metrics)
    } else {
        val display = this.windowManager.defaultDisplay
        display.getMetrics(metrics)
    }
    var isTablet = false
    val widthInches: Float = metrics.widthPixels / metrics.xdpi
    val heightInches: Float = metrics.heightPixels / metrics.ydpi
    val diagonalInches =
        sqrt(
            widthInches.toDouble().pow(2.0) + heightInches.toDouble().pow(2.0)
        )
    if (diagonalInches >= 7.0) {
        isTablet = true
    }

    return isTablet
}

fun FocusManager.uiTayHideKeyboard() {
    this.clearFocus()
}





