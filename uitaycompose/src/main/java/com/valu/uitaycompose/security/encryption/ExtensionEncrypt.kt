/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.security.encryption

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.valu.uitaycompose.utils.UI_EMPTY
import com.valu.uitaycompose.utils.UI_ERROR_KEY
import com.valu.uitaycompose.utils.UI_ERROR_KEY_FORMAT
import com.valu.uitaycompose.utils.UI_MY_KEY_SECRET
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import java.security.SecureRandom
import javax.crypto.spec.SecretKeySpec


fun uiCreateSecretKeyStore(alias: String = UI_MY_KEY_SECRET, type: TypeAes = TypeAes.GCM_256): SecretKey {
    val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    // todo Attempt to recover if it already exists
    keyStore.getKey(alias, null)?.let { return it as SecretKey }

    // todo Create a new key with the parameters of the Enum
    val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")

    val spec = KeyGenParameterSpec.Builder(
        alias,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(type.blockMode)
        .setEncryptionPaddings(type.padding)
        .setRandomizedEncryptionRequired(false)
        .setKeySize(type.keySize) // todo Here apply the 128, 192 o 256
        .build()

    keyGenerator.init(spec)
    return keyGenerator.generateKey()
}

fun uiCreateSecretKey(type: TypeAes = TypeAes.GCM_256): SecretKey {
    val keyGenerator = KeyGenerator.getInstance("AES")
    keyGenerator.init(type.keySize) // todo 128, 192 o 256
    return keyGenerator.generateKey()
}
fun uiCreateKeyHexToString(type: TypeAes = TypeAes.GCM_256): String {
    return uiKeyToHex(uiCreateSecretKey(type))
}
fun uiCreateKeyBase64ToString(type: TypeAes = TypeAes.GCM_256): String {
    return uiKeyToBase64(uiCreateSecretKey(type))
}

fun uiCreateIv(isGCM: Boolean = true): String {
    val size = if (isGCM) 12 else 16
    val ivBytes = ByteArray(size)
    SecureRandom().nextBytes(ivBytes)
    return Base64.encodeToString(ivBytes, Base64.NO_WRAP)
}

fun uiKeyToBase64(key: SecretKey): String {
    return Base64.encodeToString(key.encoded, Base64.NO_WRAP)
}

fun uiKeyToHex(key: SecretKey): String {
    return key.encoded.joinToString(UI_EMPTY) { byte ->
        "%02x".format(byte)
    }
}
fun String.uiConverterKeyString(): SecretKey {
    val keyBytes: ByteArray = when {
        this.matches(Regex("^[0-9a-fA-F]+$")) && this.length % 2 == 0 -> {
            this.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        }
        else -> try {
            val decoded = Base64.decode(this, Base64.NO_WRAP)
            decoded
        } catch (e: Exception) {
            throw IllegalArgumentException(UI_ERROR_KEY_FORMAT)
        }
    }
    if (keyBytes.size !in listOf(16, 24, 32)) {
        throw IllegalArgumentException(UI_ERROR_KEY)
    }

    return SecretKeySpec(keyBytes, "AES")
}

enum class TypeAes(
    val keySize: Int,
    val blockMode: String,
    val padding: String,
    val transformation: String
) {
    // todo --- MODE ECB (Electronic Codebook) ---
    ECB_128(128, KeyProperties.BLOCK_MODE_ECB, KeyProperties.ENCRYPTION_PADDING_PKCS7, "AES/ECB/PKCS7Padding"),
    ECB_192(192, KeyProperties.BLOCK_MODE_ECB, KeyProperties.ENCRYPTION_PADDING_PKCS7, "AES/ECB/PKCS7Padding"),
    ECB_256(256, KeyProperties.BLOCK_MODE_ECB, KeyProperties.ENCRYPTION_PADDING_PKCS7, "AES/ECB/PKCS7Padding"),

    // todo --- MODE CBC (Cipher Block Chaining) ---
    CBC_128(128, KeyProperties.BLOCK_MODE_CBC, KeyProperties.ENCRYPTION_PADDING_PKCS7, "AES/CBC/PKCS7Padding"),
    CBC_192(192, KeyProperties.BLOCK_MODE_CBC, KeyProperties.ENCRYPTION_PADDING_PKCS7, "AES/CBC/PKCS7Padding"),
    CBC_256(256, KeyProperties.BLOCK_MODE_CBC, KeyProperties.ENCRYPTION_PADDING_PKCS7, "AES/CBC/PKCS7Padding"),

    // todo --- MODE GCM (Galois/Counter Mode) ---
    GCM_128(128, KeyProperties.BLOCK_MODE_GCM, KeyProperties.ENCRYPTION_PADDING_NONE, "AES/GCM/NoPadding"),
    GCM_192(192, KeyProperties.BLOCK_MODE_GCM, KeyProperties.ENCRYPTION_PADDING_NONE, "AES/GCM/NoPadding"),
    GCM_256(256, KeyProperties.BLOCK_MODE_GCM, KeyProperties.ENCRYPTION_PADDING_NONE, "AES/GCM/NoPadding")
}


fun uiCreateRsaKeyPair(): Pair<String, String> {
    val kpg = KeyPairGenerator.getInstance("RSA")
    kpg.initialize(2048) // todo Safe standard size
    val kp = kpg.generateKeyPair()
    val publicKey = Base64.encodeToString(kp.public.encoded, Base64.NO_WRAP)
    val privateKey = Base64.encodeToString(kp.private.encoded, Base64.NO_WRAP)
    return Pair(publicKey, privateKey)
}


fun String.uiCreateRsaKeyStore() {
    val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    if (keyStore.containsAlias(this)) return
    val kpg = KeyPairGenerator.getInstance(
        KeyProperties.KEY_ALGORITHM_RSA,
        "AndroidKeyStore"
    )
    val parameterSpec = KeyGenParameterSpec.Builder(
        this,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    ).run {
        setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
        setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
        setKeySize(2048)
        build()
    }
    kpg.initialize(parameterSpec)
    kpg.generateKeyPair()
}

fun String.uiRsaPublicKeyStore(): PublicKey {
    val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    return keyStore.getCertificate(this).publicKey
}

fun String.uiRsaPrivateKeyStore(): PrivateKey {
    val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    return keyStore.getKey(this, null) as PrivateKey
}
