plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.lina.data"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project (":domain"))

    implementation(libs.retrofit.converter)
    implementation(libs.retrofit2)
    implementation(libs.okhttp3)
    implementation(libs.moshi.kotlin)
    implementation(libs.hilt.core)
    kapt(libs.hilt.android.compiler)
    implementation(libs.corountines.core)
    implementation(libs.corountines.android)
}