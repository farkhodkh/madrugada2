
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("ru.petrolplus.pos.convention")
}

android {
    namespace = Dependencies.namespaceRoom

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kotlinOptions {
        jvmTarget =  JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    //Modules
    implementation(project(":core"))
    //Room
    implementation("${Dependencies.roomKtx}:${Versions.room}")
    kapt("${Dependencies.roomCompiler}:${Versions.room}")

    //Dagger 2
    implementation("${Dependencies.dagger}:${Versions.dagger}")
    kapt("${Dependencies.daggerCompiler}:${Versions.dagger}")

    //Testing
    testImplementation("${Dependencies.junit}:${Versions.junit}")
    androidTestImplementation("${Dependencies.roomTesting}:${Versions.room}")
    androidTestImplementation("${Dependencies.extJunit}:${Versions.extJunit}")
    androidTestImplementation("${Dependencies.espresso}:${Versions.espresso}")
}