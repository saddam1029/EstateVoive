plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version "1.9.22" // ADD THIS DIRECTLY

}

android {
    namespace = "com.example.estatevoice"
    compileSdk = 36  // Change from 35 to 36

    defaultConfig {
        applicationId = "com.example.estatevoice" // Fixed: changed from sparehub
        minSdk = 28
        targetSdk = 36  // Also update targetSdk to 36
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

    buildFeatures {
        viewBinding = true  // Add this if using view binding
        // or dataBinding = true if using data binding
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.auth)
//    implementation(libs.firebase.auth)
//    implementation(libs.firebase.firesftore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

// SDP/SSP for scalable sizes
    implementation("com.intuit.ssp:ssp-android:1.0.6")
    implementation("com.intuit.sdp:sdp-android:1.0.6")

    implementation("com.google.android.material:material:1.11.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.9.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.0")

    // Supabase
    implementation("io.github.jan-tennert.supabase:gotrue-kt:1.4.1")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:1.4.1")
    implementation("io.ktor:ktor-client-android:2.3.5")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")


}
