plugins {
    alias(libs.plugins.android.application)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.numberstesttask"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.numberstesttask"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    buildFeatures {
        viewBinding = true
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-scalars:3.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.4.0")

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.11.0")
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    //room
    // Базовая библиотека Room
    implementation("androidx.room:room-runtime:2.8.4")
    // Компилятор Room (для KSP)
    ksp("androidx.room:room-compiler:2.8.4")
    // --- ОПЦИОНАЛЬНО, НО ОЧЕНЬ РЕКОМЕНДУЕТСЯ ---
    // Для удобной работы с Kotlin Coroutines (suspend-функции, Flow)
    implementation("androidx.room:room-ktx:2.8.4") //[reference:8]
    // --- ДЛЯ ТЕСТИРОВАНИЯ (опционально) ---
    testImplementation("androidx.room:room-testing:2.8.4") //[reference:9]

}