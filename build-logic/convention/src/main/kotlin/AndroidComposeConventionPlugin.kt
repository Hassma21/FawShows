import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {

        val libs = extensions
            .getByType<VersionCatalogsExtension>()
            .named("libs")
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

        extensions.findByType(CommonExtension::class.java)?.apply {
            buildFeatures {
                compose = true
            }
        }

        dependencies {
            add("implementation", platform(libs.findLibrary("androidx-compose-bom").get()))

            add("implementation", libs.findLibrary("androidx-compose-ui").get())
            add("implementation", libs.findLibrary("androidx-compose-ui-graphics").get())
            add("implementation", libs.findLibrary("androidx-compose-material3").get())
            add("implementation", libs.findLibrary("androidx-activity-compose").get())
            add("implementation", libs.findLibrary("hilt-navigation-compose").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
            add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())

            add("androidTestImplementation", platform(libs.findLibrary("androidx-compose-bom").get()))
            add("androidTestImplementation", libs.findLibrary("androidx-compose-ui-test-junit4").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-manifest").get())
            add("implementation", libs.findLibrary("material3WindowSizeClass").get())
            add("implementation", libs.findLibrary("coilCompose").get())
        }
    }
}
