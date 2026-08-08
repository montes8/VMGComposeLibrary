package com.valu.uitaycompose.utils.permission

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.valu.uitaycompose.utils.UI_EMPTY
import com.valu.uitaycompose.utils.extension.uiTayReduceBitmapSize
import com.valu.uitaycompose.utils.extension.uiTayRotateIfNeeded
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar

@Composable
fun rememberUiTayOriginalCameraManager(
    uiTayNameFilePath: String,
    appMultipleCamera: Boolean = true,
    listener:  UiTayOriginalCameraManagerCompose.UiTayOriginalCameraListener
): UiTayOriginalCameraManagerCompose {
    val context = LocalContext.current

    var onGrantedCallback by remember { mutableStateOf({}) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        if (map.values.all { it }) {
            onGrantedCallback()
        } else {
            listener.onCameraPermissionDenied()
        }
    }

    var picturePathTemp by remember { mutableStateOf("") }
    var pictureNameTemp by remember { mutableStateOf("") }
    var pictureFileNamePhone by remember { mutableStateOf("") }
    var uiTayNamePhoto by remember { mutableStateOf("") }
    var typeBanner by remember { mutableStateOf(true) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
            val storageDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), uiTayNameFilePath)
            val data = result.data

            try {
                val targetFile: File = if (data == null) {
                    File(storageDir, pictureNameTemp)
                } else {
                    val calendar = Calendar.getInstance()
                    val pictureFileName = uiTayNamePhoto.ifEmpty { calendar.timeInMillis.toString() }
                    val photoFile = File(storageDir, "$pictureFileName.jpg")

                    data.data?.let { uri ->
                        context.contentResolver.openInputStream(uri)?.use { inputStream ->
                            FileOutputStream(photoFile).use { outputStream ->
                                inputStream.copyTo(outputStream)
                            }
                        }
                    }
                    photoFile
                }

                if (targetFile.exists()) {
                    val fileUri = Uri.fromFile(targetFile)

                    // 1. BITMAP PARA QR: Alta calidad sin comprimir artefactos JPEG
                    val rawBitmap = BitmapFactory.decodeFile(targetFile.absolutePath)
                    val originalBitmap = context.uiTayRotateIfNeeded(rawBitmap, fileUri)

                    // 2. BITMAP PARA UI: Reducido para cuadros pequeños o perfiles
                    val reducedBitmap = context.uiTayRotateIfNeeded(uiTayReduceBitmapSize(targetFile), fileUri)
                    val path = "$externalFilesDir/$uiTayNameFilePath/${targetFile.name}"
                    FileOutputStream(targetFile).use { out ->
                        reducedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
                    }

                    // Se entregan ambas versiones al listener
                    listener.onGetImageCameraCompleted(
                        path = path,
                        displayBitmap = reducedBitmap,
                        originalBitmap = originalBitmap
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    return remember {
        UiTayOriginalCameraManagerCompose(
            onDoCamera = { namePhoto, isBanner ->
                typeBanner = isBanner
                uiTayNamePhoto = namePhoto

                val calendar = Calendar.getInstance()
                pictureFileNamePhone = namePhoto.ifEmpty { calendar.timeInMillis.toString() }
                val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val picture = File("$storageDir/$uiTayNameFilePath", "$pictureFileNamePhone.jpg")
                val newPath = File("$storageDir/$uiTayNameFilePath")
                if (!newPath.exists()) newPath.mkdirs()

                picturePathTemp = picture.absolutePath
                pictureNameTemp = picture.name

                val pictureUri = FileProvider.getUriForFile(
                    context,
                    "${context.applicationContext.packageName}.provider",
                    picture
                )

                onGrantedCallback = {
                    if (appMultipleCamera) {
                        val cameraIntents = ArrayList<Intent>()
                        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                            putExtra(MediaStore.EXTRA_OUTPUT, pictureUri)
                        }
                        context.packageManager.queryIntentActivities(captureIntent, 0).forEach { res ->
                            val intent = Intent(captureIntent).apply {
                                component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
                                setPackage(res.activityInfo.packageName)
                                putExtra(MediaStore.EXTRA_OUTPUT, pictureUri)
                            }
                            cameraIntents.add(intent)
                        }
                        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        val chooserIntent = Intent.createChooser(galleryIntent, "Elije una imagen").apply {
                            putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toTypedArray<Parcelable>())
                        }
                        cameraLauncher.launch(chooserIntent)
                    } else {
                        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                            putExtra(MediaStore.EXTRA_OUTPUT, pictureUri)
                        }
                        cameraLauncher.launch(captureIntent)
                    }
                }

                permissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
            }
        )
    }
}



class UiTayOriginalCameraManagerCompose(
    private val onDoCamera: (String, Boolean) -> Unit
) {
    fun doCamera(namePhoto: String = UI_EMPTY, isBanner: Boolean = true) {
        onDoCamera(namePhoto, isBanner)
    }
    interface UiTayOriginalCameraListener {
        fun onCameraPermissionDenied()
        fun onGetImageCameraCompleted(path: String, displayBitmap: Bitmap, originalBitmap: Bitmap)
    }
}