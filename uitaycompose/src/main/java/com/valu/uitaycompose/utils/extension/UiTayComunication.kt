/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.utils.extension

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.telecom.Call
import android.util.Log
import androidx.core.net.toUri
import com.valu.uitaycompose.model.entity.UiTayContactPhone
import com.valu.uitaycompose.utils.TAY_LOG
import com.valu.uitaycompose.utils.UI_EMPTY


fun uiTayCallPhoneIntent(context: Context,number : String) {
    val intent = Intent(Intent.ACTION_CALL)
    intent.data = "tel:$number".toUri()
    context.startActivity(intent)
}

fun Context.uiTayDialedNumber(
    number: String = UI_EMPTY, key: String = "tel:",
    uiTayAction: String = Intent.ACTION_DIAL
) {
    try {
        val intent = Intent(uiTayAction)
        intent.setData("$key$number".toUri())
        this.startActivity(intent)
    } catch (e: SecurityException) {
        Log.e(TAY_LOG, e.message.toString())
    }
}

fun Context.uiTayViewCall() {
    try {
        val intent = Intent()
        intent.setClass(this, Call::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)
    } catch (e: SecurityException) {
        Log.e(TAY_LOG, e.message.toString())
    }
}

fun Context.uiTayViewCallButton() {
    val intent = Intent(Intent.ACTION_CALL_BUTTON)
    this.startActivity(intent)
}

fun Application.loadContactUser(): List<UiTayContactPhone>{
    val contacts : ArrayList<UiTayContactPhone> = ArrayList()
    val projection = arrayOf(
        ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
    )

    val cursor = this.contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        projection,
        null,
        null,
        "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC")

    if(cursor?.moveToFirst() == true)
        do {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)).trim()
            val phone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)).trim()

            if(phone.isNotEmpty()){
                contacts.add(
                    UiTayContactPhone( name = name, phoneNumber = phone)
                )
            }
        } while (cursor.moveToNext())

    cursor?.close()
    return contacts
}


@SuppressLint("UseKtx")
fun Application.uiTayDeleteSMS(all: Boolean = false, utNumber: String = UI_EMPTY) {
    this.contentResolver.query(
        Uri.parse("content://sms/"),
        arrayOf("_id", "thread_id", "address", "person", "date", "body"),
        null,
        null,
        null
    )?.let { c ->
        uiTayTryCatch {
            while (c.moveToNext()) {
                val id = c.getInt(0)
                val address = c.getString(2)
                if (all) {
                    this.contentResolver.delete(
                        Uri.parse("content://sms/$id"), null, null
                    )
                } else {
                    if (address == utNumber) {
                        this.contentResolver.delete(
                            Uri.parse("content://sms/$id"), null, null
                        )
                    } } }
            c.close()
        }
    }


}
