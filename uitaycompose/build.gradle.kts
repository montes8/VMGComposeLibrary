plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.valu.uitaycompose"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 25

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        jvmToolchain(21)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation("androidx.compose.ui:ui:1.10.0")
    implementation("androidx.compose.material3:material3:1.4.0")
    implementation("io.ktor:ktor-client-okhttp:3.1.1")
    implementation("io.ktor:ktor-client-cio:3.1.1")
    implementation("androidx.biometric:biometric:1.2.0-alpha05")
    implementation("org.bouncycastle:bcprov-jdk18on:1.77")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation(libs.kotlinx.serialization.json)
    implementation("androidx.navigation:navigation-compose:2.8.5")
    implementation("androidx.exifinterface:exifinterface:1.3.3")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    // Librería de utilidades y PKIX (necesaria para manejar pares de llaves)
    implementation("org.bouncycastle:bcpkix-jdk18on:1.77")
}