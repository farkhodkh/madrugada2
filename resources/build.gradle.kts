plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("ru.petrolplus.pos.convention")
}

android {
    namespace = Dependencies.namespaceResources
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    //Json
    implementation("${Dependencies.gson}:${Versions.gson}")

    //Modules
    implementation(project(":util"))
}