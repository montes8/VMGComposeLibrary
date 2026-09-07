/*
 * Copyright (c) 2026 Tayler (montes8). Todos los derechos reservados.
 * Este código es propiedad exclusiva de su autor. Queda prohibida su 
 * copia, distribución o uso sin autorización previa.
 */
package com.valu.uitaycompose.security.biometric

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.valu.uitaycompose.utils.UI_CANCEL_BIOMETRIC
import com.valu.uitaycompose.utils.UI_SUB_TITLE_BIOMETRIC
import com.valu.uitaycompose.utils.UI_TITLE_BIOMETRIC

/** todo val authManager = remember {
    AuthenticationManager(activity) { result ->
        when (result) {
         is AuthResult.ConfigChanged -> {
     AQUÍ: El usuario agregó/quitó huellas.
       Debes pedirle contraseña manual y volver a llamar a generateSecretKey()
     }
      otros casos
      }
     }
 } */

class UiTayBiometricManager(
    private val activity: AppCompatActivity,
    private val alias: String,
    private val onResult: (UiAuthResult) -> Unit
) {
    sealed class UiAuthResult {
        data class Success(val result: BiometricPrompt.AuthenticationResult) : UiAuthResult()
        data class Error(val code: Int, val message: String) : UiAuthResult()
        object Failed : UiAuthResult()
        object ConfigChanged : UiAuthResult()
    }

    private val executor = ContextCompat.getMainExecutor(activity)

    private val biometricPrompt = BiometricPrompt(
        activity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                onResult(UiAuthResult.Error(errorCode, errString.toString()))
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                onResult(UiAuthResult.Success(result))
            }

            override fun onAuthenticationFailed() {
                onResult(UiAuthResult.Failed)
            }
        }
    )

    fun uiTayShowAuthenticationSure(
        title: String = UI_TITLE_BIOMETRIC,
        subTitle: String = UI_SUB_TITLE_BIOMETRIC,
        btnCancel: String = UI_CANCEL_BIOMETRIC
    ) {
        val (isValid, cipher) = alias.validateNewFinger()
        if (isValid && cipher != null) {
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(title)
                .setSubtitle(subTitle)
                .setNegativeButtonText(btnCancel)
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                .build()

            biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
        } else {
            onResult(UiAuthResult.ConfigChanged)
        }
    }


    fun uiTayShowAuthentication(
        title: String = UI_TITLE_BIOMETRIC,
        subTitle: String = UI_SUB_TITLE_BIOMETRIC,
        btnCancel: String = UI_CANCEL_BIOMETRIC
    ) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subTitle)
            .setNegativeButtonText(btnCancel)
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK)
            .build()
        biometricPrompt.authenticate(promptInfo)
    }

}
