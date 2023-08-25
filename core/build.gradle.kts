plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("ru.petroplus.pos.convention")
}

android {
    namespace = Dependencies.namespaceCore

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    //Dagger 2
    implementation("${Dependencies.dagger}:${Versions.dagger}")
}