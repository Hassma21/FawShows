plugins {
    id("my.android.library")
    id("my.android.hilt")
    id("my.android.retrofit")
}

android {
    namespace = "com.mm.data"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:common"))
    implementation(project(":core:database"))
}