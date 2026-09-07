/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.security.encryption.aes

import android.util.Base64
import com.valu.uitaycompose.security.encryption.TypeAes
import com.valu.uitaycompose.security.encryption.uiConverterKeyString
import com.valu.uitaycompose.security.encryption.uiCreateSecretKeyStore
import com.valu.uitaycompose.utils.UI_MY_KEY_SECRET
import javax.crypto.Cipher
import javax.crypto.SecretKey

object AesECB {

    fun encrypt(data: String, key: SecretKey, type: TypeAes): String {
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encrypted, Base64.NO_WRAP)
    }

    fun decrypt(dataBase64: String, key: SecretKey, type: TypeAes): String {
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.DECRYPT_MODE, key)

        val decodedBytes = Base64.decode(dataBase64, Base64.NO_WRAP)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return String(decryptedBytes, Charsets.UTF_8)
    }

    fun encrypt(data: String, key: String, type: TypeAes): String {
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.ENCRYPT_MODE, key.uiConverterKeyString())

        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encrypted, Base64.NO_WRAP)
    }


    fun decrypt(dataBase64: String, key: String, type: TypeAes): String {
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.DECRYPT_MODE, key.uiConverterKeyString())

        val decodedBytes = Base64.decode(dataBase64, Base64.NO_WRAP)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return String(decryptedBytes, Charsets.UTF_8)
    }

    fun encryptAut(data: String, type: TypeAes, alias: String = UI_MY_KEY_SECRET): String {
        val key = uiCreateSecretKeyStore(alias, type)
        val cipher = Cipher.getInstance(type.transformation)

        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))

        return Base64.encodeToString(encrypted, Base64.NO_WRAP)
    }

    fun decryptAut(dataBase64: String, type: TypeAes, alias: String = UI_MY_KEY_SECRET): String {
        val key = uiCreateSecretKeyStore(alias, type)
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decodedBytes = Base64.decode(dataBase64, Base64.NO_WRAP)
        val decryptedBytes = cipher.doFinal(decodedBytes)

        return String(decryptedBytes, Charsets.UTF_8)
    }
}
