plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("ru.petrolplus.pos.convention")
}

android {
    namespace = Dependencies.namespaceUtil

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    //TODO - Эту зависимость нужно убрать от сюда
    implementation(project(":p7libapi"))

    //Json
    implementation("${Dependencies.gson}:${Versions.gson}")
}