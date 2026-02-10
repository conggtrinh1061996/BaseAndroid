import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.androidx.navigation.safeargs.kotlin)
    alias(libs.plugins.google.gms.google.services)
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
        freeCompilerArgs.add("-Xopt-in=kotlin.RequiresOptIn")
    }
}

android {
    namespace = "com.androidtech.base"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.androidtech.base"
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

    flavorDimensions += "default"
    productFlavors {
        create("dev") {
            applicationId = "com.androiddev.androidprojects.dev"
            resValue("string", "app_name", "Dev Android Projects")

            buildConfigField("String", "DOMAIN_URL", "\"https://newsapi.org/v2/\"")
            buildConfigField("String", "TOKEN", "\"e2c8643cf3b84da391633b213c9efac7\"")

        }

        create("prod") {
            buildConfigField("String", "DOMAIN_URL", "\"https://newsapi.org/v2/\"")
            buildConfigField("String", "TOKEN", "\"e2c8643cf3b84da391633b213c9efac7\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(project(path = ":domain"))
    implementation(project(path = ":data"))

    // Timber
    implementation(libs.com.jakewharton.timber)
    // Moshi
    implementation(libs.com.squareup.moshi.kotlin)
    implementation(libs.com.squareup.retrofit2.converter.moshi)
    // Gson
    implementation(libs.com.google.code.gson)
    // Coroutine
    implementation(libs.org.jetbrains.kotlinx.coroutines)
    // OkHttp
    implementation(libs.com.squareup.okhttp)
    implementation(libs.com.squareup.okhttp3.logging.interceptor)
    // Room database
    implementation(libs.androidx.runtime.room)
    ksp(libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)

    // life cycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)

    // Retrofit
    implementation (libs.com.squareup.retrofit)
    implementation (libs.com.squareup.retrofit.converter.gson)
    // Skydoves
    implementation (libs.com.github.skydoves.sandwich)
    // Hilt
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.android.compiler)
    // navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    //coil
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)
    //paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.common.ktx)
}