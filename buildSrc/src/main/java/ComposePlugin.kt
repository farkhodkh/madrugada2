import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val android = target.extensions.getByType(LibraryExtension::class.java)

        with(android) {
            buildFeatures {
                compose = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtensionVersion
            }
        }

        target.dependencies {
            add("implementation","${Dependencies.composeConstraint}:${Versions.composeConstraint}")
            add("implementation","${Dependencies.composeConstraint}:${Versions.composeConstraint}")
            add("implementation","${Dependencies.composeUi}:${Versions.compose}")
            add("implementation","${Dependencies.composeMaterial}:${Versions.compose}")
            add("implementation","${Dependencies.composeUiUtil}:${Versions.compose}")
            add("implementation","${Dependencies.composeUiToolPreview}:${Versions.compose}")
            add("implementation","${Dependencies.composeUiTool}:${Versions.compose}")
            add("implementation","${Dependencies.activityCompose}:${Versions.activityCompose}")
            add("implementation","${Dependencies.composeNavigation}:${Versions.composeNavigation}")
            add("implementation","${Dependencies.composeFoundation}:${Versions.compose}")
        }


    }
}