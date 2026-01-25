    plugins {
    id("my.android.application")
    id("my.android.compose")
    id("my.android.hilt")
}

android {
    namespace = "com.mm.fawshows"

    defaultConfig {
        applicationId = "com.mm.fawshows"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(project(":feature:fawshows_main"))
    implementation(project(":feature:fawshows_detail"))
    implementation(project(":feature:fawshows_favourites"))
    implementation(project(":feature:fawshows_search"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
    implementation(project(":core:common"))
}