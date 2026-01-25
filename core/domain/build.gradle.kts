plugins {
    id("my.android.library")
    id("my.android.hilt")
}

android {
    namespace = "com.mm.domain"
}

dependencies {
    implementation(project(":core:common"))
}