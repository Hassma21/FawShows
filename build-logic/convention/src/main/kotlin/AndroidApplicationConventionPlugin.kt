import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {

        val libs = extensions
            .getByType<VersionCatalogsExtension>()
            .named("libs")
        pluginManager.apply("com.android.application")
        pluginManager.apply("org.jetbrains.kotlin.android")
        pluginManager.apply("com.google.gms.google-services")
        pluginManager.apply("com.google.firebase.crashlytics")

        extensions.configure<ApplicationExtension> {

            compileSdk = 36

            defaultConfig {
                minSdk = 24
                targetSdk = 36
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }

        }
        extensions.configure<KotlinAndroidProjectExtension> {
            compilerOptions {
                jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
            }
        }
        dependencies {
            add("implementation", platform(libs.findLibrary("firebase-bom").get()))
            add("implementation", libs.findLibrary("firebase-analytics").get())
            add("implementation", libs.findLibrary("firebase-crashlytics").get())
        }
    }
}

