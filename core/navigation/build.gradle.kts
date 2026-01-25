plugins {
    id("my.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.mm.navigation"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(libs.kotlinx.serialization.json)
}
