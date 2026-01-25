plugins {
    id("my.android.library")
    id("my.android.hilt")
    id("my.android.room")
}

android {
    namespace = "com.mm.database"
}

dependencies {
    implementation(project(":core:domain"))
}