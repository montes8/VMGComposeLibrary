/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.security.encryption.quantum

import android.util.Base64
import org.bouncycastle.pqc.crypto.mlkem.MLKEMGenerator
import org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPublicKeyParameters
import org.json.JSONObject
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object UiTayQuantumEngine {

    fun uiTaEncapsulate(publicKey: String): Pair<String, ByteArray >{
        val serverPubKeyBytes = Base64.decode(
            publicKey,
            Base64.NO_WRAP
        )
        val mlKemParams = MLKEMParameters.ml_kem_768
        val publicKeyParams = MLKEMPublicKeyParameters(mlKemParams, serverPubKeyBytes)
        val kemGenerator = MLKEMGenerator(SecureRandom())
        val encapsulationResult = kemGenerator.generateEncapsulated(publicKeyParams)
        val sharedSecretKeyBytes = encapsulationResult.secret
        val ciphertextBytes = encapsulationResult.encapsulation
        return Pair(Base64.encodeToString(ciphertextBytes, Base64.NO_WRAP),sharedSecretKeyBytes)
    }

     fun uiTayDecryptAesGcm(
        ciphertextB64: String,
        ivB64: String,
        authTagB64: String,
        aesKeyBytes: ByteArray
    ): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val combinedCiphertext = Base64.decode(ciphertextB64, Base64.NO_WRAP) +
                Base64.decode(authTagB64, Base64.NO_WRAP)
        val ivBytes = Base64.decode(ivB64, Base64.NO_WRAP)

        cipher.init(
            Cipher.DECRYPT_MODE,
            SecretKeySpec(aesKeyBytes, "AES"),
            GCMParameterSpec(128, ivBytes)
        )

        val decryptedBytes = cipher.doFinal(combinedCiphertext)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}
