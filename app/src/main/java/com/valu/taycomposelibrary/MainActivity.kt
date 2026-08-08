package com.valu.taycomposelibrary

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valu.taycomposelibrary.ui.theme.TayComposeLibraryTheme
import com.valu.uitaycompose.button.UiTayButton
import com.valu.uitaycompose.loading.uiTayShowProgress
import com.valu.uitaycompose.security.encryption.quantum.QuantumEngine
import com.valu.uitaycompose.security.encryption.quantum.uiKeyPrivateQuantum
import com.valu.uitaycompose.security.encryption.quantum.uiKeyPublicQuantum
import com.valu.uitaycompose.security.encryption.uiCreateIv
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Ejecutamos la prueba de encriptación cuántica al iniciar
        runQuantumEncryptionTest()

        setContent {
            TayComposeLibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    /**
     * Prueba el motor de encriptación post-cuántica Kyber + AES-GCM
     */
    private fun runQuantumEncryptionTest() {
        val originalMessage = "Este es un mensaje protegido contra computadoras cuánticas"
        val keyPair = QuantumEngine.uiCreateKeys()
        val iv = uiCreateIv(true)

        // 1. Encriptar
        val encryptedResult = QuantumEngine.encrypt(
            data = originalMessage,
            publicKey = keyPair.uiKeyPublicQuantum(),
            iv = iv
        )
        
        Log.d("QuantumSecurity", "--- INICIO PRUEBA CUÁNTICA ---")
        Log.d("QuantumSecurity", "Mensaje Original: $originalMessage")
        Log.d("QuantumSecurity", "Texto Cifrado (Base64): ${encryptedResult.first}")
        Log.d("QuantumSecurity", "Tamaño del paquete de llave: ${encryptedResult.second.size} bytes")

        // 2. Desencriptar (Usamos el texto cifrado, no el original)
        val decryptedMessage = QuantumEngine.decrypt(
            data = encryptedResult.first, 
            privateKey = keyPair.uiKeyPrivateQuantum(),
            packageKey = encryptedResult.second,
            iv = iv
        )

        Log.d("QuantumSecurity", "Mensaje Desencriptado: $decryptedMessage")
        Log.d("QuantumSecurity", "--- FIN PRUEBA CUÁNTICA ---")
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .uiTayShowProgress(isLoading),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UiTayButton(
            uiTayText = "Probar Modal de Carga",
            uiTayClick = {
                isLoading = true
                scope.launch {
                    delay(3000)
                    isLoading = false
                }
            }
        )
    }
}
