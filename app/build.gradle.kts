plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("androidx.navigation.safeargs.kotlin")

    id("org.jetbrains.kotlin.plugin.serialization")

    id("com.google.dagger.hilt.android")
    kotlin("kapt")

    id("com.google.gms.google-services")
}

android {
    namespace = "com.miitdiplomasoft.forgotintransport"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.miitdiplomasoft.forgotintransport"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation ("androidx.activity:activity-ktx:1.3.0-rc01")

    implementation ("com.github.bumptech.glide:glide:4.13.2")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.firebase:firebase-firestore-ktx:25.0.0")
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.13.2")


    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-compiler:2.51.1")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("com.airbnb.android:lottie:6.1.0")
    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("androidx.navigation:navigation-ui-ktx:2.7.3")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")

    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    implementation("io.ktor:ktor-client-core:1.6.8")
    implementation("io.ktor:ktor-client-okhttp:1.6.8")
    implementation("io.ktor:ktor-client-android:1.6.8")
    implementation("io.ktor:ktor-client-logging:1.6.8")
    implementation("io.ktor:ktor-client-serialization:1.6.8")
//    implementation("io.ktor:ktor-client-content-negotiation:2.3.4")
//    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")

}
