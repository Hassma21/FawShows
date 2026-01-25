import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidHiltConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {

        val libs = extensions
            .getByType<VersionCatalogsExtension>()
            .named("libs")

        pluginManager.withPlugin("com.android.application") {
            applyHilt(libs)
        }

        pluginManager.withPlugin("com.android.library") {
            applyHilt(libs)
        }
    }

    private fun Project.applyHilt(libs: org.gradle.api.artifacts.VersionCatalog) {

        pluginManager.apply("com.google.dagger.hilt.android")
        pluginManager.apply("com.google.devtools.ksp")

        dependencies {
            add("implementation", libs.findLibrary("hilt-android").get())
            add("ksp", libs.findLibrary("hilt-compiler").get())
        }
    }
}
