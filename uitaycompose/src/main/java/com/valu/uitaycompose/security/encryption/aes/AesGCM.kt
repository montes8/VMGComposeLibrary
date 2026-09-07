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
import javax.crypto.spec.GCMParameterSpec

object AesGCM {

    private const val AUTH_TAG_LENGTH = 128

    fun encrypt(data: String, key: String, iv: String, type: TypeAes): String {
        val ivBytes = Base64.decode(iv, Base64.NO_WRAP)
        val spec = GCMParameterSpec(AUTH_TAG_LENGTH, ivBytes)
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.ENCRYPT_MODE, key.uiConverterKeyString(), spec)
        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encrypted, Base64.NO_WRAP)
    }

    fun decrypt(dataBase64: String, key: String, iv: String, type: TypeAes): String {
        val ivBytes = Base64.decode(iv, Base64.NO_WRAP)
        val spec = GCMParameterSpec(AUTH_TAG_LENGTH, ivBytes)
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.DECRYPT_MODE, key.uiConverterKeyString(), spec)
        val decodedData = Base64.decode(dataBase64, Base64.NO_WRAP)
        val decryptedBytes = cipher.doFinal(decodedData)
        return String(decryptedBytes, Charsets.UTF_8)
    }

    fun encrypt(data: String, key: SecretKey, iv: String, type: TypeAes): String {
        val ivBytes = Base64.decode(iv, Base64.NO_WRAP)
        val spec = GCMParameterSpec(AUTH_TAG_LENGTH, ivBytes)
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.ENCRYPT_MODE, key, spec)
        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encrypted, Base64.NO_WRAP)
    }

    fun decrypt(dataBase64: String, key: SecretKey, iv: String, type: TypeAes): String {
        val ivBytes = Base64.decode(iv, Base64.NO_WRAP)
        val spec = GCMParameterSpec(AUTH_TAG_LENGTH, ivBytes)
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.DECRYPT_MODE, key, spec)
        val decodedData = Base64.decode(dataBase64, Base64.NO_WRAP)
        val decryptedBytes = cipher.doFinal(decodedData)
        return String(decryptedBytes, Charsets.UTF_8)
    }


    fun encryptAut(data: String, iv: String, type: TypeAes, alias: String = UI_MY_KEY_SECRET): String {
        val key = uiCreateSecretKeyStore(alias, type)
        val ivBytes = Base64.decode(iv, Base64.NO_WRAP)
        val spec = GCMParameterSpec(AUTH_TAG_LENGTH, ivBytes)
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.ENCRYPT_MODE, key, spec)
        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encrypted, Base64.NO_WRAP)
    }


    fun decryptAut(dataBase64: String, iv: String, type: TypeAes, alias: String = UI_MY_KEY_SECRET): String {
        val key = uiCreateSecretKeyStore(alias, type)
        val ivBytes = Base64.decode(iv, Base64.NO_WRAP)
        val spec = GCMParameterSpec(AUTH_TAG_LENGTH, ivBytes)
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.DECRYPT_MODE, key, spec)
        val decodedData = Base64.decode(dataBase64, Base64.NO_WRAP)
        val decryptedBytes = cipher.doFinal(decodedData)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}
