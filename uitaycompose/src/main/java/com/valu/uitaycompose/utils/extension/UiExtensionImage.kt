package com.valu.uitaycompose.utils.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import androidx.exifinterface.media.ExifInterface
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.GlobalHistogramBinarizer
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeWriter
import com.valu.uitaycompose.R
import com.valu.uitaycompose.model.entity.UITayMetaDataImage
import com.valu.uitaycompose.utils.ERROR_QR_IMG
import com.valu.uitaycompose.utils.ERROR_TRY_ORIENTATION
import com.valu.uitaycompose.utils.TAY_LOG
import com.valu.uitaycompose.utils.UI_EMPTY
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.collections.set
import kotlin.math.min

fun Context.uiTayRotateIfNeeded(bitmap: Bitmap, uri: Uri): Bitmap {
    val exitInt = this.contentResolver.openInputStream(uri)?.let { ExifInterface(it) }
    return when (exitInt?.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL
    )) {
        ExifInterface.ORIENTATION_ROTATE_90 -> {
            uiTayRotateImages(bitmap, 90)
        }

        ExifInterface.ORIENTATION_ROTATE_180 -> {
            uiTayRotateImages(bitmap, 180)
        }

        ExifInterface.ORIENTATION_ROTATE_270 -> {
            uiTayRotateImages(bitmap, 270)
        }

        else -> {
            bitmap
        }
    }
}

fun uiTayRotateImages(imageToOrient: Bitmap, degreesToRotate: Int): Bitmap {
    var result: Bitmap = imageToOrient
    try {
        if (degreesToRotate != 0) {
            val matrix = Matrix()
            matrix.setRotate(degreesToRotate.toFloat())
            result = Bitmap.createBitmap(
                imageToOrient,
                0,
                0,
                imageToOrient.width,
                imageToOrient.height,
                matrix,
                true
            )
        }
    } catch (e: java.lang.Exception) {
        Log.e(TAY_LOG, ERROR_TRY_ORIENTATION, e)
    }
    return result
}


fun uiTayReduceBitmapSize(imageFilePath: File): Bitmap {
    val bmOptions = BitmapFactory.Options()
    bmOptions.inJustDecodeBounds = true
    BitmapFactory.decodeFile(imageFilePath.absolutePath, bmOptions)
    bmOptions.inSampleSize = uiTayCalculateInSampleSize(bmOptions)
    bmOptions.inJustDecodeBounds = false
    return BitmapFactory.decodeFile(imageFilePath.absolutePath, bmOptions)
}

fun uiTayCalculateInSampleSize(bmOptions: BitmapFactory.Options): Int {
    val photoWidth = bmOptions.outWidth
    val photoHeight = bmOptions.outHeight
    var scaleFactor = 1
    if (photoWidth > 1000 || photoHeight > 1000) {
        val halfPhotoWidth = photoWidth / 2
        val halfPhotoHeight = photoHeight / 2
        while (halfPhotoWidth / scaleFactor >= 500 && halfPhotoHeight / scaleFactor >= 500) {
            scaleFactor *= 2
        }
    }
    return scaleFactor
}

fun Bitmap.uiTayConverterCircle(): Bitmap {
    val size: Int = min(this.width, this.height)
    val bitmap = ThumbnailUtils.extractThumbnail(this, size, size)
    val output = createBitmap(bitmap.width, bitmap.height)
    val canvas = Canvas(output)
    val color = -0x10000
    val paint = Paint()
    val rect = Rect(0, 0, bitmap.width, bitmap.height)
    val rectF = RectF(rect)
    paint.isAntiAlias = true
    paint.isDither = true
    paint.isFilterBitmap = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = color
    canvas.drawOval(rectF, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(bitmap, rect, rect, paint)
    return output
}

fun Bitmap.uiTayWriteCodeQrImage(): String {
    val safeBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && this.config == Bitmap.Config.HARDWARE) {
        this.copy(Bitmap.Config.ARGB_8888, false)
    } else {
        this
    }

    val maxDimension = 1080
    val scaledBitmap = if (safeBitmap.width > maxDimension || safeBitmap.height > maxDimension) {
        val scale = maxDimension.toFloat() / maxOf(safeBitmap.width, safeBitmap.height)
        Bitmap.createScaledBitmap(
            safeBitmap,
            (safeBitmap.width * scale).toInt(),
            (safeBitmap.height * scale).toInt(),
            true
        )
    } else {
        safeBitmap
    }

    val hints = mapOf(
        DecodeHintType.TRY_HARDER to true,
        DecodeHintType.POSSIBLE_FORMATS to listOf(BarcodeFormat.QR_CODE)
    )
    val reader = MultiFormatReader().apply { setHints(hints) }

    var currentBitmap = scaledBitmap
    for (angle in listOf(0f, 90f, 180f, 270f)) {
        if (angle != 0f) {
            val matrix = Matrix().apply { postRotate(angle) }
            currentBitmap = Bitmap.createBitmap(
                scaledBitmap, 0, 0, scaledBitmap.width, scaledBitmap.height, matrix, true
            )
        }

        val width = currentBitmap.width
        val height = currentBitmap.height
        val pixels = IntArray(width * height)
        currentBitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        val source = RGBLuminanceSource(width, height, pixels)
        try {
            val bBitmap = BinaryBitmap(HybridBinarizer(source))
            val result = reader.decode(bBitmap)
            if (result.text.isNotEmpty()) return result.text
        } catch (_: NotFoundException) { }


        try {
            val bBitmap = BinaryBitmap(GlobalHistogramBinarizer(source))
            val result = reader.decode(bBitmap)
            if (result.text.isNotEmpty()) return result.text
        } catch (_: NotFoundException) { }

        try {
            val bBitmap = BinaryBitmap(HybridBinarizer(source.invert()))
            val result = reader.decode(bBitmap)
            if (result.text.isNotEmpty()) return result.text
        } catch (_: NotFoundException) { }
    }

    Log.e(TAY_LOG, ERROR_QR_IMG)
    return UI_EMPTY
}

fun String.uiTayGenerateQrImage(size : Int = 512):Bitmap?{
    return if (this.isNotEmpty()){
        val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 }
        val bits = QRCodeWriter().encode(this, BarcodeFormat.QR_CODE, size, size, hints)
        createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }else{
        null
    }
}

fun String.uiTayMetaDataImage(): UITayMetaDataImage {
    var uiTayData = UITayMetaDataImage()
    try {
        val exifInterface = ExifInterface(this)
        uiTayData =  UITayMetaDataImage(
            length  = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH)?: UI_EMPTY,
            width = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)?: UI_EMPTY,
            dateTime  = exifInterface.getAttribute(ExifInterface.TAG_DATETIME)?: UI_EMPTY,
            take  = exifInterface.getAttribute(ExifInterface.TAG_MAKE)?: UI_EMPTY,
            model  = exifInterface.getAttribute(ExifInterface.TAG_MODEL)?: UI_EMPTY,
            orientation  = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION)?: UI_EMPTY,
            whiteBalance  = exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE)?: UI_EMPTY,
            focalLength  = exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH)?: UI_EMPTY,
            flash  = exifInterface.getAttribute(ExifInterface.TAG_FLASH)?: UI_EMPTY,
            gpsDatesTamp  = exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP)?: UI_EMPTY,
            gpsTimesTamp  = exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP)?: UI_EMPTY,
            gpsLatitude  = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE)?: UI_EMPTY,
            gpsLatitudeReferential  = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF)?: UI_EMPTY,
            gpsLongitude  = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)?: UI_EMPTY,
            gpsLongitudeReferential  = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF)?: UI_EMPTY,
            gpsProcessingMethod  = exifInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD)?: UI_EMPTY
        )

    } catch (e: IOException) {
        e.printStackTrace()
    }
    return uiTayData
}

suspend fun GraphicsLayer.uiTayToBitmap(): Bitmap {
    return this.toImageBitmap().asAndroidBitmap()
}

fun String.uiTayToBitmap(): Bitmap? {
    return try {
        val file = File(this)
        if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else {
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Context.uiTayCreatePictureFolder(nameFile: String = "imgSave"): File {
    val storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val newPath = File(storageDir, nameFile)
    if (!newPath.exists()) {
        newPath.mkdirs()
    }
    return newPath
}

fun Context.uiTaySaveImg(
    directory: File,
    img: Bitmap,
    nameImage: String,
    toast: Boolean = false,
    message: String = UI_EMPTY,
): String {
    val myPath = File(directory, "$nameImage.jpg")

    try {
        FileOutputStream(myPath).use { fos ->
            img.compress(Bitmap.CompressFormat.JPEG, 90, fos)
        }

        if (toast && message.isNotBlank()) {
            this.uiTayShowToast(message)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        if (toast) {
            this.uiTayShowToast(this.getString(R.string.tay_ui_error_pdf_link))
        }
    }

    return myPath.absolutePath
}

fun String.uiTayBase64toBitmap(): Bitmap? {
    val decodedBytes = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}

fun Bitmap.uiTayBitmapToBase64(quality: Int = 100): String? {
    return try {
        ByteArrayOutputStream().use { stream ->
            this.compress(Bitmap.CompressFormat.JPEG, quality, stream)
            val bytes = stream.toByteArray()
            Base64.encodeToString(bytes, Base64.DEFAULT)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
fun UiTayShowRoundImage(bitmap: Bitmap,size: Dp = 100.dp) {
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
    )
}