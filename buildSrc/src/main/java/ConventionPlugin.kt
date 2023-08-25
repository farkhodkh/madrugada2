import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class ConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val android = target.extensions.getByType(LibraryExtension::class.java)

        with(android) {
            compileSdk = Versions.targetSdk

            defaultConfig {
                minSdk = Versions.minSdkVersion
                targetSdk = Versions.targetSdk
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
    }
}