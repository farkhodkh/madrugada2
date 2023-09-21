plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("ru.petrolplus.pos.convention")
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
    implementation(project(":data_sdkapi"))

    //Modules
    implementation(project(":util"))
    implementation(project(":evotorlib"))
    implementation(project(":core"))

    //Dagger 2
    implementation("${Dependencies.dagger}:${Versions.dagger}")
    kapt("${Dependencies.daggerCompiler}:${Versions.dagger}")

    //Coroutines
    implementation("${Dependencies.kotlinxCoroutines}:${Versions.kotlinxCoroutines}")
    implementation("${Dependencies.kotlinxCoroutinesCore}:${Versions.kotlinxCoroutines}")
}