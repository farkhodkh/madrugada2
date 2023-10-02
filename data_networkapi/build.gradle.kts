plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("ru.petrolplus.pos.convention")
}

android {
    namespace = Dependencies.namespaceNetworkApi

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
dependencies {

    implementation("${Dependencies.composeRuntime}:${Versions.compose}")

    //Retrofit
    implementation("${Dependencies.retrofit}:${Versions.retrofit}")

    //Coroutines
    implementation("${Dependencies.kotlinxCoroutines}:${Versions.kotlinxCoroutines}")

    //Modules
    implementation(project(":resources"))
}