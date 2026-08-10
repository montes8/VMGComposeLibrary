package com.valu.taycomposelibrary

import android.content.ContentValues.TAG
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
import android.util.Base64
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.lifecycle.lifecycleScope
import com.valu.uitaycompose.security.encryption.TypeAes
import com.valu.uitaycompose.security.encryption.aes.AesGCM
import com.valu.uitaycompose.security.encryption.quantum.UiTayQuantumEngine
import com.valu.uitaycompose.security.encryption.quantum.UiTayQuantumEngine.uiTaEncapsulate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bouncycastle.pqc.crypto.mlkem.MLKEMGenerator
import org.bouncycastle.pqc.crypto.mlkem.MLKEMParameters
import org.bouncycastle.pqc.crypto.mlkem.MLKEMPublicKeyParameters
import org.bouncycastle.pqc.crypto.util.PrivateKeyInfoFactory
import org.bouncycastle.pqc.crypto.util.SubjectPublicKeyInfoFactory
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

const val PUBLIC_KEY_BASE64 = "MIIEtDANBgsrBgEEAYGwGgUGAgOCBKEAOouNkMSQAQSrOcUpnYokKzNa+EsPxVw7z2x73jqZu3BSTiaYf9m0lhQSKHhbIrNZSYdYI9QT0tcOK1JOtYQj4RUU/8WZV/hr7QOs6JojgRpgumQOj1lM5meYBFK9hvlM87Q6VIlfVYRzDXi0ZCcQ8VNCbbkw7cWtZ0YptznJZgxGtTQe+BctlouyhjyWRNSeYglqJrmRjvViRUY0yMl1NndVBlqPVxeTkKM5M9Y1XsNPsLd8jUdWksQwSePOFscyCWwOthw6LtmQ59MbouBWlZHLELERoat8KeKoYvhkfRV2xWgefNtLEfm5MfLAXTMhnssdAOKIYiW/H0K/f3BkWKWeKCI5oRJklctIGCUMq6amPHVmRzZUwosZxzkKB+HIiFKqsaKx0BcZE2pU+5YfoAUnP4C1lAqZGNJWLjAvzWYrzkedLpxDb6XI6BAr3kytcRap9ZoRIpQJ96VvsrigNzch5kZ7qBDNE/bCp/AhHHwwDpBJQYmhV5GfcgvG+8GubAQKKVuU5HwLbWsSPwioEctmLEats2ZjOFXLSCEo3JYdBlW4zDcNyGCg8XCom2mK37dTCJBPWWsLoBsQipVADDG8t7MeTdtlOEyrI9gND7els/XOquLLg+FY1UavhdxnghsfdueRUgg7MxJpKKZ4riCD1kW74ym/rVtgSJUsbaMveqZG5rNHfTVJxdOFxLA+ofMZfoBzyUQMjgFItbWSdnAqTgZpu7sTlPUlFPQp+VLH63Um1gOc4PAkCit7MbQ418wvOTeVtgtG2kMj41txXhmMsjNq+hChhgtanFMMtbcHo+SUaXdDCXnBtbR7qpsVXmCg1ghzGuSAZvITxBx4n2KEO9QdBvaY6kMtVdDJ/9CgT/V08gusDNMt3nRoDHGIjGN/CmgyirdFo2osDqp3c9C7g7NQIyOGqVFe37B2y6sVO6kJN4yopMcFSUMYhxmwpJyCbkqDOvE3qVIa83pRdDVmmtixpdpOCGmMapK87PE5ygcqrjaNwEaPH8V6Q2ULY8w+3bg3fjIAr7QocoVr9mTBywYidkAR/vKb16Auylog87lX0qVxdRQ0UJs3aqEsl5S88fgKAAGtrPRROFxcBtYC8YuSFvbFAIlccIhbuCxO06MZSWd4jzATuSetWwtUASoB+giVZDNsNilMEAbNyXodgbWxssYv0pypaSAMUVdud3aYXgxqy1dcFGs7N9MdBowB/0uawoZC6Pp6lNm2u3qi3konTVZrRXa25rE4uGW9BnIJa9xtLGdI1pdnpdEdDiJbtJcoHFAuB/piD7rCukVUsCyMhMFDONqfIdmoZyJOHkqX61OaplwjyTBF47Ip3ig/JRNpGEoC0VappDsqZYqg8+aZ6vl72ZBu8aBonVJiM8V6G/VNCsMLd3W9a7hFD9S5AkGi/VIAlsxd97F9F8WDGahjj/YuaIO1NXG6DrtNAADQp2Eek6YVhuaKSNwR8MfLb8p3BPY//psXhZLLXMiNJRIqLit0PhCRupJ/uxkQ7ltuFIpr7ZYgQvix1BoUfHBTHdLlFKQtpUiqwkyMa05VEjrX7dTXky6vdFK/wjg="

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        executePostQuantumHandshake()
        // Ejecutamos la prueba de encriptación cuántica al iniciar
        //printStaticQuantumKeys()

        setContent {
            TayComposeLibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    private fun executePostQuantumHandshake() {
        // Lanzamos la corrutina directamente aquí en el hilo de I/O
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                Log.d(TAG, "Iniciando protocolo poscuántico...")

                // 1. Obtener la llave pública poscuántica del servidor Node.js
                val pubKeyUrl = URL("http://10.0.2.2:8080/services/param/public-key")
                val pubConn = pubKeyUrl.openConnection() as HttpURLConnection
                pubConn.requestMethod = "GET"

                val pubResponse = pubConn.inputStream.bufferedReader().use { it.readText() }
                val serverPubKeyBytes = uiTaEncapsulate(JSONObject(pubResponse).getString("publicKey"))
                Log.d(TAG, "Llave pública del servidor obtenida correctamente.")

                val packageBase64 = serverPubKeyBytes.first

                // 3. Enviar el paquete poscuántico al endpoint protegido
                val url = URL("http://10.0.2.2:8080/services/param")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("x-quantum-package", packageBase64)
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val responseString = connection.inputStream.bufferedReader().use { it.readText() }
                    val jsonResponse = JSONObject(responseString)

                    if (jsonResponse.getString("status") == "encrypted") {
                        val dataObj = jsonResponse.getJSONObject("data")
                        val ciphertextB64 = dataObj.getString("ciphertext")
                        val ivB64 = dataObj.getString("iv")
                        val authTagB64 = dataObj.getString("authTag")

                        val decryptedData = UiTayQuantumEngine.uiTayDecryptAesGcm(ciphertextB64, ivB64, authTagB64, serverPubKeyBytes.second)
                        Log.d(TAG, "¡Éxito total! Datos descifrados: $decryptedData")
                    } else {
                        Log.w(TAG, "El servidor respondió pero el estado no es cifrado.")
                    }
                } else {
                    Log.e(TAG, "Error HTTP del servidor: $responseCode")
                }

            } catch (e: Exception) {
                Log.e(TAG, "Fallo en la ejecución del handshake poscuántico", e)
            }
        }
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

