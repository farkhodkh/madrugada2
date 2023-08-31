plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("ru.petroplus.pos.convention")
}

android {
    namespace = Dependencies.namespaceEvotorSdk

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

}

dependencies {

    //Evotor
    implementation("${Dependencies.evotor}:${Versions.evotor}")

    //Modules
    implementation(project(":sdkapi"))
    implementation(project(":util"))
    implementation(project(":evotorlib"))

    //Coroutines
    implementation("${Dependencies.kotlinxCoroutines}:${Versions.kotlinxCoroutines}")
    implementation("${Dependencies.kotlinxCoroutinesCore}:${Versions.kotlinxCoroutines}")
}