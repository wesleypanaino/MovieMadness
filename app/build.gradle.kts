import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    kotlin("kapt")  // Kotlin Annotation Processing Tool (KAPT) plugin for processing annotations.
    id("com.google.dagger.hilt.android")  // Dagger Hilt plugin for dependency injection.
    id("dagger.hilt.android.plugin")  // Another Dagger Hilt plugin, possibly for enabling additional Dagger Hilt features.
    id("com.android.application")  // Android Application plugin to indicate this is an application module.
    id("org.jetbrains.kotlin.android")  // Kotlin Android plugin for building Android apps with Kotlin.
}
// Load the secrets.properties file
val secretsProperties = Properties()
val secretsPropertiesFile = rootProject.file("secrets.properties")
if (secretsPropertiesFile.exists()) {
    secretsProperties.load(FileInputStream(secretsPropertiesFile))
}
android.buildFeatures.buildConfig = true
android {
    namespace = "com.wesleypanaino.moviemadness"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.wesleypanaino.moviemadness"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField(
                String::class.java.typeName,
                "API_KEY",
                secretsProperties["API_KEY"].toString()
            )
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                String::class.java.typeName,
                "API_KEY",
                secretsProperties["API_KEY"].toString()
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core Libraries:
    implementation(libs.core.ktx)  // Kotlin extensions for core Android libraries.
    implementation(libs.lifecycle.runtime.ktx)  // Kotlin extensions for Android lifecycle runtime.
    implementation(libs.activity.compose)  // Compose extensions for Android Activity.

    // Compose Libraries:
    implementation(platform(libs.compose.bom))  // Bill of Materials for Jetpack Compose.
    implementation(libs.ui)  // Jetpack Compose UI library.
    implementation(libs.ui.graphics)  // Jetpack Compose UI Graphics library.
    implementation(libs.ui.tooling.preview)  // Jetpack Compose UI Tooling Preview library.
    implementation(libs.material3)  // Material Design 3 components for Jetpack Compose.

    // Navigation Libraries:
    implementation(libs.androidx.navigation.runtime.ktx)  // Kotlin extensions for AndroidX Navigation runtime.
    implementation(libs.androidx.navigation.compose)  // Compose extensions for AndroidX Navigation.

    // Testing Libraries:
    testImplementation(libs.junit)  // JUnit for unit testing.
    androidTestImplementation(libs.androidx.test.ext.junit)  // AndroidX Test extensions for JUnit.
    androidTestImplementation(libs.espresso.core)  // Espresso core library for UI testing.
    androidTestImplementation(platform(libs.compose.bom))  // Bill of Materials for Jetpack Compose (testing scope).
    androidTestImplementation(libs.ui.test.junit4)  // Jetpack Compose UI Test library for JUnit 4.

    // Debugging Libraries:
    debugImplementation(libs.ui.tooling)  // Jetpack Compose UI Tooling library (debugging scope).
    debugImplementation(libs.ui.test.manifest)  // Jetpack Compose UI Test Manifest library (debugging scope).

    // Network Libraries:
    implementation(libs.retrofit)  // Retrofit for network operations.
    implementation(libs.okhttp)  // OkHttp for HTTP client.

    // Room Libraries:
    implementation(libs.androidx.room.runtime)  // AndroidX Room runtime for database operations.
    annotationProcessor(libs.androidx.room.compiler)  // Annotation processor for Room.

    // Serialization Libraries:
    implementation(libs.converter.gson)  // Gson converter for serialization/deserialization.

    // Hilt Libraries:
    implementation(libs.hilt.android)  // Dagger Hilt for dependency injection.
    kapt(libs.hilt.android.compiler)  // KAPT for Dagger Hilt compiler.
    kapt(libs.google.hilt.compiler)  // KAPT for Google Hilt compiler.
    kapt(libs.androidx.hilt.compiler)  // KAPT for AndroidX Hilt compiler.

    // Hilt and Navigation Libraries:
    implementation(libs.androidx.hilt.work)  // AndroidX Hilt WorkManager extensions.
    implementation(libs.androidx.hilt.navigation.compose)  // Compose extensions for Hilt and Navigation.

    // More Navigation Libraries:
    implementation(libs.androidx.navigation.ui.ktx)  // Kotlin extensions for AndroidX Navigation UI.
    implementation(libs.androidx.navigation.dynamic.features.fragment)  // Dynamic features support for AndroidX Navigation.

    // WorkManager Library:
    implementation(libs.androidx.work.runtime.ktx)  // Kotlin extensions for AndroidX WorkManager.

    // Image Loading Library:
    implementation(libs.coil.compose)  // Coil-Compose for image loading.

    // Material and Icon Libraries:
    implementation(libs.androidx.material.icons.core)  // Material icons core library.
    implementation(libs.androidx.material)  // AndroidX Material components library.

    // Palette and Material Libraries:
    implementation(libs.palette)  // Android Palette library for color extraction.
    implementation(libs.google.material)  // Google Material components library.
}
kapt {
    correctErrorTypes = true
}