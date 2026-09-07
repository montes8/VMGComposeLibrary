/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.security.encryption.rsa

import android.util.Base64
import com.valu.uitaycompose.security.encryption.uiRsaPrivateKeyStore
import com.valu.uitaycompose.security.encryption.uiRsaPublicKeyStore
import com.valu.uitaycompose.utils.UI_MY_KEY_SECRET_RSA
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

object RsaEngine {

    private const val RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding"

    /**
     * Encripta usando la LLAVE PÚBLICA (String Base64)
     */
    fun encrypt(data: String, publicKeyString: String): String {
        val keyBytes = Base64.decode(publicKeyString, Base64.NO_WRAP)
        val spec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKey = keyFactory.generatePublic(spec)
        val cipher = Cipher.getInstance(RSA_TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptedBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
    }

    /**
     * Desencripta usando la LLAVE PRIVADA (String Base64)
     */
    fun decrypt(dataBase64: String, privateKeyString: String): String {
        val keyBytes = Base64.decode(privateKeyString, Base64.NO_WRAP)
        val spec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        val privateKey = keyFactory.generatePrivate(spec)
        val cipher = Cipher.getInstance(RSA_TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        val decodedData = Base64.decode(dataBase64, Base64.NO_WRAP)
        val decryptedBytes = cipher.doFinal(decodedData)
        return String(decryptedBytes, Charsets.UTF_8)
    }

    fun encryptAut(data: String, alias: String = UI_MY_KEY_SECRET_RSA): String {
        val publicKey = alias.uiRsaPublicKeyStore()
        val cipher = Cipher.getInstance(RSA_TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encrypted, Base64.NO_WRAP)
    }

    fun decryptAut(dataBase64: String, alias: String = UI_MY_KEY_SECRET_RSA): String {
        val privateKey = alias.uiRsaPrivateKeyStore()
        val cipher = Cipher.getInstance(RSA_TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        val decodedData = Base64.decode(dataBase64, Base64.NO_WRAP)
        val decryptedBytes = cipher.doFinal(decodedData)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}
