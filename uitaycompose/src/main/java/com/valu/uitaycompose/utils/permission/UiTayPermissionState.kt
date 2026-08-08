package com.valu.uitaycompose.utils.permission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberUiTayPermissionManager(
    onDeny: () -> Unit = {}
): UiTayPermissionController {
    var onGrantedCallback by remember { mutableStateOf({}) }

    val singleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) onGrantedCallback() else onDeny()
    }

    val multipleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        val allGranted = map.values.all { it }
        if (allGranted) onGrantedCallback() else onDeny()
    }

    return remember {
        UiTayPermissionController(
            onSingleRequest = { permission, onGranted ->
                onGrantedCallback = onGranted
                singleLauncher.launch(permission)
            },
            onMultipleRequest = { permissions, onGranted ->
                onGrantedCallback = onGranted
                multipleLauncher.launch(permissions)
            }
        )
    }
}

class UiTayPermissionController(
    private val onSingleRequest: (String, () -> Unit) -> Unit,
    private val onMultipleRequest: (Array<String>, () -> Unit) -> Unit
) {
    fun requestPermission(required: String, onGranted: () -> Unit) {
        onSingleRequest(required, onGranted)
    }

    fun requestPermissions(required: Array<String>, onGranted: () -> Unit) {
        onMultipleRequest(required, onGranted)
    }
}