plugins {
    id("my.android.library")
    id("my.android.compose")
    id("my.android.hilt")
}

android {
    namespace = "com.mm.feature.search"
}
dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
}