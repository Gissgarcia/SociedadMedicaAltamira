plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.sociedadmedicaaltamira_grupo13"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.sociedadmedicaaltamira_grupo13"
        minSdk = 24
        targetSdk = 36
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

    // --- AndroidX / Compose ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation("androidx.navigation:navigation-compose:2.8.3")
    implementation("androidx.compose.material3:material3-window-size-class-android:1.3.2")
    implementation("androidx.compose.animation:animation:1.7.5")

    // --- Datastore ---
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // --- Coil (imágenes) ---
    implementation("io.coil-kt:coil-compose:2.6.0")

    // --- Coroutines ---
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // --- JSON Serialization ---
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // --- Retrofit + Gson ---
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // --- OkHttp (logging) ---
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.14")
    implementation(libs.androidx.junit.ktx)

    // ─────────────────────────────────────────────
    // Testing
    // ─────────────────────────────────────────────

    // Unit tests (JUnit 4)
    testImplementation(libs.junit)

    // MockK para mocks
    testImplementation("io.mockk:mockk:1.13.8")

    // Coroutines test (para viewModelScope, Dispatchers.Main, etc.)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("com.squareup.okhttp3:mockwebserver:5.0.0-alpha.14")
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.3")

    // Instrumented tests (Android)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Compose UI test helpers
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Herramientas de debug de Compose
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
