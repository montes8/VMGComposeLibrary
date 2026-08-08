package com.valu.uitaycompose.utils.extension

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import com.valu.uitaycompose.utils.UI_EMPTY
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.Collections

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
