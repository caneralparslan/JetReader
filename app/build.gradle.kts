plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.amasmobile.jet_a_reader"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.amasmobile.jet_a_reader"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.espresso.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Firebase Bom
    implementation(platform(libs.firebase.bom))
    // Firebase Auth
    implementation(libs.firebase.auth.ktx)
    // Firestore
    implementation(libs.firebase.firestore.ktx)

    implementation(libs.google.firebase.core)


// Coroutines Core & Android
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

// Play Services coroutine support
    implementation(libs.kotlinx.coroutines.play.services)


// Lifecycle ViewModel with coroutines
    implementation(libs.androidx.lifecycle.viewmodel.ktx)


// Dagger - Hilt (with KSP)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

// AndroidX Hilt Navigation (Jetpack Compose)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.androidx.hilt.compiler)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Logging Interceptor for OkHttp
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

// Retrofit Core
    implementation(libs.retrofit)

// GSON Converter
    implementation(libs.converter.gson)

    implementation("androidx.compose.material:material-icons-extended:1.7.8" )


}