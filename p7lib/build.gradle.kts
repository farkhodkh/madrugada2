plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("ru.petroplus.pos.convention")
}

android {
    namespace = Dependencies.namespaceP7Lib
    externalNativeBuild {
        cmake {
            path = File("src/main/cpp/CMakeLists.txt")
            version = "3.18.1"
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    implementation(project(":p7libapi"))

    //Dagger 2
    implementation("${Dependencies.dagger}:${Versions.dagger}")
    implementation("${Dependencies.composeRuntime}:${Versions.compose}")

    //Modules
    implementation(project(":core"))
    implementation(project(":util"))
}