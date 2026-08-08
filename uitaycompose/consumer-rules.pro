# Reglas para la librería uitaycompose (MÁXIMA COMPATIBILIDAD)

# Mantener firmas para Reflection
-keepattributes Signature

# Gson TypeToken (Vital para uiTayDataJson)
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken
-keep public class * extends com.google.gson.reflect.TypeToken

# Modelos de la librería
-keep class com.valu.uitaycompose.model.entity.** { *; }
