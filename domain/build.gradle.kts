plugins {
    id("java-library")
    id("kotlin")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {

    // Coroutine
    implementation(libs.org.jetbrains.kotlinx.coroutines)
    // Serializable
    implementation(libs.org.jetbrains.kotlinx.serialization.json)
    // Inject
    implementation(libs.javax.inject)
}