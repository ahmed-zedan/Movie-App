@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.navigation.safe.args)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.zedan.dru.movieapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zedan.dru.movieapp"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
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
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.kotlinx.core)
    implementation(libs.kotlinx.android)

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.fragment)

    implementation(libs.material)
    implementation(libs.constraintlayout)

    // Androidx
    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.room.paging)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.legacy.support.v4)
    ksp(libs.androidx.room.compiler)


    // Jetpack
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.viewmodel.savedstate)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)


    implementation(libs.glide.core)
    implementation(libs.glide.transformations)
    ksp(libs.glide.compiler)

    // Network
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.moshi.core)
    ksp(libs.moshi.kotlin.codegen)

    // DI - Hilt
    implementation(libs.dagger.hilt.android.core)
    kapt(libs.dagger.hilt.android.compiler)


    implementation(libs.sdp.android)
    implementation(libs.ssp.android)
    implementation(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}