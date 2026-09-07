/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.security.biometric

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


fun String.generateSecretKey() {
    try {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        )
        val builder = KeyGenParameterSpec.Builder(
            this,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .setUserAuthenticationRequired(true)
            .setInvalidatedByBiometricEnrollment(true)
        keyGenerator.init(builder.build())
        keyGenerator.generateKey()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun String.validateNewFinger(): Pair<Boolean, Cipher?> {
    return try {
        val cipher = getCipher()
        val secretKey = this.getSecretKey()
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        Pair(true, cipher)
    } catch (e: Exception) {
        e.printStackTrace()
        Pair(false, null)
    }
}

fun getCipher(): Cipher {
    return Cipher.getInstance(
        "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}")
}

 fun String.getSecretKey(): SecretKey {
    val keyStore = KeyStore.getInstance("AndroidKeyStore")
    keyStore.load(null)
    return keyStore.getKey(this, null) as SecretKey
}
