# Reglas para la librería uitaycompose

# Proteger el uso de Gson TypeToken
-keepattributes Signature
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken
-keep public class * extends com.google.gson.reflect.TypeToken

# Proteger modelos de la librería
-keep class com.valu.uitaycompose.model.entity.** { *; }
