# TayComposeLibrary (uitaycompose) 🚀

> [!CAUTION]
> **PROPIEDAD INTELECTUAL Y TÉRMINOS DE USO**
>
> Todos los derechos reservados. Este repositorio es exclusivamente para fines de **exhibición y portafolio personal**. 
> 
> **Queda estrictamente prohibida** la copia, modificación, distribución o uso de este código (total o parcial) en proyectos personales o comerciales sin el consentimiento expreso y por escrito del autor. 
> 
> **AVISO IMPORTANTE:** Este código contiene códigos internos, marcas de agua digitales y patrones lógicos específicos que detectan que es mi código, incluso si es copiado o modificado parcialmente.
> 
> © 2026 Tayler (montes8).

---

Una potente librería de componentes de **Jetpack Compose** y utilidades esenciales para agilizar el desarrollo de aplicaciones Android modernas.

## ✨ Características

- **Componentes UI Personalizables:** Botones con estados de clic, Progress bars, Dialogs, Toolbars y Switches.
- **Seguridad:** Gestión simplificada de Biometría (Huella/Rostro) y potentes motores de encriptación (AES, RSA e incluso un motor Quantum experimental).
- **Extensiones (KTX):** Decenas de utilidades para `Date`, `Image`, `Location`, `Validation`, `Encryption` y más.
- **Gestión de Permisos:** Manejo fácil de permisos de cámara y estados de permisos.
- **Multimedia:** Soporte para GIF y carga de imágenes por URL.

## 🛠 Estructura del Proyecto

- `animation/`: Utilidades para animaciones fluidas.
- `button/`: Implementaciones de botones con feedback visual.
- `security/`: Motores de encriptación y manager biométrico.
- `utils/extension/`: El corazón de la librería con extensiones para casi todo tipo de dato en Android.
- `modal/`: Dialogos y vistas de detalle preconfiguradas.

## 🚀 Uso Rápido (Solo referencia interna)

### Botón Personalizado (`UiTayButton`)

```kotlin
UiTayButton(
    uiTayText = "Enviar Datos",
    uiTayStyleBtn = UTStyleCButton.UI_TAY_PRIMARY,
    uiTayClick = { 
        // Lógica interna
    }
)
```

### Autenticación Biométrica (`UiTayBiometricManager`)

```kotlin
val biometricManager = UiTayBiometricManager(
    activity = context as AppCompatActivity,
    alias = "mi_llave_segura",
    onResult = { result ->
        // Manejo de resultados
    }
)
```

---
Hecho con ❤️ por [montes8](https://github.com/montes8)
