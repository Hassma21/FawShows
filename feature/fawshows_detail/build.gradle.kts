plugins {
    id("my.android.library")
    id("my.android.compose")
    id("my.android.hilt")
}

android {
    namespace = "com.mm.feature.detail"
}
dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
    implementation( "com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.28")
}