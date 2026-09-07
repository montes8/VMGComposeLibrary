# VMGComposeLibrary (uitaycompose) 🚀

Una potente librería de componentes de **Jetpack Compose** y utilidades esenciales para agilizar el desarrollo de aplicaciones Android modernas.

## ✨ Características

- **Componentes UI Personalizables:** Botones con estados de clic, Progress bars, Dialogs, Toolbars y Switches.
- **Seguridad:** Gestión simplificada de Biometría (Huella/Rostro) y potentes motores de encriptación (AES, RSA e incluso un motor Quantum experimental).
- **Extensiones (KTX):** Decenas de utilidades para `Date`, `Image`, `Location`, `Validation`, `Encryption` y más.
- **Gestión de Permisos:** Manejo fácil de permisos de cámara y estados de permisos.
- **Multimedia:** Soporte para GIF y carga de imágenes por URL.

## 📦 Instalación

Para usar esta librería en tu proyecto, puedes utilizar **JitPack**.

### 1. Agregar el repositorio en `settings.gradle.kts`

```kotlin
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}
```

### 2. Agregar la dependencia en `build.gradle.kts`

```kotlin
dependencies {
    implementation("com.github.montes8:VMGComposeLibrary:1.0.0")
}
```

## 🚀 Uso Rápido

### Botón Personalizado (`UiTayButton`)

```kotlin
UiTayButton(
    uiTayText = "Enviar Datos",
    uiTayStyleBtn = UTStyleCButton.UI_TAY_PRIMARY,
    uiTayClick = { 
        // Tu lógica aquí
    }
)
```

### Autenticación Biométrica (`UiTayBiometricManager`)

```kotlin
val biometricManager = UiTayBiometricManager(
    activity = context as AppCompatActivity,
    alias = "mi_llave_segura",
    onResult = { result ->
        when (result) {
            is UiAuthResult.Success -> { /* Éxito */ }
            is UiAuthResult.Error -> { /* Error */ }
            is UiAuthResult.ConfigChanged -> { /* Cambio en huellas */ }
            else -> {}
        }
    }
)

biometricManager.uiTayShowAuthentication()
```

### Utilidades de Fecha (Extensiones)

```kotlin
val fechaActual = Date().uiTayFormat("dd/MM/yyyy")
val esHoy = Date().uiTayIsToday()
```

### Encriptación AES

```kotlin
val encrypted = "Texto Secreto".uiTayEncryptAES(key = "clave_16_chars")
val decrypted = encrypted.uiTayDecryptAES(key = "clave_16_chars")
```

## 🛠 Estructura del Proyecto

- `animation/`: Utilidades para animaciones fluidas.
- `button/`: Implementaciones de botones con feedback visual.
- `security/`: Motores de encriptación y manager biométrico.
- `utils/extension/`: El corazón de la librería con extensiones para casi todo tipo de dato en Android.
- `modal/`: Dialogos y vistas de detalle preconfiguradas.

## 📄 Licencia y Uso

**Todos los derechos reservados.**

Este repositorio es exclusivamente para fines de **exhibición y portafolio personal**. No se otorga permiso para el uso, copia, modificación o distribución de este código, ya sea de forma total o parcial, en proyectos personales o comerciales sin el consentimiento expreso del autor.

---
Hecho con ❤️ por [montes8](https://github.com/montes8)
