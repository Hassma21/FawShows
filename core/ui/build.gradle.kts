plugins {
    id("my.android.library")
    id("my.android.compose")
}

android {
    namespace = "com.mm.ui"
}

dependencies {
    implementation(project(":core:domain"))
}
