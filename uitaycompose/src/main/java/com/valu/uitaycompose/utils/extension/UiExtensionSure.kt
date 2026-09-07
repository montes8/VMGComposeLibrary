package com.valu.uitaycompose.utils.extension

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.valu.uitaycompose.utils.UI_EMPTY
import com.valu.uitaycompose.utils.tag.uiTayLog
import java.net.Inet4Address
import java.net.NetworkInterface
import java.security.MessageDigest
import java.util.Collections
import kotlin.text.contains

fun uiTayGetIpAddress(useIPv4: Boolean = true): String {
    try {
        val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
        for (networkInterface in interfaces) {
            val addresses = Collections.list(networkInterface.inetAddresses)
            for (address in addresses) {
                if (!address.isLoopbackAddress) {
                    val hostAddress = address.hostAddress ?: continue
                    val isIPv4 = address is Inet4Address

                    if (useIPv4) {
                        if (isIPv4) return hostAddress
                    } else {
                        if (!isIPv4) {
                            val ipAddress = hostAddress.indexOf('%')
                            return if (ipAddress < 0) hostAddress.uppercase() else hostAddress.substring(0, ipAddress).uppercase()
                        }
                    }
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return UI_EMPTY
}

@SuppressLint("QueryPermissionsNeeded")
fun uiTayExistApplicationInDevice(context: Context, name: String): Boolean {
    for (app in context.packageManager.getInstalledPackages(0)) {
        if (app.packageName == name) {
            return true
        }
    }
    return false
}

fun Context.uiTayIsDeviceLocked(): Boolean {
    val manager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
    return manager.isKeyguardLocked
}

fun Context.uiTayHasPinSecureLock(): Boolean {
    val manager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
    return manager.isDeviceSecure
}

fun String.uiTayValidateCardNumber(): Boolean {
    val ints = IntArray(this.length)
    for (i in indices) {
        ints[i] = Integer.parseInt(this.substring(i, i + 1))
    }
    run {
        var i = ints.size - 2
        while (i >= 0) {
            var j = ints[i]
            j *= 2
            if (j > 9) {
                j = j % 10 + 1
            }
            ints[i] = j
            i -= 2
        }
    }
    var sum = 0
    for (i in ints.indices) {
        sum += ints[i]
    }

    return sum % 10 == 0
}

fun Context.uiTayIsDeveloperMode(): Boolean {
    return try {
        val devMode = android.provider.Settings.Global.getInt(
            this.contentResolver,
            android.provider.Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
        ) != 0

        val adbEnabled = android.provider.Settings.Global.getInt(
            this.contentResolver,
            android.provider.Settings.Global.ADB_ENABLED, 0
        ) != 0

        val devModeSecure = try {
            android.provider.Settings.Secure.getInt(
                this.contentResolver, "development_settings_enabled", 0
            ) != 0
        } catch (_: Exception) { false }

        devMode || adbEnabled || devModeSecure
    } catch (_: Exception) {
        false
    }
}

fun Context.uiTayIsDeviceRooted(): Boolean {
    val buildTags = Build.TAGS
    if (buildTags != null && buildTags.contains("test-keys")) {
        "Root detected by BuildTags".uiTayLog("SECURITY_CHECK")
        return true
    }

    val rootPackages = arrayOf(
        "com.topjohnwu.magisk",
        "eu.chainfire.supersu",
        "com.noshufou.android.su",
        "com.koushikdutta.superuser",
        "com.zachareew.systemuituner",
        "com.amaze.filemanager",
        "com.chelpus.lackypatch",
        "com.joeykrim.rootcheck"
    )
    val pm = this.packageManager
    for (pkg in rootPackages) {
        try {
            pm.getPackageInfo(pkg, 0)
            "Root detected by Package: $pkg".uiTayLog("SECURITY_CHECK")
            return true
        } catch (_: Exception) { }
    }

    val paths = arrayOf(
        "/system/app/Superuser.apk",
        "/sbin/su",
        "/system/bin/su",
        "/system/xbin/su",
        "/data/local/xbin/su",
        "/data/local/bin/su",
        "/system/sd/xbin/su",
        "/system/bin/failsafe/su",
        "/data/local/su",
        "/su/bin/su",
        "/data/adb/magisk",
        "/sbin/.magisk/",
        "/cache/magisk.log",
        "/data/adb/modules"
    )
    for (path in paths) {
        if (java.io.File(path).exists()) {
            "Root detected by Path: $path".uiTayLog("SECURITY_CHECK")
            return true
        }
    }

    val extraPaths = arrayOf(
        "/system/usr/we-need-root/", "/cache/", "/data/", "/dev/", "/data/adb/", "/system/bin/.ext/"
    )
    val extraBinaries = arrayOf("busybox", "magisk", "daemonsu")

    for (path in extraPaths) {
        for (bin in extraBinaries) {
            if (java.io.File(path + bin).exists()) {
                "Root detected by Extra Bin: $path$bin".uiTayLog("SECURITY_CHECK")
                return true
            }
        }
    }

    try {
        val filesToScan = arrayOf("/proc/self/mounts", "/proc/self/maps")
        for (filePath in filesToScan) {
            val file = java.io.File(filePath)
            if (file.exists()) {
                val reader = java.io.BufferedReader(java.io.FileReader(file))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    val lowerLine = line!!.lowercase()
                    if (lowerLine.contains("magisk") ||
                        lowerLine.contains("zygisk") ||
                        lowerLine.contains("core/mirror") ||
                        lowerLine.contains("core/img")) {
                        "Root detected by Memory Scan: $filePath".uiTayLog("SECURITY_CHECK")
                        reader.close()
                        return true
                    }
                }
                reader.close()
            }
        }
    } catch (_: Exception) { }

    if (getSystemProperty("ro.debuggable") == "1" || getSystemProperty("ro.secure") == "0") {
        "Root detected by System Prop".uiTayLog("SECURITY_CHECK")
        return true
    }

    var process: Process? = null
    return try {
        process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
        val reader = java.io.BufferedReader(java.io.InputStreamReader(process.inputStream))
        val detected = reader.readLine() != null
        if (detected) "Root detected by Execution".uiTayLog("SECURITY_CHECK")
        detected
    } catch (_: Throwable) {
        false
    } finally {
        process?.destroy()
    }
}

fun Context.uiTayIsAppTampered(expectedHash: String): Boolean {
    try {
        val packageName = this.packageName
        val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val pkgInfo = this.packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNING_CERTIFICATES
            )
            pkgInfo.signingInfo?.apkContentsSigners
        } else {
            @Suppress("DEPRECATION")
            val pkgInfo = this.packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            )
            pkgInfo.signatures
        }

        if (!signatures.isNullOrEmpty()) {
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(signatures[0].toByteArray())
            val currentHash = digest.joinToString(":") { "%02X".format(it) }
            return currentHash != expectedHash
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}


private fun getSystemProperty(key: String): String? {
    return try {
        val process = Runtime.getRuntime().exec(arrayOf("/system/bin/getprop", key))
        val reader = java.io.BufferedReader(java.io.InputStreamReader(process.inputStream))
        reader.readLine()
    } catch (e: Exception) { null }
}