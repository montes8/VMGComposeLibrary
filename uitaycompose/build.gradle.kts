plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    id("maven-publish")
    id("kotlin-parcelize")
}

android {
    namespace = "com.valu.uitaycompose"
    compileSdk {
        version = release(37)
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

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.material3)
    api(libs.bouncycastle.bcprov)
    api(libs.bouncycastle.bcpkix)
    api(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.cio)
    implementation(libs.androidx.biometric)
    implementation(libs.androidx.activity.compose)
    implementation(libs.gson)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.exifinterface)
    implementation(libs.zxing.android.embedded)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.montes8"
            artifactId = "uitaycompose"
            version = "1.0.1"
            
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
