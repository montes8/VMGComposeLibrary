package com.valu.uitaycompose.utils.extension

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.WindowManager
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.valu.uitaycompose.utils.COUNTRY_DEFAULT
import com.valu.uitaycompose.utils.UI_EMPTY
import java.io.File

fun Context. uiTaychangeIcon(activeAliasName: String, oldAliasName: String) {
    try {
        val pm = this.packageManager
        val pkg = this.packageName
        val targetAlias = "$pkg.$activeAliasName"
        val oldAlias = "$pkg.$oldAliasName"
        val targetComponent = ComponentName(pkg, targetAlias)
        val oldComponent = ComponentName(pkg, oldAlias)
        pm.setComponentEnabledSetting(
            targetComponent,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        pm.setComponentEnabledSetting(
            oldComponent,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
        
        Log.d("ChangeIcon", "Icono cambiado de $oldAliasName a $activeAliasName de forma silenciosa")

    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.uiTayCountryNetwork():String{
    var codeCountry = COUNTRY_DEFAULT
    uiTayTryCatch(catch = {
        codeCountry = COUNTRY_DEFAULT
    }) {
        val tm = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        codeCountry =  tm.networkCountryIso
    }
    return codeCountry.uppercase()
}

@SuppressLint("MissingPermission", "HardwareIds")
fun  Context.uiTayNumberPhone() :String{
    var numberPhone = UI_EMPTY
    uiTayTryCatch {
        val mPhoneNumber = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        numberPhone = mPhoneNumber.line1Number
    }
    return  numberPhone
}

@SuppressLint("HardwareIds")
fun Application.uiTayGetAndroidId(): String {
    return try {
        Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        ) ?: UI_EMPTY
    } catch (e: Exception) {
        e.printStackTrace()
        UI_EMPTY
    }
}

fun uiTayIsWhatsAppInstalled(context: Context, name: String): Boolean {
    try {
        context.packageManager.getPackageInfo(name, PackageManager.GET_ACTIVITIES)
        return true
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        return false
    }
}

@SuppressLint("MissingPermission")
fun Context?.uiTayIsConnected(): Boolean {
    return this?.let {
        val cm = it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.getNetworkCapabilities(cm.activeNetwork)
            ?.hasCapability((NetworkCapabilities.NET_CAPABILITY_INTERNET)) ?: false
    } ?: false
}

fun Context?.uiTayIsAirplaneModeActive(): Boolean {
    return this?.let {
        return Settings.Global.getInt(it.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    } ?: false

}

fun Context.uiTayGetUiSizeContent(): Pair<Int, Int> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val bounds = windowManager.currentWindowMetrics.bounds
        Pair(bounds.width(), bounds.height())
    } else {
        val displayMetrics = resources.displayMetrics
        Pair(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }
}

fun Context.uiTayClearNotificationsAlter() {
    NotificationManagerCompat.from(this).cancelAll()
}

fun Context.uiTayClearNotifications() {
    getSystemService<NotificationManager>()?.cancelAll()
}

fun String.uiTayDeleteArchive(){
    val delete = File(this)
    if (delete.exists()) {
        if (delete.delete()) {
            Log.v("","file Deleted :$this")
        } else {
            Log.v("","file not Deleted :$this")
        }
    }
}
